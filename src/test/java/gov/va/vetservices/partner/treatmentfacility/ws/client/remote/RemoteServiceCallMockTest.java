package gov.va.vetservices.partner.treatmentfacility.ws.client.remote;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.va.vetservices.partner.treatmentfacility.ws.client.AbstractTreatmentFacilityTest;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.MedicalTreatmentFacilityListReturnVO;

public class RemoteServiceCallMockTest extends AbstractTreatmentFacilityTest {

	private final static String TEST_VALID_CODE = "VA";
	private static final String ALL_FACILITIES = "allFacilities";

	@Test
	public void testGetKeyForMockResponse() {
		TreatmentFacilityRemoteServiceCallMock mock = new TreatmentFacilityRemoteServiceCallMock();
		GetVAMedicalTreatmentFacilityList request = makeRequest(TEST_VALID_CODE);
		String keyForMockResponse = mock.getKeyForMockResponse(request);

		assertNotNull(keyForMockResponse);
		assertTrue(keyForMockResponse.equals(TEST_VALID_CODE));

	}

	@Test
	public void testGetKeyForMockResponse_NullStateCode() {
		TreatmentFacilityRemoteServiceCallMock mock = new TreatmentFacilityRemoteServiceCallMock();
		GetVAMedicalTreatmentFacilityList request = makeRequest(null);
		String keyForMockResponse = mock.getKeyForMockResponse(request);

		keyForMockResponse = mock.getKeyForMockResponse(request);

		assertNotNull(keyForMockResponse);
		assertTrue(keyForMockResponse.equals(ALL_FACILITIES));

		new MedicalTreatmentFacilityListReturnVO();
		keyForMockResponse = mock.getKeyForMockResponse(request);

		assertTrue(keyForMockResponse.equals(ALL_FACILITIES));
	}

	@Test
	public void testCallRemoteService_NullRequest() {
		TreatmentFacilityRemoteServiceCallMock mock = new TreatmentFacilityRemoteServiceCallMock();

		GetVAMedicalTreatmentFacilityList request = null;

		String keyForMockResponse = null;

		try {
			keyForMockResponse = mock.getKeyForMockResponse(request);
		} catch (Throwable e) {
			e.printStackTrace();
			assertTrue("Invalid excepetion was thrown.", IllegalArgumentException.class.equals(e.getClass()));
			assertTrue("Exception message contains wrong string.",
					e.getMessage().equals(TreatmentFacilityRemoteServiceCallMock.ERROR_NULL_REQUEST));
		}

		assertNull("Null request should have thrown exception.", keyForMockResponse);
	}

}
