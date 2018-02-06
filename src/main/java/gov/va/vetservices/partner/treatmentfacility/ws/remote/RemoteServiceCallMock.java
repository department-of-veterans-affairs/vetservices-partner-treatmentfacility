package gov.va.vetservices.partner.treatmentfacility.ws.remote;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.util.Defense;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.ws.remote.AbstractRemoteServiceCallMock;
import gov.va.vetservices.partner.ws.remote.RemoteServiceCall;

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

		final String stateAbbr = ((GetVAMedicalTreatmentFacilityList) request).getStateCd();
		Defense.hasText(stateAbbr);

		return stateAbbr;
	}

}
