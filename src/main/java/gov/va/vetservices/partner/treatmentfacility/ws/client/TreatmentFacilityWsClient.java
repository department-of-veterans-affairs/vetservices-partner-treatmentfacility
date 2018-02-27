package gov.va.vetservices.partner.treatmentfacility.ws.client;

import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;

/**
 * This interface contains the TreatmentFacility operations in the remote MedicalTreatmentFacility
 * web service
 *
 * @author vgadda
 *
 */
//Sonar mis-identifies this interface as a single-abstract-method interface ("squid:S1609").
// Future requirements could add more methods to this class from the partner's SOAP API,
// so it is best to disallow lambda expressions against this interface (not put @FunctionalInterface on this class).
@SuppressWarnings("squid:S1609")
public interface TreatmentFacilityWsClient {

	/**
	 * Get the TreatmentFacilityList from the remote MedicalTreatmentFacility Web Service.
	 * @param request The remote request entity to find treatment facilities
	 * @return GetVAMedicalTreatmentFacilityListResponse The remote response entity
	 */

	GetVAMedicalTreatmentFacilityListResponse getTreatmentFacilityList(
			final GetVAMedicalTreatmentFacilityList request);
}
