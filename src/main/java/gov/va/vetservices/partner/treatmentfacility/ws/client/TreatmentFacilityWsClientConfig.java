package gov.va.vetservices.partner.treatmentfacility.ws.client;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
import gov.va.ascent.framework.ws.client.BaseWsClientConfig;
import gov.va.ascent.framework.ws.client.WsClientSimulatorMarshallingInterceptor;

/**
 * Spring configuration for the MedicalTreatmentFacility Web Service Client.
 *
 * @author vgadda
 */
@Configuration
@ComponentScan(basePackages = {"gov.va.vetservices.partner.medicaltreatmentfacility.ws.client" },
excludeFilters = @Filter(Configuration.class))
public class TreatmentFacilityWsClientConfig extends BaseWsClientConfig {

	/**
	 * Transfer Package Constant.
	 */
	private static final String TRANSFER_PACKAGE = "gov.va.vetservices.partner.medicaltreatmentfacility.ws.transfer";

	/**
	 * Exception class for exception interceptor
	 */
	private static final String DEFAULT_EXCEPTION_CLASS =
			"gov.va.vetservices.partner.medicaltreatmentfacility.ws.client.MedicalTreatmentFacilityWsClientException";

	/**
	 * exclude package for exception interceptor
	 */
	private static final String EXCLUDE_EXCEPTION_PKG = "gov.va.vetservices.partner.medicaltreatmentfacility.ws.client";
	/**
	 * Boolean flag to indicate if we should log the JAXB error as an error nor debug. In the test environment we get so many errors we
	 * don't want to polute logs, however in prod data is expected to be cleaner, logs less polluted and we may want these logged.
	 */
	@Value("${wss-partner-medicaltreatmentfacility.ws.client.logSchemaValidationFailureAsError:true}")
	private boolean logSchemaValidationFailureAsError;

	/**
	 * Username for medicaltreatmentfacility WS Authentication.
	 */
	@Value("${wss-partner-medicaltreatmentfacility.ws.client.username}")
	private String username;

	/**
	 * pw for medicaltreatmentfacility WS Authentication.
	 */
	@Value("${wss-partner-medicaltreatmentfacility.ws.client.password}")
	private String password;

	/**
	 * VA Application Name Header value.
	 */
	@Value("${wss-partner-medicaltreatmentfacility.ws.client.vaApplicationName}")
	private String vaApplicationName;

	/**
	 * WS Client object marhsaller for MedicalTreatmentFacility.
	 *
	 * @return object marshaller
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	@Qualifier("medicalTreatmentFacilityMarshaller")
	Jaxb2Marshaller medicalTreatmentFacilityMarshaller() {
		// CHECKSTYLE:ON
		final Resource[] schemas = new Resource[] { new ClassPathResource("xsd/medicalLookup-services.xsd") };

		return getMarshaller(TRANSFER_PACKAGE, schemas, logSchemaValidationFailureAsError);
	}

	/**
	 * Axiom based WebServiceTemplate for the MedicalTreatmentFacility Web
	 * Service Client.
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
	@Qualifier("medicalTreatmentFacilityWsClient.axiom")
	WebServiceTemplate medicalTreatmentFacilityWsClientAxiomTemplate(
			// CHECKSTYLE:ON
			@Value("${wss-partner-medicaltreatmentfacility.ws.client.endpoint}") final String endpoint,
			@Value("${wss-partner-medicaltreatmentfacility.ws.client.readTimeout:60000}") final int readTimeout,
			@Value("${wss-partner-medicaltreatmentfacility.ws.client.connectionTimeout:60000}") final int connectionTimeout)
					throws KeyManagementException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException,
					CertificateException, IOException {

		return createDefaultWebServiceTemplate(endpoint, readTimeout, connectionTimeout,
				medicalTreatmentFacilityMarshaller(), medicalTreatmentFacilityMarshaller(),
				new ClientInterceptor[] { getVAServiceWss4jSecurityInterceptor(username, password, vaApplicationName, null) });
	}

	/**
	 * PerformanceLogMethodInterceptor for the medicalTreatmentFacility Web
	 * Service Client
	 * <p>
	 * Handles performance related logging of the web service client response
	 * times.
	 *
	 * @param methodWarningThreshhold
	 *            the method warning threshhold
	 * @return the performance log method interceptor
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	PerformanceLogMethodInterceptor medicalTreatmentFacilityWsClientPerformanceLogMethodInterceptor(
			// CHECKSTYLE:ON
			@Value("${wss-partner-medicaltreatmentfacility.ws.client.methodWarningThreshhold:2500}")
			final Integer methodWarningThreshhold) {
		return getPerformanceLogMethodInterceptor(methodWarningThreshhold);
	}

	/**
	 * InterceptingExceptionTranslator for the medicalTreatmentFacility Web
	 * Service Client
	 * <p>
	 * Handles runtime exceptions raised by the web service client through
	 * runtime operation and communication with the remote service.
	 *
	 * @return the intercepting exception translator
	 * @throws ClassNotFoundException
	 *             the class not found exception
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	InterceptingExceptionTranslator medicalTreatmentFacilityWsClientExceptionInterceptor()
			throws ClassNotFoundException {
		// CHECKSTYLE:ON
		final InterceptingExceptionTranslator interceptingExceptionTranslator =
				getInterceptingExceptionTranslator(DEFAULT_EXCEPTION_CLASS, PACKAGE_WSS_FOUNDATION_EXCEPTION);
		final Set<String> exclusionSet = new HashSet<String>();
		exclusionSet.add(PACKAGE_WSS_FOUNDATION_EXCEPTION);
		exclusionSet.add(EXCLUDE_EXCEPTION_PKG);
		interceptingExceptionTranslator.setExclusionSet(exclusionSet);
		return interceptingExceptionTranslator;
	}

	/**
	 * A standard bean proxy to apply interceptors to the
	 * medicalTreatmentFacility web service client.
	 *
	 * @return the bean name auto proxy creator
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	BeanNameAutoProxyCreator medicalTreatmentFacilityWsClientBeanProxy() {
		// CHECKSTYLE:ON
		final String[] beanNames = { TreatmentFacilityWsClientImpl.BEAN_NAME, TreatmentFacilityWsClientSimulator.BEAN_NAME };

		// load each interceptor needed for the above beans.
		final String[] interceptorNames =
			{ "medicalTreatmentFacilityWsClientExceptionInterceptor",
			"medicalTreatmentFacilityWsClientPerformanceLogMethodInterceptor" };

		final BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
		creator.setBeanNames(beanNames);
		creator.setInterceptorNames(interceptorNames);
		return creator;
	}

	/**
	 * Ws client simulator marshalling interceptor, so that requests and responses to the simulator are passed through the marshaller
	 * to ensure we don't have any Java-to-XML conversion surprises if we leverage simulators heavily in development and then start
	 * using real web services later on.
	 *
	 * @return the ws client simulator marshalling interceptor
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	WsClientSimulatorMarshallingInterceptor medicalTreatmentFacilityWsClientSimulatorMarshallingInterceptor() {
		// CHECKSTYLE:ON
		final Map<String, Jaxb2Marshaller> marshallerForPackageMap = new HashMap<String, Jaxb2Marshaller>();
		marshallerForPackageMap.put(TRANSFER_PACKAGE, medicalTreatmentFacilityMarshaller());

		return new WsClientSimulatorMarshallingInterceptor(marshallerForPackageMap);
	}

	/**
	 * A standard bean proxy to apply interceptors to the web service client simulations that we don't need/want to apply to real web
	 * service client impls.
	 *
	 * @return the bean name auto proxy creator
	 */
	// ignoring DesignForExtension check, we cannot make this spring
	// bean method private or final
	// CHECKSTYLE:OFF
	@Bean
	BeanNameAutoProxyCreator medicalTreatmentFacilityWsClientSimulatorProxy() {
		// CHECKSTYLE:ON
		final String[] beanNames = { TreatmentFacilityWsClientSimulator.BEAN_NAME };

		// load each interceptor needed for the above beans.
		final String[] interceptorNames = { "medicalTreatmentFacilityWsClientSimulatorMarshallingInterceptor" };

		final BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
		creator.setBeanNames(beanNames);
		creator.setInterceptorNames(interceptorNames);
		return creator;
	}
}
