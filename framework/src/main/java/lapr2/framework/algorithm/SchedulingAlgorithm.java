package lapr2.framework.algorithm;

import lapr2.framework.company.Company;
import lapr2.framework.company.affectation.AffectationManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;

import java.util.Comparator;
import java.util.List;

/**
 * Represents the scheduling algorithm capable of affect service providers to services.
 *
 * @author flow
 */
public abstract class SchedulingAlgorithm {

	/**
	 * The service provider manager.
	 */
	private ServiceProviderManager serviceProviderManager;

	/**
	 * The affectation manager.
	 */
	private AffectationManager affectationManager;

	/**
	 * Affects providers to services.
	 *
	 * @param serviceRequests the requests where the services reside
	 */
	public void affectProviders(List<ServiceRequest> serviceRequests) {
		Company company = HomeServices.getInstance().getCompany();
		this.affectationManager = company.getDataManager().get(AffectationManager.class);
		this.serviceProviderManager = company.getDataManager().get(ServiceProviderManager.class);

		List<ServiceRequest> orderedRequests = orderRequests(serviceRequests);

		assignProviders(orderedRequests);
	}

	/**
	 * Orders the requests that have services that are going to be affected.
	 *
	 * @param serviceRequests the list with the requests
	 * @return the requests ordered
	 */
	abstract List<ServiceRequest> orderRequests(List<ServiceRequest> serviceRequests);

	/**
	 * Assigns providers to each request
	 *
	 * @param requests the requests
	 */
	private void assignProviders(List<ServiceRequest> requests) {
		for (ServiceRequest request : requests) {
			affectServiceDescriptions(request);
		}
	}

	/**
	 * Affects the service descriptions of a given request.
	 *
	 * @param request the given request
	 */
	private void affectServiceDescriptions(ServiceRequest request) {
		List<Schedule> schedules = request.getScheduleList().getSchedulePreferencesList();
		PostalAddress postalAddress = request.getPostalAddress();

		for (ServiceDescription serviceDescription : request.getServiceDescriptionList().getServiceDescriptions()) {

			if (affectationManager.isServiceDescriptionAffected(serviceDescription))
				continue; //ignores the description if it is already affected

			affectDescription(request, schedules, postalAddress, serviceDescription);
		}
	}

	/**
	 * Affects a given service description to a provider.
	 *
	 * @param request            the request of the service description
	 * @param schedules          the available schedules
	 * @param postalAddress      the postal address of the service
	 * @param serviceDescription the given service description
	 */
	private void affectDescription(ServiceRequest request, List<Schedule> schedules, PostalAddress postalAddress, ServiceDescription serviceDescription) {
		float duration = serviceDescription.getDuration();

		for (Schedule listedSchedule : schedules) {
			ServiceProvider serviceProvider = chooseServiceProvider(listedSchedule, duration, postalAddress);

			if (serviceProvider == null) continue; //if any service provider is available, goes to the next schedule.

			affectationManager.affect(request, serviceDescription, serviceProvider, listedSchedule);
		}
	}

	/**
	 * Gets the most suited service provider for a service given its schedule and location.
	 * Returns null if there are not service providers available.
	 *
	 * @param schedule      the schedule of the service
	 * @param duration      the duration of the service
	 * @param postalAddress the postal address of the service
	 * @return the most suited service provider
	 */
	private ServiceProvider chooseServiceProvider(Schedule schedule, float duration, PostalAddress postalAddress) {
		List<ServiceProvider> serviceProviders = serviceProviderManager.getAvailableServiceProviders(schedule, duration, postalAddress);

		//if there are not service providers available.
		if (serviceProviders.isEmpty()) {
			return null;
		}

		ServiceProvider chosen = serviceProviders.get(0);

		for (int i = 1; i < serviceProviders.size(); i++) {
			ServiceProvider serviceProvider = serviceProviders.get(i);

			if (Comparator.comparing(ServiceProvider::getRating)
					.thenComparing(getDistanceComparator(postalAddress.getPostCode()).reversed())
					.thenComparing(ServiceProvider::getCompleteName).reversed()
					.compare(chosen, serviceProvider) < 0) {

				chosen = serviceProvider;
			}
		}

		return chosen;
	}

	/**
	 * Returns a comparator that compares the distance to a given post codes by service providers.
	 *
	 * @param postCode the given post code
	 * @return the comparator
	 */
	private Comparator<? super ServiceProvider> getDistanceComparator(PostCode postCode) {

		return (Comparator<ServiceProvider>) (o1, o2) -> {
			double distanceO1 = PostCode.getDistance(o1.getPostCode(), postCode);
			double distanceO2 = PostCode.getDistance(o2.getPostCode(), postCode);

			return Double.compare(distanceO1, distanceO2);
		};
	}
}
