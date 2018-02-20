package gov.va.vetservices.partner.treatmentfacility.ws.client.remote;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.ws.client.remote.RemoteServiceCall;

/**
 * Implements the {@link RemoteServiceCall} interface for the remote client impls spring profile
 */
@Profile(AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS)
@Component(RemoteServiceCallImpl.BEAN_NAME_LOCAL)
public class RemoteServiceCallImpl implements RemoteServiceCall {

	/** The spring bean name for implementation. MUST BE UNIQUE ACROSS ALL PARTNER JARS */
	static final String BEAN_NAME_LOCAL = "treatmentFacilityRemoteServiceCall";

	@Override
	public AbstractTransferObject callRemoteService(final WebServiceTemplate webserviceTemplate, final AbstractTransferObject request,
			final Class<? extends AbstractTransferObject> requestClass) {

		return (AbstractTransferObject) webserviceTemplate.marshalSendAndReceive(requestClass.cast(request));
	}

}
