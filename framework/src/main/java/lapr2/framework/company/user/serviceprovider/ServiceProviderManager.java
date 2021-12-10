package lapr2.framework.company.user.serviceprovider;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.manager.Manager;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.user.serviceprovider.rating.Rating;

import java.util.ArrayList;
import java.util.List;


/**
 * Stores and retrieves all the {@link ServiceProvider}s of the Company. Also allows their
 * creation and validation.
 *
 * @author flow
 */
public class ServiceProviderManager extends Manager<ServiceProvider> {

	/**
	 * Constructs a new service provider manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public ServiceProviderManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link ServiceProvider} returning it.
	 *
	 * @param mechanographicalNumber the mechanographical number of the service provider
	 * @param completeName           the complete name of the service provider
	 * @param abbreviatedName        the abbreviated name of the service provider
	 * @param email                  the email of the service provider
     * @param postCode                 the post code of the service provider
     * @return the created service provider
	 */
    public ServiceProvider createServiceProvider(String mechanographicalNumber, String completeName, String abbreviatedName, String email, PostCode postCode) {
        return new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
	}

	/**
	 * Checks if a service provider is secure, verifying its integrity locally and globally.
	 *
	 * @param serviceProvider the service provider
	 * @return <code>true</code> if the service provider is valid locally and globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(ServiceProvider serviceProvider) {
		return serviceProvider.isValid() && isValid(serviceProvider);
	}

	/**
	 * Computes the mean of the population reviews of the service providers. Since the
	 * rating is a discrete distribution the algorithm used is based on the mean formula
	 * of discrete distributions.
	 *
	 * @return the population mean
	 */
	public float getPopulationMean() {
		float mean = 0;

		float[] reviews = Rating.getReviewsProbability(getPopulationReviews());

		for (int index = 0; index < reviews.length; index++) {
			mean += index * reviews[index];
		}

		return mean;
	}

	/**
	 * Computes the standard deviation of the global reviews of the service providers.
	 * Since the rating is a discrete distribution the algorithm used is based on the
	 * standard deviation formula of discrete distributions.
	 *
	 * @return the population standard deviation
	 */
	public double getPopulationStandardDeviation() {
		float standardDeviation = 0;

		float[] reviews = Rating.getReviewsProbability(getPopulationReviews());

		for (int index = 0; index < reviews.length; index++) {
			standardDeviation += Math.pow(index, 2) * reviews[index];
		}

		return standardDeviation - Math.pow(getPopulationMean(), 2);
	}

	/**
	 * Returns all the service providers available at a give schedule and duration, in form of list.
	 *
	 * @param schedule      the schedule
	 * @param duration      the duration in hours
	 * @return set with all available service providers
	 */
	public List<ServiceProvider> getAvailableServiceProviders(Schedule schedule, float duration) {
		List<ServiceProvider> serviceProviders = new ArrayList<>();

		for (ServiceProvider serviceProvider : elements) {
			if (serviceProvider.getDailyAvailabilityList().isAvailableAt(schedule, duration)) {
				serviceProviders.add(serviceProvider);
			}
		}

		return serviceProviders;
	}

	/**
	 * Returns all the service providers available at a give schedule and duration, in form of list.
	 *
	 * @param schedule      the schedule
	 * @param duration      the duration in hours
	 * @param postalAddress the post address of the service
	 * @return set with all available service providers
	 */
	public List<ServiceProvider> getAvailableServiceProviders(Schedule schedule, float duration, PostalAddress postalAddress) {
		List<ServiceProvider> serviceProviders = new ArrayList<>();

		for (ServiceProvider serviceProvider : elements) {
			if (serviceProvider.getDailyAvailabilityList().isAvailableAt(schedule, duration) && serviceProvider.isEligible(postalAddress)) {
				serviceProviders.add(serviceProvider);
			}
		}

		return serviceProviders;
	}

	/**
	 * Checks if a service provider is already in the elements of the manager.
	 *
	 * @param serviceProvider the service provider
	 * @return <code>true</code> if the service provider is not in elements of the manager, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(ServiceProvider serviceProvider) {
		return (!elements.contains(serviceProvider));
	}

	/**
	 * Sums the ratings of each service provider to an array with the population reviews.
	 *
	 * @return the array with the population reviews
	 */
	private int[] getPopulationReviews() {
		int[] reviews = new int[Rating.MAX_RATING - Rating.MIN_RATING + 1];

		for (ServiceProvider serviceProvider : elements) {
			Rating rating = serviceProvider.getRating();

			for (int index = 0; index < reviews.length; index++) {
				reviews[index] += rating.getReviewNumber(Rating.MIN_RATING + index);
			}
		}

		return reviews;
	}

	/**
	 * Returns a {@link ServiceProvider} by its email. Returns null if there is none.
	 *
	 * @param email email of the service provider
	 * @return the service provider found
	 */

	public ServiceProvider getServiceProvider(String email) {
		for (ServiceProvider serviceProvider : elements) {
			if (serviceProvider.getEmail().equals(email)) {
				return serviceProvider;
			}
		}
		return null;
	}

	/**
	 * Returns the service provider associated with a given mechanographical number.
	 * Returns null if such service provider does not exist.
	 *
	 * @param mechanographicalNumber the mechanographical number
	 * @return the service provider associated with the mechanographical number
	 */
	public ServiceProvider getServiceProviderMN(String mechanographicalNumber) {
		for (ServiceProvider serviceProvider : elements) {
			if (serviceProvider.getMechanographicalNumber().equals(mechanographicalNumber)) {
				return serviceProvider;
			}
		}
		return null;
	}
}
