package gov.va.vetservices.partner.treatmentfacility.ws.client;

import gov.va.ascent.framework.exception.AscentRuntimeException;

/**
 * Root hierarchy of exceptions which indicates there was an exception/error in
 * the MedicalTreatmentFacility web service
 *
 * @author vgadda
 */
public class TreatmentFacilityWsClientException extends AscentRuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7282496605582623526L;

	/**
	 * Instantiates a new exception.
	 */
	public TreatmentFacilityWsClientException() {
		super();
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param message the message
	 * @param cause the cause
	 */
	public TreatmentFacilityWsClientException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param message the message
	 */
	public TreatmentFacilityWsClientException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new exception.
	 *
	 * @param cause the cause
	 */
	public TreatmentFacilityWsClientException(final Throwable cause) {
		super(cause);
	}
}
