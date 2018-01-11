package gov.va.vetservices.partner.treatmentfacility.ws.client;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.vetservices.partner.mock.framework.PartnerMockFrameworkTestConfig;

/**
 * Unit test of PersonWsClientImpl.
 */
// ignored for now as its integration test and requires SOAP UI to be running
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles({ AscentCommonSpringProfiles.PROFILE_ENV_LOCAL_INT, AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_IMPLS })
@ContextConfiguration(inheritLocations = false,
classes = { TreatmentFacilityWsClientConfig.class, PartnerMockFrameworkTestConfig.class })
public class TreatmentFacilityWsClientImpl_UnitTest {

	@Autowired
	TreatmentFacilityWsClient treatmentFacilityWsClient;



	@Before
	public void setUp() {
	}

	@After
	public void clear() {
	}

	@Test
	public void testGetVAMedicalTreatmentFacilityList() {
		assertNotNull(treatmentFacilityWsClient);
	}
}
