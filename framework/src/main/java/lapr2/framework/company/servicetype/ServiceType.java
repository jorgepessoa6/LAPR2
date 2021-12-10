package lapr2.framework.company.servicetype;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.service.Service;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.WordUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.logging.Level;

/**
 * Represents the service type. This class is responsible for creating services.
 */
@AllArgsConstructor
public class ServiceType implements Serializable {

	private static final long serialVersionUID = -810899354365944569L;

	/**
	 * The type of the service.
	 */
	@Getter
	private Type type;

	/**
	 * The class path of the service class.
	 */
	private String classPath;

	/**
	 * Creates a service with its key values. If the service is not successfully created returns <code>null</code>.
	 *
	 * @param id                  the id
	 * @param briefDescription    the brief description
	 * @param completeDescription the complete description
	 * @param cost                the hourly cost
	 * @param category            the category
	 * @return the service
	 */
	public Service createService(String id, String briefDescription, String completeDescription, double cost, Category category) {
		Class[] args = new Class[]{String.class, String.class, String.class, double.class, Category.class};
		Constructor constructor = null;
		try {
			constructor = Class.forName(classPath).getConstructor(args);
		} catch (NoSuchMethodException | ClassNotFoundException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not create service", e);
		}

		Object[] values = new Object[]{id, briefDescription, completeDescription, cost, category};

		Service service = null;
		try {
			service = (Service) (constructor != null ? constructor.newInstance(values) : null);
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not create service", e);
		}

		return service;
	}

	/**
	 * Returns whether a service has additional data or not.
	 *
	 * @return <code>true</code> if the service has additional data, <code>false</code> otherwise
	 */
	public boolean hasAdditionalData() {
		return createService("", "", "", 0, null).hasAdditionalAttributes();
	}

	/**
	 * Returns a string representation of the object.
	 *
	 * @return a string representation of the object
	 */
	@Override
	public String toString() {
		return WordUtils.capitalize(type.name());
	}

	/**
	 * Verifies if this service type is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if this service type is equals to the given object, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceType)) return false;
		ServiceType type1 = (ServiceType) o;
		return getType() == type1.getType() &&
				classPath.equals(type1.classPath);
	}

	/**
	 * Returns a single code for the object.
	 *
	 * @return the single code for the object
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getType(), classPath);
	}

	/**
	 * The type of a service. Services can be defined as fixed, limited or expandable.
	 */
	public enum Type {
		FIXED, LIMITED, EXPANDABLE;

		/**
		 * Returns a string representation of the object.
		 *
		 * @return a string representation of the object
		 */
		@Override
		public String toString() {
			return WordUtils.capitalize(this.name());
		}
	}
}
