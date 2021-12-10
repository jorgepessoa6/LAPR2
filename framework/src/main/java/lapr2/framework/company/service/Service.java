package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;

import java.io.Serializable;

/**
 * Represents a service implementation.
 */
public interface Service extends Serializable {

	/**
	 * Returns the category of the service.
	 *
	 * @return the service
	 */
	Category getCategory();

	/**
	 * Returns the hourly cost of the service.
	 *
	 * @return the cost
	 */
	double getCost();

	/**
	 * Returns the cost of the service according to a duration, in hours.
	 *
	 * @param duration the duration
	 * @return the cost
	 */
	double getCost(double duration);

	/**
	 * Returns the id of the service.
	 *
	 * @return the id of the service.
	 */
	String getId();

	/**
	 * Returns the brief description of the service.
	 *
	 * @return the brief description.
	 */
	String getBriefDescription();

	/**
	 * Returns the complete description of the service.
	 *
	 * @return the complete description.
	 */
	String getCompleteDescription();

	/**
	 * Returns whether the service has additional attributes or not.
	 *
	 * @return <code>true</code> if the service has other attributes, <code>false</code> otherwise
	 */
	boolean hasAdditionalAttributes();

	/**
	 * Verifies if a data of the service is valid.
	 *
	 * @return <code>true</code> if the service is valid, <code>false</code> otherwise
	 */
	boolean isValid();

}
