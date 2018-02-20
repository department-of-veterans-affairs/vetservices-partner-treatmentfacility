package gov.va.vetservices.partner.treatmentfacility.ws.client.remote;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.transform.Source;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.remote.AbstractRemoteServiceCallMock;
import gov.va.vetservices.partner.treatmentfacility.ws.client.AbstractTreatmentFacilityTest;
import gov.va.vetservices.partner.treatmentfacility.ws.client.PartnerMockFrameworkTestConfig;
import gov.va.vetservices.partner.treatmentfacility.ws.client.TreatmentFacilityWsClientConfig;
import gov.va.vetservices.partner.treatmentfacility.ws.client.TreatmentFacilityWsClientException;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
@ContextConfiguration(inheritLocations = false, classes = { PartnerMockFrameworkTestConfig.class,
		TreatmentFacilityWsClientConfig.class })
public class RemoteServiceCallImpl_UnitTest extends AbstractTreatmentFacilityTest {

	private final static String TEST_VALID_CODE = "VA";

	/** Specifically the IMPL class for the RemoteServiceCall interface */
	private TreatmentFacilityRemoteServiceCallImpl callPartnerService = new TreatmentFacilityRemoteServiceCallImpl();

	private MockWebServiceServer mockWebServicesServer;

	@Autowired
	@Qualifier("treatmentFacilityWsClientAxiomTemplate")
	private WebServiceTemplate axiomWebServiceTemplate;

	@Before
	public void setUp() {
		assertNotNull("FAIL axiomWebServiceTemplate cannot be null.", axiomWebServiceTemplate);
		assertNotNull("FAIL callPartnerService cannot be null.", callPartnerService);

		mockWebServicesServer = MockWebServiceServer.createServer(axiomWebServiceTemplate);
		assertNotNull("FAIL mockWebServicesServer cannot be null.");
	}

	@Test
	public void testCallRemoteService() {
		// call the impl declared by the current @ActiveProfiles

		GetVAMedicalTreatmentFacilityList request = makeRequest(TEST_VALID_CODE);
		final Source requestPayload = marshalMockRequest((Jaxb2Marshaller) axiomWebServiceTemplate.getMarshaller(), request,
				request.getClass());
		final Source responsePayload = readMockResponseByKey(request.getStateCd());

		mockWebServicesServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));

		/* attempt to call partner will ALWAYS fail - test for specific exception classes */
		try {
			callPartnerService
			.callRemoteService(axiomWebServiceTemplate, request, GetVAMedicalTreatmentFacilityList.class);

			mockWebServicesServer.verify();

		} catch (final Throwable e) {
			e.printStackTrace();

			fail("FAIL mockWebServicesServer did not function as expected");
		}
	}

	/**
	 * Exact copy of the
	 * @param marshaller
	 * @param request
	 * @param requestClass
	 * @return
	 */
	private StringSource marshalMockRequest(final Jaxb2Marshaller marshaller, final AbstractTransferObject request,
			final Class<?> requestClass) {
		final StringResult result = new StringResult();
		marshaller.marshal(requestClass.cast(request), result);
		return new StringSource(result.toString());
	}

	/**
	 * Request payload creation specific to this endpoint operation test.
	 * @param keyPath
	 * @return
	 */
	private ResourceSource readMockResponseByKey(final String keyPath) {
		Defense.hasText(keyPath);

		// read the resource
		ResourceSource resource = null;
		try {
			resource = new ResourceSource(new ClassPathResource(MessageFormat.format(AbstractRemoteServiceCallMock.MOCK_FILENAME_TEMPLATE, keyPath)));
		} catch (final IOException e) {
			throw new TreatmentFacilityWsClientException(("Could not read mock XML file '"
					+ MessageFormat.format(AbstractRemoteServiceCallMock.MOCK_FILENAME_TEMPLATE, keyPath)
					+ "' using key '" + keyPath + "'. Please make sure this response file exists in the main/resources directory."), e);
		}
		return resource;
	}
}
