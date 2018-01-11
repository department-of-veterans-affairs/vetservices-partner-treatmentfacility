package gov.va.vetservices.partner.treatmentfacility.ws.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_ENV_LOCAL_INT, AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS })
/** ISSUE
 * Had to remove from @ContextConfiguration:  , PartnerMockFrameworkTestConfig.class })
 * For some reason, causes properties to not be loaded;.
 */
@ContextConfiguration(inheritLocations = false, classes = { TreatmentFacilityWsClientConfig.class })
public class TreatmentFacilityWsClientSimulator_UnitTest extends TestCase {

	@Autowired
	TreatmentFacilityWsClient medicalTreatmentFacilityWsClient;


	@Override
	@Before
	public void setUp() {

	}

	@After
	public void clear() {
	}

	@Test
	public void testGetVAMedicalTreatmentFacilityList() {
		assertTrue(true);
	}
}
