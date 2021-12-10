package lapr2.framework.company.servicerequest;

import lapr2.framework.company.affectation.AffectationManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.manager.Manager;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.homeservices.HomeServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and retrieves all the {@link ServiceRequest}s of the Company. Also allows their
 * creation and validation.
 *
 * @author flow
 */
public class ServiceRequestManager extends Manager<ServiceRequest> {

	/**
	 * Constructs a new manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public ServiceRequestManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link ServiceRequest} returning it.
	 *
	 * @param client        the client
	 * @param postalAddress the postal address of the client
	 * @return the created service request
	 */
	public ServiceRequest createServiceRequest(Client client, PostalAddress postalAddress) {
		return new ServiceRequest(client, postalAddress);
	}

	/**
	 * Returns in form of set all the service requests that are not fully affected, i.e., that have at least
	 * one not affected service description.
	 *
	 * @return the set with the service requests
	 */
	public List<ServiceRequest> getNotFullyAffectedServiceRequests() {
		List<ServiceRequest> requests = new ArrayList<>();

		AffectationManager affectationManager = HomeServices.getInstance().getCompany().getDataManager().get(AffectationManager.class);

		for (ServiceRequest request : elements) {
			for (ServiceDescription description : request.getServiceDescriptionList().getServiceDescriptions()) {
				if (!affectationManager.isServiceDescriptionAffected(description)) {
					requests.add(request);
					break;
				}
			}
		}

		return requests;
	}

	/**
	 * Returns in form of set all the service requests that are from a client
	 *
	 * @param client the client
	 * @return the set with the service requests
	 */
	public List<ServiceRequest> getServicesRequests(Client client) {
		List<ServiceRequest> requests = new ArrayList<>();
		for (ServiceRequest request : elements) {
			if (request.getClient().equals(client)) {
				requests.add(request);
			}
		}
		return requests;
	}

	/**
	 * Verifies if a service request is valid.
	 *
	 * @param serviceRequest service request
	 * @return <code>true</code> if the service request is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(ServiceRequest serviceRequest) {
		return serviceRequest.isValid() && this.isValid(serviceRequest);
	}

	/**
	 * Verifies if a service request is valid globally.
	 *
	 * @param serviceRequest the service request
	 * @return <code>true</code> if the service request is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(ServiceRequest serviceRequest) {
		return !elements.contains(serviceRequest);
	}
}
