package gov.va.vetservices.partner.mock.framework;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.config.BasePropertiesConfig;

/**
 * Fake Spring configuration used to test the partner mock framework classes
 *
 * @author jshrader
 */
@Configuration
@Profile({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
@ComponentScan(basePackages = "gov.va.vetservices.partner.mock.framework", excludeFilters = @Filter(Configuration.class))
@Import({ PartnerMockFrameworkConfig.class })
public class PartnerMockFrameworkTestConfig extends BasePropertiesConfig
{

	/** ISSUE
	 * The code below was originally in PartnerMockFrameworkTestPropertiesConfig (as in demo-partner)
	 * However, the class refused to be @Import-ed and caused failure to load properties.
	 * Putting the config here solved the problem, and also simplifies the config (one less spring class)
	 */

	/** The Constant APP_NAME. */
	public static final String APP_NAME = "wss-partner-mock-framework-test";

	/** The Constant DEFAULT_PROPERTIES. */
	private static final String DEFAULT_PROPERTIES = "classpath:/config/" + APP_NAME + ".properties";

	/**
	 * The local environment configuration.
	 */
	@Configuration
	@PropertySource(DEFAULT_PROPERTIES)
	static class DefaultEnvironment extends BasePropertiesEnvironment {
	}

}
