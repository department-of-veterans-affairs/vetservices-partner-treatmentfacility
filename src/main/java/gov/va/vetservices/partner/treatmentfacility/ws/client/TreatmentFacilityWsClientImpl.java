package gov.va.vetservices.partner.treatmentfacility.ws.client;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientImpl;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;

/**
 * Spring Web Service based implementation of the TreatmentFacility
 * interface
 *
 * @author vgadda
 *
 */
@Component(TreatmentFacilityWsClientImpl.BEAN_NAME)
public class TreatmentFacilityWsClientImpl extends BaseWsClientImpl implements TreatmentFacilityWsClient {

	/** The Constant BEAN_NAME. */
	public static final String BEAN_NAME = "treatmentFacilityWsClient";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TreatmentFacilityWsClientImpl.class);

	/** axiom web service template for treatmentFacility service */
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
	 * Get a list of treatment facilities from the partner
	 */
	@SuppressWarnings("unchecked")
	@Override
	public final GetVAMedicalTreatmentFacilityListResponse getTreatmentFacilityList(
			final GetVAMedicalTreatmentFacilityList request) {
		Defense.notNull(request);

		final GetVAMedicalTreatmentFacilityListResponse response = (GetVAMedicalTreatmentFacilityListResponse)
				this.treatmentFacilityWsTemplate.marshalSendAndReceive(request);

		Defense.notNull(response, RESPONSE_FROM_WEBSERVICE_CALL_NULL);

		return response;
	}

}
