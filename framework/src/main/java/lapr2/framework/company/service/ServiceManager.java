package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.manager.Manager;

import java.util.Collection;
import java.util.HashSet;

/**
 * Stores and retrieves all the {@link Service}. Allowing also their creation and validation.
 */
public class ServiceManager extends Manager<Service> {

	/**
	 * Constructs a new manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public ServiceManager(String filePath) {
		super(filePath);
	}

	/**
	 * Verifies if a service is valid.
	 *
	 * @param service the service
	 * @return <code>true</code> if the service is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(Service service) {
		return isValid(service) && service.isValid();
	}

	/**
	 * Verifies if a service is valid globally.
	 *
	 * @param service the service
	 * @return <code>true</code> if the service is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(Service service) {
		return !elements.contains(service);
	}

	/**
	 * Returns all the services of a given category.
	 *
	 * @param category the given category
	 * @return a collection with all the services
	 */
	public Collection<Service> getServicesOf(Category category) {
		Collection<Service> services = new HashSet<>();

		for (Service service : elements)
			if (service.getCategory().equals(category))
				services.add(service);

		return services;
	}

	/**
	 * Returns a service by its ID.
	 * Returns null if such service does not exist.
	 *
	 * @param id the ID
	 * @return the service
	 */
	public Service getService(String id) {
		for (Service service : elements)
			if (service.getId().equals(id))
				return service;

		return null;
	}
}
