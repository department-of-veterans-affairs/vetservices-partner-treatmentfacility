package gov.va.ascent.framework.ws.client.remote;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.audit.RequestResponseAuditData;
import gov.va.ascent.framework.messages.MessageSeverity;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.ws.client.remote.test.mocks.TestAbstractRemoteServiceCallMockRequest;
import gov.va.ascent.framework.ws.client.remote.test.mocks.TestAbstractRemoteServiceCallMockResponse;

@RunWith(MockitoJUnitRunner.class)
public class RemoteServiceCallAspect_UnitTest {

	private static final String REQUEST_KEY_VALUE = "abstract-remote-service-call-mock-data";
	private static final String RESPONSE_VALUE = "some-value";

	@Mock
	private ProceedingJoinPoint proceedingJoinPoint;

	@Mock
	private MethodSignature signature;

	@Mock
	private JoinPoint.StaticPart staticPart;

	@Mock
	private RemoteServiceCallAspect mockRemoteServiceCallAspect;

	private TestAbstractRemoteServiceCallMockRequest request;
	private TestAbstractRemoteServiceCallMockResponse response;

	@Spy
	private WebServiceTemplate webserviceTemplate;

	private Object[] value;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Throwable {
		// make request & response from mock transfer objects
		request = new TestAbstractRemoteServiceCallMockRequest();
		request.setSomeKeyVariable(REQUEST_KEY_VALUE);

		response = new TestAbstractRemoteServiceCallMockResponse();
		response.setSomeData(RESPONSE_VALUE);

		// mock the joinpoint arguments
		value = new Object[3];
		value[0] = webserviceTemplate;
		value[1] = request;
		value[2] = request.getClass();

		// modify behavior of spied webservice template when it executes marshalSendAndReceive
		doReturn(response).when(webserviceTemplate).marshalSendAndReceive(request);

		// modify behavior of mocked elements within the aspect itself
		doNothing().when(mockRemoteServiceCallAspect).writeAudit(isA(MessageSeverity.class), isA(Method.class), isA(RequestResponseAuditData.class));
		doCallRealMethod().when(mockRemoteServiceCallAspect).aroundAdvice(any(ProceedingJoinPoint.class), any(WebServiceTemplate.class),
				any(AbstractTransferObject.class), any(Class.class)); // any(Class.class) should have <? extends AbstractTransferObject> but wildcards not allowed
		when(proceedingJoinPoint.getArgs()).thenReturn(value);
		when(proceedingJoinPoint.getStaticPart()).thenReturn(staticPart);
		when(staticPart.getSignature()).thenReturn(signature);
		when(signature.getMethod()).thenReturn(someMethod());
	}

	@Test
	public void testAroundAdvice() {
		try {
			assertNotNull(mockRemoteServiceCallAspect.aroundAdvice(proceedingJoinPoint, webserviceTemplate, request, request.getClass()));
			assertNull(((TestAbstractRemoteServiceCallMockRequest) mockRemoteServiceCallAspect.aroundAdvice(
					proceedingJoinPoint, webserviceTemplate, request,
					request.getClass())).getSomeKeyVariable());

		} catch (Throwable throwable) {
			throwable.printStackTrace();
			fail("mockRemoteServiceCallAspect.aroundAdvice() threw exception " + throwable.getMessage());
		}
	}

	@Test
	public void testAroundAdviceMissingArgs() throws Throwable {
		value[0] = webserviceTemplate;
		value[1] = request;
		value[2] = null;
		try {
			mockRemoteServiceCallAspect.aroundAdvice(proceedingJoinPoint, webserviceTemplate, request, null);
		} catch (Throwable throwable) {
			assertTrue(IllegalArgumentException.class.isAssignableFrom(throwable.getClass()));
		}

		value[0] = webserviceTemplate;
		value[1] = null;
		value[2] = request.getClass();
		try {
			mockRemoteServiceCallAspect.aroundAdvice(proceedingJoinPoint, webserviceTemplate, null, request.getClass());
		} catch (Throwable throwable) {
			assertTrue(IllegalArgumentException.class.isAssignableFrom(throwable.getClass()));
		}

		value[0] = null;
		value[1] = request;
		value[2] = request.getClass();
		try {
			mockRemoteServiceCallAspect.aroundAdvice(proceedingJoinPoint, null, request, request.getClass());
		} catch (Throwable throwable) {
			assertTrue(IllegalArgumentException.class.isAssignableFrom(throwable.getClass()));
		}
	}

	/** required for Audit log events */
	public Method someMethod() throws NoSuchMethodException{
		return getClass().getDeclaredMethod("someResponseMethod",String.class);
	}

	/** required to make someMethod() work */
	public String someResponseMethod(String irrelevant) {
		return irrelevant;
	}
}