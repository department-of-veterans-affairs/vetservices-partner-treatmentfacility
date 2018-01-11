package gov.va.vetservices.partner.treatmentfacility.ws.client;

import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;

/**
 * This interface contains the operations in the remote MedicalTreatmentFacility
 * web service necessary to be implemented in a client capable of communicating
 * with the MedicalTreatmentFacility service
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
	 * Spring profile for MedicalTreatmentFacility wsclient remote client
	 * implementation.
	 */
	String PROFILE_TREATMENT_FACILITY_WSCLIENT_REMOTE_CLIENT_IMPL = "medicaltreatmentfacility_wsclient_remote_client_impl";

	/**
	 * Spring profile for MedicalTreatmentFacility wsclient remote client
	 * simulator.
	 */
	String PROFILE_TREATMENT_FACILITY_WSCLIENT_REMOTE_CLIENT_SIM = "medicaltreatmentfacility_wsclient_remote_client_sim";

	/**
	 * @param request The MedicalTreatmentFacility Web Service to find treatment facilities
	 * @return GetVAMedicalTreatmentFacilityListResponse The MedicalTreatmentFacility Web Service  response entity
	 */

	GetVAMedicalTreatmentFacilityListResponse getVAMedicalTreatmentFacilityList(
			final GetVAMedicalTreatmentFacilityList request);
}
