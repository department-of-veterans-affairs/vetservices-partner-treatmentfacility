package gov.va.vetservices.partner.treatmentfacility.ws.remote;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
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
@Component(RemoteServiceCall.BEAN_NAME)
public class RemoteServiceCallMock extends AbstractRemoteServiceCallMock implements RemoteServiceCall {

	@Override
	public AbstractTransferObject callRemoteService(final WebServiceTemplate webserviceTemplate,
			final AbstractTransferObject request, final Class<? extends AbstractTransferObject> requestClass) {

		return callMockService(webserviceTemplate, request, requestClass);
	}

	@Override
	protected String getKeyForMockResponse(final AbstractTransferObject request) {
		Defense.notNull(request);

		String mockFilename = null;

		if(request.getClass().isAssignableFrom(GetVAMedicalTreatmentFacilityList.class)) { //request instanceof GetVAMedicalTreatmentFacilityList) {
			if(StringUtils.isNotBlank(((GetVAMedicalTreatmentFacilityList) request).getStateCd())) {
				// specify a mock filename that is the state code
				mockFilename = ((GetVAMedicalTreatmentFacilityList) request).getStateCd();
			}
		}
		if(StringUtils.isBlank(mockFilename)) {
			// the API allows to retrieve all facilities if input state code is null, so hard-code a mock filename for it
			mockFilename = "AllFacilities";
		}
		// return value can never be null or empty, there is Defense against it
		return mockFilename;
	}

}
