package gov.va.vetservices.partner.treatmentfacility.ws.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;

public class AbstractTreatmentFacilityTest {

	/**
	 * Make a treatment facility request object with the specified state code.
	 * @param stateCode the stateCode, or null/empty to retrieve all
	 * @return GetVAMedicalTreatmentFacilityList
	 */
	protected GetVAMedicalTreatmentFacilityList makeRequest(final String stateCode) {
		final GetVAMedicalTreatmentFacilityList request = new GetVAMedicalTreatmentFacilityList();
		request.setStateCd(stateCode);
		return request;
	}

	@Test
	public void testInterface() {
		assertTrue(TreatmentFacilityWsClient.class.isInterface());
	}

}
