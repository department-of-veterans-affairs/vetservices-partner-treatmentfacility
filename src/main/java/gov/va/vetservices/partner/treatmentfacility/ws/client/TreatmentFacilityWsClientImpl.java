package gov.va.vetservices.partner.treatmentfacility.ws.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientImpl;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;

/**
 * Spring Web Service based implementation of the MedicalTreatmentFacility
 * interface
 *
 * @author vgadda
 *
 */
@Component(TreatmentFacilityWsClientImpl.BEAN_NAME)
@Profile({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS,
	TreatmentFacilityWsClient.PROFILE_TREATMENT_FACILITY_WSCLIENT_REMOTE_CLIENT_IMPL})
public class TreatmentFacilityWsClientImpl extends BaseWsClientImpl implements TreatmentFacilityWsClient {

	/** The Constant BEAN_NAME. */
	public static final String BEAN_NAME = "treatmentFacilityWsClientImpl";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TreatmentFacilityWsClientImpl.class);

	/**
	 * axiom web service template for medicalTreatmentFacility service
	 */
	@Autowired
	@Qualifier("treatmentFacilityWsClient.axiom")
	private WebServiceTemplate treatmentFacilityWsTemplate;

	/**
	 * Executed after dependency injection is done to validate initialization.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.notNull(treatmentFacilityWsTemplate,
				"axiomWebServiceTemplate cannot be null in order for TreatmentWsClientImpl to work properly.");
	}

	/**
	 * Get a list of medical treatment facilities from the partner
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final GetVAMedicalTreatmentFacilityListResponse getVAMedicalTreatmentFacilityList(
			final GetVAMedicalTreatmentFacilityList request) {
		Defense.notNull(request);

		LOGGER.debug("Inside Get Medical Treatment Facility Client");
		final GetVAMedicalTreatmentFacilityListResponse response = (GetVAMedicalTreatmentFacilityListResponse)
				this.treatmentFacilityWsTemplate.marshalSendAndReceive(request);
		Defense.notNull(response, RESPONSE_FROM_WEBSERVICE_CALL_NULL);
		return response;
	}

}
