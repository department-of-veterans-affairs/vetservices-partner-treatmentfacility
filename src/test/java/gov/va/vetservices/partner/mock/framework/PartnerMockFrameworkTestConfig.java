package gov.va.vetservices.partner.mock.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.config.BaseYamlConfig;

/**
 * Fake Spring configuration used to test the partner mock framework classes
 *
 * @author jshrader
 */
@Configuration
@Profile({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
@ComponentScan(basePackages = "gov.va.vetservices.partner.mock.framework", excludeFilters = @Filter(Configuration.class))
@Import({ PartnerMockFrameworkConfig.class })
public class PartnerMockFrameworkTestConfig extends BaseYamlConfig
{

	/** The Constant DEFAULT_PROPERTIES. */
	private static final String DEFAULT_PROPERTIES = "classpath:/application.yml";

	/**
	 * The local environment configuration.
	 */
	@Configuration
	@PropertySource(DEFAULT_PROPERTIES)
	static class DefaultEnvironment extends BaseYamlEnvironment {
	}

}
