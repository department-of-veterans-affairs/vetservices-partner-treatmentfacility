package gov.va.vetservices.partner.ws.remote;

import static org.springframework.ws.test.client.RequestMatchers.payload;
import static org.springframework.ws.test.client.ResponseCreators.withPayload;

import java.io.IOException;
import java.text.MessageFormat;

import javax.xml.transform.Source;

import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.test.client.MockWebServiceServer;
import org.springframework.xml.transform.ResourceSource;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import gov.va.ascent.framework.transfer.AbstractTransferObject;
import gov.va.ascent.framework.util.Defense;
import gov.va.vetservices.partner.treatmentfacility.ws.client.TreatmentFacilityWsClientException;

/**
 * <p>
 * Provides abstract and concrete methods to simplify mocking of
 * {@link RemoteServiceCall} mock/simulator implementations.
 * </p>
 * <p>
 * Classes that are simulator profile implementations for mocking should extend
 * this class.
 * </p>
 *
 * @author aburkholder
 */
public abstract class AbstractRemoteServiceCallMock {

	/** Constant for the filename template for mocked files */
	protected static final String MOCK_FILENAME_TEMPLATE = "mocks/{0}.xml";

	/**
	 * <p>
	 * Implements the logic to extract a key value from the request object, that can
	 * be used as the replaceable parameter in {@literal MOCK_FILENAME_TEMPLATE}.
	 * </p>
	 * <p>
	 * Implementation of this method must not return {@code null} or empty string
	 * (there is a Defense for this). Implementation could be as simple as:
	 * </p>
	 *
	 * <pre>
	 * {@code
	 * 	&#64;Override
	 * 	String getKeyForMockResponse(final AbstractTransferObject request) {
	 * 		Defense.notNull(request);
	 * 		// cast the request back to the original request transfer object type so we can get the state code
	 * 		final String stateAbbr = ((GetVAMedicalTreatmentFacilityList) request).getStateCd();
	 * 		Defense.hasText(stateAbbr);
	 * 		return stateAbbr;
	 * 	}
	 * }
	 * </pre>
	 *
	 * @param request
	 *            the request object received from the calling functional test or or
	 *            unit test.
	 * @return String the key to be used to retrieve the mock response XML
	 */
	protected abstract String getKeyForMockResponse(AbstractTransferObject request);

	/**
	 * Execution of a mocked remote call to the web service identified by the
	 * WebServiceTemplate. There is a bean of the same name for
	 * PROFILE_REMOTE_CLIENT_IMPLS in the ClientConfig class.
	 *
	 * @param webserviceTemplate
	 *            the template for the web service being called
	 * @param request
	 *            the request (a subclass of AbstractTransferObject)
	 * @param requestClass
	 *            the actual Class of the request object
	 * @return AbstractTransferObject the response from the remote web service (cast
	 *         it to the desired response type)
	 */
	protected AbstractTransferObject callMockService(final WebServiceTemplate webserviceTemplate,
			final AbstractTransferObject request, final Class<? extends AbstractTransferObject> requestClass) {

		Defense.notNull(webserviceTemplate, "To callMockService, the WebServiceTemplate cannot be null.");
		Defense.notNull(request, "To callMockService, the transfer object 'request' cannot be null.");
		Defense.notNull(requestClass,
				"To callMockService, the 'requestClass' of the request transfer object cannot be null.");

		final MockWebServiceServer mockSoapServer = MockWebServiceServer.createServer(webserviceTemplate);

		final Source requestPayload = marshalMockRequest((Jaxb2Marshaller) webserviceTemplate.getMarshaller(), request,
				requestClass);
		final Source responsePayload = readMockResponseByKey(request, requestClass);

		mockSoapServer.expect(payload(requestPayload)).andRespond(withPayload(responsePayload));

		final AbstractTransferObject response = (AbstractTransferObject) webserviceTemplate
				.marshalSendAndReceive(requestClass.cast(request));

		mockSoapServer.verify();

		return response;
	}

	/**
	 * Mock helper for functional tests. Marshals an AbstractTransferObject request
	 * object to a StringSource formatted as XML.
	 *
	 * @param marshaller
	 *            a JAXB2 marshaler
	 * @param request
	 *            the request transfer object generated by xjc
	 * @param requestClass
	 *            the response transfer object generated by xjc
	 * @return StringSource the xml
	 */
	private StringSource marshalMockRequest(final Jaxb2Marshaller marshaller, final AbstractTransferObject request,
			final Class<?> requestClass) {
		final StringResult result = new StringResult();
		marshaller.marshal(requestClass.cast(request), result);
		return new StringSource(result.toString());
	}

	/**
	 * Mock helper for functional tests. Based on the key provided by implementing
	 * code in the abstract
	 * {@code getKeyForMockResponse(AbstractTransferObject request)} method.
	 *
	 * @param request
	 * @param requestClass
	 * @return
	 * @throws IOException
	 */
	private ResourceSource readMockResponseByKey(final AbstractTransferObject request, final Class<?> requestClass) {
		// get the key from the calling class
		final String key = getKeyForMockResponse(request);

		Defense.hasText(key);

		// read the resource
		ResourceSource resource = null;
		try {
			resource = new ResourceSource(new ClassPathResource(MessageFormat.format(MOCK_FILENAME_TEMPLATE, key)));
		} catch (final IOException e) {
			throw new TreatmentFacilityWsClientException("Could not read mock XML file 'mocks/" + key
					+ ".xml'. Please make sure this response file exists in the main/resources directory.", e);
		}
		return resource;
	}

}
