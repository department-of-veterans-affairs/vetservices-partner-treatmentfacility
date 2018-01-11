package gov.va.vetservices.partner.treatmentfacility.ws.client;

import java.io.IOException;
import java.util.Arrays;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import gov.va.ascent.framework.config.AscentCommonSpringProfiles;
import gov.va.ascent.framework.ws.client.BaseWsClientSimulator;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.GetVAMedicalTreatmentFacilityListResponse;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.MedicalTreatmentFacilityListReturnVO;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.MedicalTreatmentFacilityListReturnVO.MedicalTreatmentFacilityList;
import gov.va.vetservices.partner.treatmentfacility.ws.client.transfer.MedicalTreatmentFacilityReturnVO;

/**
 * This is a simulation / mock of a real web service client. No remote web service calls are actually made as this is a pure locally
 * running simulation. The advantage here is we can leverage Java and our IDE to code richer simulations of the remote services and we
 * don't need other tools/techniques such as locally running soapUI simulators etc.
 *
 * @author vgadda
 */
@Component(TreatmentFacilityWsClientSimulator.BEAN_NAME)
@Profile({ AscentCommonSpringProfiles.PROFILE_REMOTE_CLIENT_SIMULATORS,
	TreatmentFacilityWsClient.PROFILE_TREATMENT_FACILITY_WSCLIENT_REMOTE_CLIENT_SIM})
public class TreatmentFacilityWsClientSimulator extends BaseWsClientSimulator implements TreatmentFacilityWsClient {

	/** The Constant BEAN_NAME. */
	public static final String BEAN_NAME = "treatmentFacilityWsClientSimulator";

	/** The Constant LOGGER. */
	private static final Logger LOGGER = LoggerFactory.getLogger(TreatmentFacilityWsClientSimulator.class);

	/**
	 * Post construct.
	 * @throws IOException exception
	 */
	@PostConstruct
	public final void postConstruct() throws IOException {
		LOGGER.warn("!!!TreatmentFacility SIMULATOR LOADED!!!");
	}

	@Override
	public final GetVAMedicalTreatmentFacilityListResponse getVAMedicalTreatmentFacilityList(
			final GetVAMedicalTreatmentFacilityList request) {
		final GetVAMedicalTreatmentFacilityListResponse response = new GetVAMedicalTreatmentFacilityListResponse();
		final MedicalTreatmentFacilityListReturnVO medicalTreatmentFacilityListReturnVO = new MedicalTreatmentFacilityListReturnVO();
		final MedicalTreatmentFacilityList medicalTreatmentFacilityList = new MedicalTreatmentFacilityList();
		final MedicalTreatmentFacilityReturnVO facility1 = new MedicalTreatmentFacilityReturnVO();


		facility1.setFacilityNm("MINNEAPOLIS VA MEDICAL CENTER");
		facility1.setPtcpntId(12965800);
		facility1.setCityNm("MINNEAPOLIS");
		facility1.setPostalCd("MN");

		final MedicalTreatmentFacilityReturnVO facility2 = new MedicalTreatmentFacilityReturnVO();
		facility2.setFacilityNm("BIRMINGHAM VA MEDICAL CENTER");
		facility2.setPtcpntId(12965732);
		facility2.setCityNm("BIRMINGHAM");
		facility2.setPostalCd("AL");

		final MedicalTreatmentFacilityReturnVO facility3 = new MedicalTreatmentFacilityReturnVO();
		facility3.setFacilityNm("JOHN L. MCCLELLAN MEMORIAL VETERANS HOSPITAL");
		facility3.setPtcpntId(12965737);
		facility3.setCityNm("LITTLE ROCK");
		facility3.setPostalCd("AR");

		final MedicalTreatmentFacilityReturnVO facility4 = new MedicalTreatmentFacilityReturnVO();
		facility4.setFacilityNm("ORLANDO VA MEDICAL CENTER");
		facility4.setPtcpntId(600029581);
		facility4.setCityNm("ORLANDO");
		facility4.setPostalCd("FL");

		final MedicalTreatmentFacilityReturnVO facility5 = new MedicalTreatmentFacilityReturnVO();
		facility5.setFacilityNm("VA TEXAS VALLEY COASTAL BEND HEALTH CARE SYSTEM");
		facility5.setPtcpntId(600113317);
		facility5.setCityNm("HARLINGEN");
		facility5.setPostalCd("TX");

		final MedicalTreatmentFacilityReturnVO[] list = {facility1, facility2, facility3, facility4, facility5};

		if(request.getStateCd() == null) {
			medicalTreatmentFacilityList.getMedicalTreatmentFacility().addAll(Arrays.asList(list));
		} else {
			for(final MedicalTreatmentFacilityReturnVO item:list) {
				if(item.getPostalCd().equals(request.getStateCd())) {
					medicalTreatmentFacilityList.getMedicalTreatmentFacility().add(item);
				}
			}
		}

		medicalTreatmentFacilityListReturnVO.setMedicalTreatmentFacilityList(medicalTreatmentFacilityList);
		response.setMedicalTreatmentFacilityListReturn(medicalTreatmentFacilityListReturnVO);
		return response;
	}


}
