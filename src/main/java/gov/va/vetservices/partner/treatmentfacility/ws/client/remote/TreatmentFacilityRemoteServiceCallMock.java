package gov.va.vetservices.partner.treatmentfacility.ws.client.remote;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;
import gov.va.ascent.framework.transfer.PartnerTransferObjectMarker;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.remote.AbstractRemoteServiceCallMock;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;

/**
 * Implements the {@link RemoteServiceCall} interface, and extends
 * {@link AbstractRemoteServiceCallMock} for mocking the remote client under the
 * simulators spring profile.
 *
 * @author aburkholder
 */
@Profile(AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS)
@Component(TreatmentFacilityRemoteServiceCallImpl.BEAN_NAME)
public class TreatmentFacilityRemoteServiceCallMock extends AbstractRemoteServiceCallMock {
	private static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(TreatmentFacilityRemoteServiceCallMock.class);

	/** default mock data if stateCode is null or empty */
	private static final String ALL_FACILITIES = "allFacilities";

	/** error for null request */
	static final String ERROR_NULL_REQUEST = "getKeyForMockResponse request parameter cannot be null.";

	/*
	 * (non-Javadoc)
	 *
	 * @see gov.va.ascent.framework.ws.client.remote.RemoteServiceCall#callRemoteService(org.springframework.ws.client.core.
	 * WebServiceTemplate, gov.va.ascent.framework.transfer.PartnerTransferObjectMarker, java.lang.Class)
	 */
	@Override
	public PartnerTransferObjectMarker callRemoteService(final WebServiceTemplate webserviceTemplate,
			final PartnerTransferObjectMarker request,
			final Class<? extends PartnerTransferObjectMarker> requestClass) {

		LOGGER.info("Calling MOCK service with request " + ReflectionToStringBuilder.toString(request));
		// super handles exceptions
		return super.callMockService(webserviceTemplate, request, requestClass);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * gov.va.ascent.framework.ws.client.remote.AbstractRemoteServiceCallMock#getKeyForMockResponse(gov.va.ascent.framework.transfer.
	 * PartnerTransferObjectMarker)
	 */
	@Override
	protected String getKeyForMockResponse(final PartnerTransferObjectMarker request) {
		Defense.notNull(request, ERROR_NULL_REQUEST);

		String mockFilename = null;

		if (request.getClass().isAssignableFrom(GetVAMedicalTreatmentFacilityList.class)
				&& StringUtils.isNotBlank(((GetVAMedicalTreatmentFacilityList) request).getStateCd())) {
			// specify a mock filename that is the state code
			mockFilename = ((GetVAMedicalTreatmentFacilityList) request).getStateCd();
		}
		if (StringUtils.isBlank(mockFilename)) {
			// the API allows to retrieve all facilities if input state code is null, so hard-code a mock filename for it
			mockFilename = ALL_FACILITIES;
		}
		// return value can never be null or empty, there is Defense against it
		return mockFilename;
	}

}
