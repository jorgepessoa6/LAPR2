package lapr2.framework.company.servicerequest;

import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a service request that catalogues its client, its postal address,
 * its list of service descriptions and its list of schedule preferences.
 *
 * @author flow
 */
public class ServiceRequest implements Serializable {

	private static final long serialVersionUID = -1617533487478880840L;

	/**
	 * The client who makes the service request.
	 */
	@Getter
	private final Client client;

	/**
	 * The postal address of the service request.
	 */
	@Getter
	private final PostalAddress postalAddress;

	/**
	 * The list of service descriptions of the service request.
	 */
	@Getter
	private ServiceDescriptionList serviceDescriptionList;

	/**
	 * The list of schedule preferences of the service request.
	 */
	@Getter
	private ScheduleList scheduleList;

	/**
	 * The cost of the service request
	 */
	@Getter
	@Setter
	private double cost;

	/**
	 * Constructs a new service request.
	 *
	 * @param client        the client that made the service request
	 * @param postalAddress the postal address of the service request
	 */
	public ServiceRequest(Client client, PostalAddress postalAddress) {
		this.client = client;
		this.postalAddress = postalAddress;
		this.serviceDescriptionList = new ServiceDescriptionList();
		this.scheduleList = new ScheduleList();
		this.cost = -1;
	}

	/**
	 * Computes the total cost of the request.
	 *
	 * @return the total cost of the request
	 */
	public double getTotalCost() {
		ArrayList<ServiceDescription> serviceDescriptions = this.getServiceDescriptionList().getServiceDescriptions();

		double totalCost = 0;

		for (ServiceDescription serviceDescription : serviceDescriptions) {
			totalCost += serviceDescription.getService().getCost(serviceDescription.getDuration());
		}

		GeographicalAreaManager geographicalAreaManager = HomeServices.getInstance().getCompany().getDataManager().get(GeographicalAreaManager.class);
		GeographicalArea geographicalArea = geographicalAreaManager.getClosestGeographicalArea(postalAddress.getPostCode());

		totalCost += geographicalArea.getTravelCost();

		return totalCost;
	}

	/**
	 * Verifies if a service request is valid locally.
	 *
	 * @return <code>true</code> if the service request is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return client != null &&
				postalAddress != null &&
				client.getPostalAddressList().getElements().contains(postalAddress) &&
				!getScheduleList().getSchedulePreferencesList().isEmpty() &&
				!getServiceDescriptionList().getServiceDescriptions().isEmpty();
	}

	/**
	 * Verifies if this request is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceRequest)) return false;
		ServiceRequest that = (ServiceRequest) o;
		return Double.compare(that.getCost(), getCost()) == 0 &&
				Objects.equals(getClient(), that.getClient()) &&
				Objects.equals(getPostalAddress(), that.getPostalAddress()) &&
				Objects.equals(getServiceDescriptionList(), that.getServiceDescriptionList()) &&
				Objects.equals(getScheduleList(), that.getScheduleList());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getClient(), getPostalAddress(), getServiceDescriptionList(), getScheduleList(), getCost());
	}
}
