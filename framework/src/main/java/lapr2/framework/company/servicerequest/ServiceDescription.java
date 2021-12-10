package lapr2.framework.company.servicerequest;

import lapr2.framework.company.service.Service;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represent the description of a service, providing access to its description, service and duration.
 *
 * @author flow
 */
@AllArgsConstructor
public class ServiceDescription implements Serializable {

	private static final long serialVersionUID = -7414997724781104923L;

	/**
	 * The description of the service.
	 */
	@Getter
	private final String description;

	/**
	 * The requested service.
	 */
	@Getter
	private final Service service;

	/**
	 * The duration of the service in hours.
	 */
	@Getter
	private final float duration;

	/**
	 * Verifies if a service description is valid locally.
	 *
	 * @return <code>true</code> if the service description is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !description.isEmpty() &&
				service.isValid() &&
				duration > 0 &&
				duration % 0.5d == 0;
	}

	/**
	 * Verifies if this description is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceDescription)) return false;
		ServiceDescription that = (ServiceDescription) o;
		return Objects.equals(getDescription(), that.getDescription()) &&
				Objects.equals(getService(), that.getService());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getDescription(), getService(), getDuration());
	}

	@Override
	public String toString() {
		return (description);
	}
}
