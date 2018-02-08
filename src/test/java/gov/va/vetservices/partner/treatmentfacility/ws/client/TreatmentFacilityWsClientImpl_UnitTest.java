package gov.va.vetservices.partner.treatmentfacility.ws.client;

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
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;
import gov.va.vetservices.partner.treatmentfacility.ws.remote.RemoteServiceCallMock;

/**
 * <p>
 * Tests the webservice implementation. Note specifically the @ActiveProfiles
 * and @ContextConfiguration.
 * </p>
 * <p>
 * To engage mocking capabilities, @ActiveProfiles must specify the simulator
 * profile. {@link RemoteServiceCallMock} declares itself as the mocking
 * implementation for the simulator profile.
 * </p>
 * <p>
 * MockitoJUnitRunner class cannot be used to @RunWith because the application
 * context must Autowire the WebServiceTemplate from the client implementation.
 * </p>
 *
 * @author aburkholder
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(inheritListeners = false, listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
@ContextConfiguration(inheritLocations = false, classes = { PartnerMockFrameworkTestConfig.class,
		TreatmentFacilityWsClientConfig.class })
public class TreatmentFacilityWsClientImpl_UnitTest {

	private final static String TEST_VALID_CODE = "VA";
	private final static String TEST_BAD_CODE = "VAA";

	@Autowired
	RemoteServiceCall callPartnerService;

	@Autowired
	@Qualifier("treatmentFacilityWsClient.axiom")
	private WebServiceTemplate axiomWebServiceTemplate;

	@Before
	public void setUp() {
		assertNotNull("FAIL axiomWebServiceTemplate cannot be null.", axiomWebServiceTemplate);
		assertNotNull("FAIL callPartnerService cannot be null.", callPartnerService);
	}

	@Test
	public void testGetVAMedicalTreatmentFacilityList() {

		// call the impl declared by the current @ActiveProfiles
		final GetVAMedicalTreatmentFacilityListResponse response = (GetVAMedicalTreatmentFacilityListResponse) callPartnerService
				.callRemoteService(axiomWebServiceTemplate, makeRequest(TEST_VALID_CODE), GetVAMedicalTreatmentFacilityList.class);

		assertNotNull(response);
		assertNotNull(response.getMedicalTreatmentFacilityListReturn());
		assertNotNull(response.getMedicalTreatmentFacilityListReturn().getMedicalTreatmentFacilityList());
		assertNotNull(response.getMedicalTreatmentFacilityListReturn().getMedicalTreatmentFacilityList()
				.getMedicalTreatmentFacility());

		assertTrue(response.getMedicalTreatmentFacilityListReturn().getMedicalTreatmentFacilityList()
				.getMedicalTreatmentFacility().size() == 3);
	}

	@Test
	public void testGetVAMedicalTreatmentFacilityList_badStateCode() {

		// call the impl declared by the current @ActiveProfiles
		GetVAMedicalTreatmentFacilityListResponse response = null;

		try {
			response = (GetVAMedicalTreatmentFacilityListResponse) callPartnerService
					.callRemoteService(axiomWebServiceTemplate, makeRequest(TEST_BAD_CODE), GetVAMedicalTreatmentFacilityList.class);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		assertNull("Bad state code should have thrown exception and not created a response.", response);
	}

	/**
	 * Make a request object with the specified state code.
	 * @param stateCode
	 * @return
	 */
	private GetVAMedicalTreatmentFacilityList makeRequest(final String stateCode) {
		final GetVAMedicalTreatmentFacilityList request = new GetVAMedicalTreatmentFacilityList();
		request.setStateCd(stateCode);
		return request;
	}

}
