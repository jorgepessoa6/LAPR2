package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;

/**
 * Represents an expandable service, an implementation of the {@link Service}.
 */
@AllArgsConstructor
public class ExpandableService implements Service {

	private static final long serialVersionUID = 7721259565571460223L;

	/**
	 * The id of the service.
	 */
	@Getter
	private String id;

	/**
	 * The brief description of the service.
	 */
	@Getter
	private String briefDescription;

	/**
	 * The complete description of the service.
	 */
	@Getter
	private String completeDescription;

	/**
	 * The cost of the service.
	 */
	@Getter
	private double cost;

	/**
	 * The category of the service.
	 */
	@Getter
	private Category category;

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
	 * @return <code>false</code> because the limited service does not have any additional attributes
	 */
	@Override
	public boolean hasAdditionalAttributes() {
		return false;
	}

	/**
	 * Verifies if a data of the service is valid.
	 *
	 * @return <code>true</code> if the service is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid() {
		return !id.isEmpty() && !briefDescription.isEmpty() && !completeDescription.isEmpty()
				&& cost > 0 && category != null;
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return String.format("%s - %s (expandable)", id, briefDescription);
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
		ExpandableService that = (ExpandableService) o;
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
