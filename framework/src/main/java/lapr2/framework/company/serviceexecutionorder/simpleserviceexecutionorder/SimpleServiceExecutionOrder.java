package lapr2.framework.company.serviceexecutionorder.simpleserviceexecutionorder;

import lapr2.framework.company.servicerequest.Schedule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a {@link lapr2.framework.company.serviceexecutionorder.ServiceExecutionOrder} in a more simple way,
 * providing access to the client's name, the service category and type and the order schedule and address.
 *
 * @author flow
 */
public class SimpleServiceExecutionOrder {

	/**
	 * The name of the client.
	 */
	@Getter
	private final String client;

	/**
	 * The category of the service of the order.
	 */
	@Getter
	private final String category;

	/**
	 * The schedule of the order.
	 */
	@Getter
	private final LocalDateTime schedule;

	/**
	 * The type of service.
	 */
	@Getter
	private final String serviceType;

	/**
	 * The postal address of the order.
	 */
	@Getter
	private final String postalAddress;

	@Getter
	private final double distance;

	/**
	 * Constructs a new simple service execution order.
	 *
	 * @param client        the name of the client
	 * @param category      the category of the service
	 * @param scheduleStr   the string representative of the schedule
	 * @param serviceType   the type of the service
	 * @param postalAddress the postal address of the order
	 * @param distance      the distance between the service provider facilities and the client facilities
	 */
	public SimpleServiceExecutionOrder(String client, String category, String scheduleStr, String serviceType, String postalAddress, double distance) {
		this.client = client;
		this.category = category;
		this.schedule = LocalDateTime.parse(scheduleStr, DateTimeFormatter.ofPattern(Schedule.TIME_FORMAT));
		this.serviceType = serviceType;
		this.postalAddress = postalAddress;
		this.distance = distance;
	}

	/**
	 * Verifies if this simple service execution order is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof SimpleServiceExecutionOrder)) return false;
		SimpleServiceExecutionOrder order = (SimpleServiceExecutionOrder) o;
		return getClient().equals(order.getClient()) &&
				getCategory().equals(order.getCategory()) &&
				getSchedule().equals(order.getSchedule()) &&
				getServiceType().equals(order.getServiceType()) &&
				getPostalAddress().equals(order.getPostalAddress()) &&
				getDistance() == order.getDistance();
	}

	/**
	 * Generates a single number to this instance.
	 *
	 * @return the single number
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getClient(), getCategory(), getSchedule(), getServiceType(), getPostalAddress());
	}
}
