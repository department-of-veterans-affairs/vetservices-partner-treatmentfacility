package gov.va.vetservices.partner.treatmentfacility.ws.client;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientImpl;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;
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

	@Autowired
	private RemoteServiceCall remoteServiceCall;

	/** axiom web service template for treatmentFacility service */
	@Autowired
	@Qualifier("treatmentFacilityWsClientAxiomTemplate")
	private WebServiceTemplate treatmentFacilityWsTemplate;

	/**
	 * Executed after dependency injection is done to validate initialization.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.notNull(remoteServiceCall, "remoteServiceCall cannot be null.");
		Defense.notNull(treatmentFacilityWsTemplate,
				"treatmentFacilityWsTemplate cannot be null in order for TreatmentWsClientImpl to work properly.");
	}

	/**
	 * <p>Get a list of treatment facilities from the partner.</p>
	 * <p>The RemoteServiceCall implementation is selected by the current spring profile. REMOTE_CLIENT_IMPLS
	 * <ul>
	 * <li>PROFILE_REMOTE_CLIENT_IMPLS instantiates RemoteServiceCallImpl</li>
	 * <li>PROFILE_REMOTE_CLIENT_SIMULATORS instantiates RemoteServiceCallMock</li>
	 * </ul>
	 * </p>
	 */
	@Override
	public final GetVAMedicalTreatmentFacilityListResponse getTreatmentFacilityList(
			final GetVAMedicalTreatmentFacilityList request) {
		Defense.notNull(request);

		final GetVAMedicalTreatmentFacilityListResponse response = (GetVAMedicalTreatmentFacilityListResponse)
				remoteServiceCall.callRemoteService(treatmentFacilityWsTemplate, request, request.getClass());

		Defense.notNull(response, RESPONSE_FROM_WEBSERVICE_CALL_NULL);

		return response;
	}

}
