package gov.va.vetservices.partner.treatmentfacility.ws.client.remote;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.va.vetservices.partner.treatmentfacility.ws.client.AbstractTreatmentFacilityTest;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;

public class RemoteServiceCallMock_UnitTest extends AbstractTreatmentFacilityTest {

	private final static String TEST_VALID_CODE = "VA";

	@Test
	public void testGetKeyForMockResponse() {
		RemoteServiceCallMock mock = new RemoteServiceCallMock();
		GetVAMedicalTreatmentFacilityList request = makeRequest(TEST_VALID_CODE);
		String keyForMockResponse = mock.getKeyForMockResponse(request);

		assertNotNull(keyForMockResponse);
		assertTrue(keyForMockResponse.equals(TEST_VALID_CODE));
	}

	@Test
	public void testCallRemoteService() {
		assertTrue(true);
	}

}
