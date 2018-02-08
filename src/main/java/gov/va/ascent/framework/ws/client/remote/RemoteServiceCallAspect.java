package gov.va.ascent.framework.ws.client.remote;

import java.lang.reflect.Method;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.ascent.framework.audit.AuditLogger;
import gov.va.ascent.framework.messages.MessageSeverity;
import gov.va.ascent.framework.service.ServiceResponse;
import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.util.Defense;

public class RemoteServiceCallAspect extends BaseRemoteServiceCallAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteServiceCallAspect.class);

	/**
	 * Around advice for partner service calls to audit the request and response.
	 * @param joinPoint
	 * @param webserviceTemplate
	 * @param request
	 * @param requestClass
	 * @return
	 * @throws Throwable
	 */
	// modified from ServiceValidationToMessageAspect class
	@SuppressWarnings("unchecked")
	@Around("standardRemoteServiceCallMethod() && args(webserviceTemplate, request, requestClass)")
	public Object aroundAdvice(final ProceedingJoinPoint joinPoint, final WebServiceTemplate webserviceTemplate, final AbstractTransferObject request, final Class<? extends AbstractTransferObject> requestClass) throws Throwable {

		LOGGER.info("@@@@@ Executing audit aspect");

		if(LOGGER.isDebugEnabled()){
			LOGGER.debug(this.getClass().getSimpleName() + " executing around method:" + joinPoint.toLongString());
		}

		LOGGER.info("@@@@@ Fetching Request");

		// fetch the request
		WebServiceTemplate serviceWebserviceTemplate = null;
		AbstractTransferObject serviceRequest = null;
		Class<? extends AbstractTransferObject> serviceRequestClass = null;
		if ((joinPoint.getArgs().length > 0) && (joinPoint.getArgs()[0] != null)) {
			serviceWebserviceTemplate = (WebServiceTemplate) joinPoint.getArgs()[0];
		}
		if ((joinPoint.getArgs().length > 1) && (joinPoint.getArgs()[1] != null)) {
			serviceRequest = (AbstractTransferObject) joinPoint.getArgs()[1];
		}
		if ((joinPoint.getArgs().length > 2) && (joinPoint.getArgs()[2] != null)) {
			serviceRequestClass = (Class<? extends AbstractTransferObject>) joinPoint.getArgs()[2];
		}

		Defense.notNull(serviceWebserviceTemplate);
		Defense.notNull(serviceRequest);
		Defense.notNull(serviceRequestClass);

		LOGGER.info("@@@@@ Audit Logging Request");

		final MethodSignature methodSignature = (MethodSignature) joinPoint.getStaticPart().getSignature();

		audit(MessageSeverity.INFO, methodSignature.getMethod(), serviceRequest);

		LOGGER.info("@@@@@ Creating Response");

		// start creating the response
		AbstractTransferObject serviceResponse = null;
		try {
			serviceResponse = (ServiceResponse) joinPoint.proceed();
		} catch (final Throwable throwable) {
			LOGGER.error(this.getClass().getSimpleName() + " encountered uncaught exception. Throwable Cause.",
					throwable.getCause());

			LOGGER.info("@@@@@ Audit Logging Error");

			audit(MessageSeverity.INFO, methodSignature.getMethod(), throwable);

			throw throwable;
		} finally {
			LOGGER.debug(this.getClass().getSimpleName() + " after method was called.");
		}

		LOGGER.info("@@@@@ Audit Logging Response");

		audit(MessageSeverity.INFO, methodSignature.getMethod(), serviceResponse);

		return serviceResponse;
	}

	/**
	 * Perform the audit logging.
	 * @param severity
	 * @param method
	 * @param serviceTransferObject
	 */
	private void audit(final MessageSeverity severity, final Method method, final Object serviceTransferObject) {
		if(severity.equals(MessageSeverity.ERROR) || severity.equals(MessageSeverity.FATAL)) {
			AuditLogger.error(
					BaseRemoteServiceCallAspect.getDefaultAuditableInstance(method),
					ReflectionToStringBuilder.toString(serviceTransferObject, ToStringStyle.JSON_STYLE));
		} else {
			AuditLogger.info(
					BaseRemoteServiceCallAspect.getDefaultAuditableInstance(method),
					ReflectionToStringBuilder.toString(serviceTransferObject, ToStringStyle.JSON_STYLE));
		}
	}
}
