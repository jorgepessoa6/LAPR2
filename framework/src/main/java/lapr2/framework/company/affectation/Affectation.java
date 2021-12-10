package lapr2.framework.company.affectation;

import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder;
import lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrderManager;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the affectation which is the connection between a service provider and a given service,
 * providing access to its request, service, provider and schedule.
 *
 * @author flow
 */
public class Affectation implements Serializable {

	private static final long serialVersionUID = -6877590098028968700L;

	/**
	 * The service request.
	 */
	@Getter
	private final ServiceRequest serviceRequest;

	/**
	 * The service description. The service description should be one from the service request.
	 */
	@Getter
	private final ServiceDescription serviceDescription;

	/**
	 * The service provider.
	 */
	@Getter
	private final ServiceProvider serviceProvider;

	/**
	 * The schedule.
	 */
	@Getter
	private final Schedule schedule;

	/**
	 * The state of the affectation.
	 */
	@Getter
	@Setter
	private State state;

	/**
	 * Constructs a new affectation letting the state as <code>SUBMITTED</code>.
	 *
	 * @param serviceRequest     the service request
	 * @param serviceDescription the service description
	 * @param serviceProvider    the service provider
	 * @param schedule           the schedule
	 */
	public Affectation(ServiceRequest serviceRequest, ServiceDescription serviceDescription, ServiceProvider serviceProvider, Schedule schedule) {
		this.serviceRequest = serviceRequest;
		this.serviceDescription = serviceDescription;
		this.serviceProvider = serviceProvider;
		this.schedule = schedule;
		this.state = State.SUBMITTED;
	}

	/**
	 * Creates a service execution order for this affectation.
	 *
	 * @return <code>true</code> if the service execution order was successfully added, <code>false</code> otherwise
	 */
	public boolean affect() {
		this.state = State.CONFIRMED;

		ServiceExecutionOrderManager serviceExecutionOrderManager = HomeServices.getInstance().getCompany().getDataManager().get(ServiceExecutionOrderManager.class);

		ServiceExecutionOrder serviceExecutionOrder = serviceExecutionOrderManager.createServiceExecutionOrder(this);

		return serviceExecutionOrderManager.isSecure(serviceExecutionOrder) && serviceExecutionOrderManager.add(serviceExecutionOrder);
	}

	/**
	 * Verifies if this instance is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Affectation)) return false;
		Affectation that = (Affectation) o;
		return getServiceRequest().equals(that.getServiceRequest()) &&
				getServiceDescription().equals(that.getServiceDescription()) &&
				getServiceProvider().equals(that.getServiceProvider()) &&
				getSchedule().equals(that.getSchedule()) &&
				getState() == that.getState();
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the unique code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getServiceRequest(), getServiceDescription(), getServiceProvider(), getSchedule(), getState());
	}

	/**
	 * Represents a state in which the state can be.
	 */
	public enum State {
		SUBMITTED,
		CONFIRMED,
		REJECTED
	}

	@Override
	public String toString() {
		return (serviceRequest.toString() + " : " + serviceDescription.toString());
	}
}
