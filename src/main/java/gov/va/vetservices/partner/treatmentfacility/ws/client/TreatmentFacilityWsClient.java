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
// More methods could be added from the partner's SOAP API,
// so anyone who uses lambda expressions against this interface will be hosed if/when that happens.
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
