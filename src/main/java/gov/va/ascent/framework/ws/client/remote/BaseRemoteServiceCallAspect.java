package gov.va.ascent.framework.ws.client.remote;

import java.lang.reflect.Method;

import org.aspectj.lang.annotation.Pointcut;

import gov.va.ascent.framework.audit.AuditEventData;
import gov.va.ascent.framework.audit.AuditEvents;

/**
 * This is the base class for aspects involved in remote calls to partner services.
 *
 * @author aburkholder
 */
//@Aspect
//@Order(-9999)
public class BaseRemoteServiceCallAspect {

	protected BaseRemoteServiceCallAspect() {
		super();
	}

	/**
	 * This pointcut reflects a standard remote partner service call.
	 * A partner service call is identified by implementation of the RemoteServiceCall interface.
	 */
	@Pointcut("execution(* gov.va.ascent.framework.ws.client.remote.RemoteServiceCall.*(..))")
	protected static final void standardRemoteServiceCallMethod() {
		//Do Nothing
	}

	/**
	 * Gets the default auditable instance.
	 *
	 * @param method the method
	 * @return the auditable instance
	 */
	public static AuditEventData getDefaultAuditableInstance(final Method method) {
		//TODO REQUEST_RESPONSE should be PARTNER_REQUEST_RESPONSE - need to add it to AuditEvents in framework
		if(method != null) {
			return new AuditEventData(AuditEvents.REQUEST_RESPONSE, method.getName(), method.getDeclaringClass().getName());
		} else {
			return new AuditEventData(AuditEvents.REQUEST_RESPONSE, "", "");
		}
	}
}
