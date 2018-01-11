package gov.va.vetservices.partner.treatmentfacility.ws.client;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import gov.va.ascent.framework.exception.AscentRuntimeException;

/**
 * Trivial unit test to test the MedicalTreatmentFacilityWsClientException.
 *
 * Sort of a stupid test, but if we want line coverage up we need to test all
 * the constructors.
 *
 * @author vgadda
 */
public class TreatmentFacilityWsClientException_UnitTest {

	private static final String EXCEPTION_MESSAGE = "This is the message passed into the exception constructor!";

	private static final AscentRuntimeException RUNTIME_EXCEPTION = new AscentRuntimeException();

	@Test
	public void testExceptionConstructor_NoMessage_NoCause() {
		// assertions for constructor that doesn't take a message or a cause
		final TreatmentFacilityWsClientException exception_NoMessage_NoCause = new TreatmentFacilityWsClientException();
		assertNull(exception_NoMessage_NoCause.getMessage());
		assertNull(exception_NoMessage_NoCause.getCause());

	}

	@Test
	public void testExceptionConstructor_YesMessage_NoCause() {
		// assertions for constructor that takes a message but doesn't take a cause
		final TreatmentFacilityWsClientException exception_YesMessage_NoCause =
				new TreatmentFacilityWsClientException(EXCEPTION_MESSAGE);
		assertEquals(EXCEPTION_MESSAGE, exception_YesMessage_NoCause.getMessage());
		assertNull(exception_YesMessage_NoCause.getCause());

	}

	@Test
	public void testExceptionConstructor_NoMessage_YesCause() {
		// assertions for constructor that doesn't take a message but does a cause
		final TreatmentFacilityWsClientException exception_NoMessage_YesCause =
				new TreatmentFacilityWsClientException(RUNTIME_EXCEPTION);
		assertNull(exception_NoMessage_YesCause.getMessage());
		assertEquals(RUNTIME_EXCEPTION, exception_NoMessage_YesCause.getCause());
	}

	@Test
	public void testExceptionConstructor_YesMessage_YesCause() {
		// assertions for constructor that takes a message and a cause
		final TreatmentFacilityWsClientException exception_YesMessage_YesCause =
				new TreatmentFacilityWsClientException(EXCEPTION_MESSAGE, RUNTIME_EXCEPTION);
		assertEquals(EXCEPTION_MESSAGE, exception_YesMessage_YesCause.getMessage());
		assertEquals(RUNTIME_EXCEPTION, exception_YesMessage_YesCause.getCause());
	}
}
