package gov.va.ascent.framework.ws.client.remote;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.ws.client.remote.test.mocks.TestAbstractRemoteServiceCallMockRequest;
import gov.va.ascent.framework.ws.client.remote.test.mocks.TestAbstractRemoteServiceCallMockResponse;

public class RemoteServiceCall_UnitTest {

	@Test
	public void test() {

		try {
			TestClass test = new TestClass();
			test.callRemoteService(new WebServiceTemplate(), new TestAbstractRemoteServiceCallMockRequest(),
					TestAbstractRemoteServiceCallMockRequest.class);
			assert(RemoteServiceCall.BEAN_NAME.equals(TestClass.BEAN_NAME));
		} catch (Exception e) {
			e.printStackTrace();
			fail("Could not instantiate an implementation of RemoteServiceCall interface");
		}
	}

	class TestClass implements RemoteServiceCall {

		@Override
		public AbstractTransferObject callRemoteService(WebServiceTemplate webserviceTemplate,
				AbstractTransferObject request, Class<? extends AbstractTransferObject> requestClass) {
			return new TestAbstractRemoteServiceCallMockResponse();
		}

	}

}
