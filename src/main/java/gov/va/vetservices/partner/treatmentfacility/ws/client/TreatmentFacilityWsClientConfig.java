package gov.va.vetservices.partner.treatmentfacility.ws.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.annotation.PostConstruct;

import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;

import gov.va.ascent.framework.exception.InterceptingExceptionTranslator;
import gov.va.ascent.framework.log.PerformanceLogMethodInterceptor;
import gov.va.ascent.framework.util.Defense;
import gov.va.ascent.framework.ws.client.BaseWsClientConfig;

/**
 * Spring configuration for the TreatmentFacility Web Service Client.
 *
 * @author vgadda
 */
@Configuration
@ComponentScan(basePackages = { "gov.va.vetservices.partner.treatmentfacility.ws.client" },
		excludeFilters = @Filter(Configuration.class))
public class TreatmentFacilityWsClientConfig extends BaseWsClientConfig {

	/** Transfer Package Constant. */
	private static final String TRANSFER_PACKAGE = "gov.va.vetservices.partner.treatmentfacility.ws.client.transfer";

	/** the XSD for this web service */
	private static final String XSD = "xsd/medicalLookup-services.xsd";

	/** Exception class for exception interceptor */
	private static final String DEFAULT_EXCEPTION_CLASS =
			"gov.va.vetservices.partner.treatmentfacility.ws.client.TreatmentFacilityWsClientException";

	// ####### for test, member values are from src/test/resource/application.yml ######
	/**
	 * Boolean flag to indicate if we should log the JAXB error as an error or
	 * debug. In the test environment we get so many errors we don't want to polute
	 * logs, however in prod data is expected to be cleaner, logs less polluted and
	 * we may want these logged.
	 */
	@Value("${vetservices-partner-treatmentfacility.ws.client.logSchemaValidationFailureAsError:true}")
	public boolean logSchemaValidationFailureAsError;

	/** Username for treatmentfacility WS Authentication. */
	@Value("${vetservices-partner-treatmentfacility.ws.client.username}")
	private String username;

	/** pw for treatmentfacility WS Authentication. */
	@Value("${vetservices-partner-treatmentfacility.ws.client.password}")
	private String password;

	/** VA Application Name Header value. */
	@Value("${vetservices-partner-treatmentfacility.ws.client.vaApplicationName}")
	private String vaApplicationName;

	/**
	 * Executed after dependency injection is done to validate initialization.
	 */
	@PostConstruct
	public final void postConstruct() {
		Defense.hasText(username, "Partner username cannot be empty.");
		Defense.hasText(password, "Partner password cannot be empty.");
		Defense.hasText(vaApplicationName, "Partner vaApplicationName cannot be empty.");
	}

	/**
	 * WS Client object marhsaller for TreatmentFacility.
	 *
	 * @return object marshaller
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	@Qualifier("treatmentFacilityMarshaller")
	Jaxb2Marshaller treatmentFacilityMarshaller() {
		// CHECKSTYLE:ON
		final Resource[] schemas = new Resource[] { new ClassPathResource(XSD) };

		return getMarshaller(TRANSFER_PACKAGE, schemas, logSchemaValidationFailureAsError);
	}

	/**
	 * Axiom based WebServiceTemplate for the TreatmentFacility Web Service Client.
	 *
	 * @param endpoint
	 *            the endpoint
	 * @param readTimeout
	 *            the read timeout
	 * @param connectionTimeout
	 *            the connection timeout
	 * @return the web service template
	 * @throws KeyManagementException
	 *             on ssl error
	 * @throws UnrecoverableKeyException
	 *             on ssl error
	 * @throws NoSuchAlgorithmException
	 *             on ssl error
	 * @throws KeyStoreException
	 *             on ssl error
	 * @throws CertificateException
	 *             on ssl error
	 * @throws IOException
	 *             on ssl error
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	WebServiceTemplate treatmentFacilityWsClientAxiomTemplate(
			// CHECKSTYLE:ON
			@Value("${vetservices-partner-treatmentfacility.ws.client.endpoint}") final String endpoint,
			@Value("${vetservices-partner-treatmentfacility.ws.client.readTimeout:60000}") final int readTimeout,
			@Value("${vetservices-partner-treatmentfacility.ws.client.connectionTimeout:60000}") final int connectionTimeout) {

		Defense.hasText(endpoint, "TreatmentFacilityWsClientAxiomTemplate endpoint cannot be empty.");
		Defense.isTrue(readTimeout > 0, "TreatmentFacilityWsClientAxiomTemplate readTimeout cannot be zero.");
		Defense.isTrue(connectionTimeout > 0, "TreatmentFacilityWsClientAxiomTemplate connectionTimeout cannot be zero.");

		return createDefaultWebServiceTemplate(endpoint, readTimeout, connectionTimeout, treatmentFacilityMarshaller(),
				treatmentFacilityMarshaller(),
				new ClientInterceptor[] { getVAServiceWss4jSecurityInterceptor(username, password, vaApplicationName, null) });
	}

	/**
	 * PerformanceLogMethodInterceptor for the treatmentFacility Web Service Client
	 * <p>
	 * Handles performance related logging of the web service client response times.
	 *
	 * @param methodWarningThreshhold
	 *            the method warning threshhold
	 * @return the performance log method interceptor
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	PerformanceLogMethodInterceptor treatmentFacilityWsClientPerformanceLogMethodInterceptor(
			// CHECKSTYLE:ON
			@Value("${vetservices-partner-treatmentfacility.ws.client.methodWarningThreshhold:2500}") final Integer methodWarningThreshhold) {
		return getPerformanceLogMethodInterceptor(methodWarningThreshhold);
	}

	/**
	 * InterceptingExceptionTranslator for the treatmentFacility Web Service Client
	 * <p>
	 * Handles runtime exceptions raised by the web service client through runtime
	 * operation and communication with the remote service.
	 *
	 * @return the intercepting exception translator
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	InterceptingExceptionTranslator treatmentFacilityWsClientExceptionInterceptor() throws ClassNotFoundException {
		// CHECKSTYLE:ON
		return getInterceptingExceptionTranslator(DEFAULT_EXCEPTION_CLASS, PACKAGE_WSS_FOUNDATION_EXCEPTION);
	}

	/**
	 * A standard bean proxy to apply interceptors to the treatmentFacility web
	 * service client.
	 *
	 * @return the bean name auto proxy creator
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	BeanNameAutoProxyCreator treatmentFacilityWsClientBeanProxy() {
		// CHECKSTYLE:ON
		return getBeanNameAutoProxyCreator(new String[] { TreatmentFacilityWsClientImpl.BEAN_NAME }, new String[] {
				"treatmentFacilityWsClientExceptionInterceptor", "treatmentFacilityWsClientPerformanceLogMethodInterceptor" });
	}
}
