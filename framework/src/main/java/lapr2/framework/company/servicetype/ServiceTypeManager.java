package lapr2.framework.company.servicetype;

import lapr2.framework.company.manager.Manager;

/**
 * Represents the service type manager, storing service types.
 *
 * @author flow
 */
public class ServiceTypeManager extends Manager<ServiceType> {

	/**
	 * Constructs a new service type manager.
	 */
	public ServiceTypeManager() {
		super(null);

		add(new ServiceType(ServiceType.Type.FIXED, "lapr2.framework.company.service.FixedService"));
		add(new ServiceType(ServiceType.Type.LIMITED, "lapr2.framework.company.service.LimitedService"));
		add(new ServiceType(ServiceType.Type.EXPANDABLE, "lapr2.framework.company.service.ExpandableService"));
	}

	/**
	 * Despite being a manager, this class does not need the <code>load()</code> method. Thus, the method is deprecated
	 * and returns <code>false</code>
	 *
	 * @return <code>false</code>
	 * @deprecated method is not going to be used
	 */
	@Deprecated
	@Override
	public boolean load() {
		return false;
	}

	/**
	 * Despite being a manager, this class does not need the <code>save()</code> method. Thus, the method is deprecated
	 * and returns <code>false</code>
	 *
	 * @return <code>false</code>
	 * @deprecated method is not going to be used
	 */
	@Deprecated
	@Override
	public boolean save() {
		return false;
	}

	/**
	 * Despite being a manager, this class does not need the <code>isSecure()</code> method. Thus, the method is deprecated
	 * and returns <code>false</code>
	 *
	 * @return <code>false</code>
	 * @deprecated method is not going to be used
	 */
	@Deprecated
	@Override
	public boolean isSecure(ServiceType serviceType) {
		return false;
	}

	/**
	 * Despite being a manager, this class does not need the <code>isValid()</code> method. Thus, the method is deprecated
	 * and returns <code>false</code>
	 *
	 * @return <code>false</code>
	 * @deprecated method is not going to be used
	 */
	@Deprecated
	@Override
	public boolean isValid(ServiceType serviceType) {
		return false;
	}

	/**
	 * Returns the service type given its type. Returns <code>null</code> if no service type is found.
	 *
	 * @param type the type
	 * @return the service type
	 */
	public ServiceType get(ServiceType.Type type) {
		for (ServiceType serviceType : elements) {
			if (type.equals(serviceType.getType())) {
				return serviceType;
			}
		}

		return null;
	}
}
