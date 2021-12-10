package lapr2.framework.company.location.geographicalservice;

import lapr2.framework.company.location.postcode.PostCode;

import java.util.HashMap;

/**
 * Represents a base implementation for the company's external geographical service.
 */
public interface ExternalGeographicalService {

	/**
	 * Computes the acting of a centered postcode.
	 *
	 * @param postCode the centered postcode
	 * @param radius   the radius of acting
	 * @return the acting
	 */
	HashMap<PostCode, Double> getActing(PostCode postCode, double radius);

}
