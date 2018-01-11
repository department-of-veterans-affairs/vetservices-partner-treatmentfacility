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
