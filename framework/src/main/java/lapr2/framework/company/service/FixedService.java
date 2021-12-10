package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Represents a fixed service, an implementation of the {@link Service}.
 *
 * @author flow
 */
public class FixedService implements Service {

	private static final long serialVersionUID = 4646554126371534129L;

	/**
	 * The id of the service.
	 */
	@Getter
	private final String id;

	/**
	 * The brief description of the service.
	 */
	@Getter
	private final String briefDescription;

	/**
	 * The complete description of the service.
	 */
	@Getter
	private final String completeDescription;

	/**
	 * The cost of the service, per hour.
	 */
	@Getter
	private final double cost;

	/**
	 * The category of the service.
	 */
	@Getter
	private final Category category;

	/**
	 * The predefined duration of the service, in hours.
	 */
	@Getter
	@Setter
	private double predefinedDuration;

	/**
	 * Constructs a new fixed service with its key values.
	 *
	 * @param id                  the id
	 * @param briefDescription    the brief description
	 * @param completeDescription the complete description
	 * @param cost                the hourly cost
	 * @param category            the category
	 */
	public FixedService(String id, String briefDescription, String completeDescription, double cost, Category category) {
		this.id = id;
		this.briefDescription = briefDescription;
		this.completeDescription = completeDescription;
		this.cost = cost;
		this.category = category;
	}

	/**
	 * Returns the cost of the service according to a duration.
	 *
	 * @param duration the duration
	 * @return the cost
	 */
	@Override
	public double getCost(double duration) {
		return cost * duration;
	}

	/**
	 * Returns whether the service has additional attributes or not.
	 *
	 * @return <code>true</code> because the fixed service does have additional attributes, the
	 * predefined duration
	 */
	@Override
	public boolean hasAdditionalAttributes() {
		return true;
	}

	/**
	 * Verifies if a data of the service is valid.
	 *
	 * @return <code>true</code> if the service is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid() {
		return !id.isEmpty() && !briefDescription.isEmpty() && !completeDescription.isEmpty()
				&& cost > 0 && predefinedDuration > 0 && category != null;
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return String.format("%s - %s (fixed)", id, briefDescription);
	}

	/**
	 * Verifies if this instance is the same as a give one.
	 *
	 * @param o the given instance
	 * @return <code>true</code> if they are the same, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FixedService that = (FixedService) o;
		return Objects.equals(id, that.id);
	}

	/**
	 * Gets a single hashcode for the instance.
	 *
	 * @return the hashcode
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
