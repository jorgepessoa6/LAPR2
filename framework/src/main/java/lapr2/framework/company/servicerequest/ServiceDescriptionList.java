package lapr2.framework.company.servicerequest;

import lapr2.framework.company.service.Service;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Stores and retrieves all the {@link ServiceDescription}s of the Company. Also allows their
 * creation and validation.
 *
 * @author flow
 */
public class ServiceDescriptionList implements Serializable {

	private static final long serialVersionUID = -8963867006195646675L;

    /**
     * Collection of service description of the all services.
     */
    @Getter
    private final ArrayList<ServiceDescription> serviceDescriptions = new ArrayList<>();

    /**
     * Creates a new {@link ServiceDescription} returning it.
     *
     * @param description the description of the Service
     * @param service     the requested service
     * @param duration    the duration of the Service
     * @return the created ServiceDescription
     */
    public ServiceDescription createServiceDescription(String description, Service service, float duration) {
        return new ServiceDescription(description, service, duration);
    }

    /**
     * Adds a {@link ServiceDescription} to the collection.
     *
     * @param serviceDescription the service description
     * @return <code>true</code> if the service description was successfully added, <code>false</code> otherwise
     */
    public boolean addServiceDescription(ServiceDescription serviceDescription) {
        return serviceDescriptions.add(serviceDescription);
    }

    /**
     * Verifies if a {@link ServiceDescription} is valid.
     *
     * @param serviceDescription the service description
     * @return <code>true</code> if the service description is valid, <code>false</code> otherwise
     */
    public boolean isSecure(ServiceDescription serviceDescription) {
		return (serviceDescription.isValid() && isValid(serviceDescription));
    }

    /**
     * Verifies if a service description is valid globally.
     *
     * @param serviceDescription the service description
     * @return <code>true</code> if the service description is valid globally, <code>false</code> otherwise
     */
    protected boolean isValid(ServiceDescription serviceDescription) {
        return !serviceDescriptions.contains(serviceDescription);
    }

	/**
	 * Verifies if this list is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceDescriptionList)) return false;
		ServiceDescriptionList that = (ServiceDescriptionList) o;
		return Objects.equals(getServiceDescriptions(), that.getServiceDescriptions());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getServiceDescriptions());
	}
}
