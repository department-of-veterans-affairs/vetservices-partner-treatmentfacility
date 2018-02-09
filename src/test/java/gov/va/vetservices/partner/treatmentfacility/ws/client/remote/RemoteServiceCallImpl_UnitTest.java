package gov.va.vetservices.partner.treatmentfacility.ws.client.remote;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.vetservices.partner.treatmentfacility.ws.client.AbstractTreatmentFacilityTest;
import gov.va.vetservices.partner.treatmentfacility.ws.client.PartnerMockFrameworkTestConfig;
import gov.va.vetservices.partner.treatmentfacility.ws.client.TreatmentFacilityWsClientConfig;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
@ContextConfiguration(inheritLocations = false, classes = { PartnerMockFrameworkTestConfig.class,
		TreatmentFacilityWsClientConfig.class })
public class RemoteServiceCallImpl_UnitTest extends AbstractTreatmentFacilityTest {

	private final static String TEST_VALID_CODE = "VA";

	/** Specifically the IMPL class for the RemoteServiceCall interface */
	private RemoteServiceCallImpl callPartnerService = new RemoteServiceCallImpl();

	@Autowired
	@Qualifier("treatmentFacilityWsClient.axiom")
	private WebServiceTemplate axiomWebServiceTemplate;

	@Before
	public void setUp() {
		assertNotNull("FAIL axiomWebServiceTemplate cannot be null.", axiomWebServiceTemplate);
		assertNotNull("FAIL callPartnerService cannot be null.", callPartnerService);
	}

	@Test
	public void testCallRemoteService() {
		// call the impl declared by the current @ActiveProfiles
		GetVAMedicalTreatmentFacilityListResponse response = null;

		/* attempt to call partner will ALWAYS fail - test for specific exception classes */
		try {
			response = (GetVAMedicalTreatmentFacilityListResponse) callPartnerService
					.callRemoteService(axiomWebServiceTemplate, makeRequest(TEST_VALID_CODE), GetVAMedicalTreatmentFacilityList.class);
		} catch (final Exception e) {
			e.printStackTrace();

			assertNotNull(e);
			assertTrue(org.springframework.ws.client.WebServiceIOException.class.equals(e.getClass()));
			assertNotNull(e.getCause());
			assertTrue(java.net.ConnectException.class.equals(e.getCause().getClass()));
		}

		assertNull(response);
	}

}
