package lapr2.framework.company.manager;

import lapr2.framework.homeservices.HomeServices;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * Stores all the managers, providing access to each one of them.
 *
 * @author flow
 */
public class DataManager {

	/**
	 * Collection with all the managers.
	 */
	private final List<Manager> managers = new ArrayList<>();

	/**
	 * Gets the instance of manager associated with the class passed as param.
	 * For example, use as <code>dataManager.get(CategoryManager.class)</code>
	 * to return the one and only category manager.
	 * Returns null if the managers of that class does not exist.
	 *
	 * @param managerClass class of the manager (e.g. <b>ClientManager.class</b>)
	 * @param <T>          the class of the manager
	 * @return the manager associated with the class
	 */
	@SuppressWarnings("unchecked")
	public <T extends Manager> T get(Class<T> managerClass) {
		for (Manager manager : managers)
			if (manager.getClass() == managerClass)
				return (T) manager;

		return null;
	}

	/**
	 * Sets the manager associated with its class, removing, if required, a previous manager of the same class.
	 *
	 * @param manager manager
	 */
	public void set(Manager manager) {
		remove(manager.getClass());
		managers.add(manager);
	}

	/**
	 * Verifies if the data manager has an instance of a given manager class.
	 * For example, use <code>dataManager.has(ServiceProviderManager.class)</code>
	 * to verify if the data manager has already one service provider manager.
	 *
	 * @param managerClass class of the manager (e.g. <b>ServiceProviderManager.class</b>)
	 * @return <code>true</code> if the data manager has instances of the manager, <code>false</code> otherwise
	 */
	public boolean has(Class<? extends Manager> managerClass) {
		return this.get(managerClass) != null;
	}

	/**
	 * Loads all the managers in the collection.
	 */
	public void loadAll() {
		for (Manager manager : managers) {
			if (manager.load()) {
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.INFO, manager.getClass().getSimpleName() + " loaded successfully");
			} else {
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Error while loading manager " + manager.getClass().getSimpleName());
			}
		}
	}

	/**
	 * Saves all the managers in the collection.
	 */
	public void saveAll() {
		for (Manager manager : managers) {
			if (!manager.save())
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Error while saving manager " + manager.getClass().getSimpleName());
		}
	}

	/**
	 * Removes the instance of a manager from the collection given its class.
	 *
	 * @param managerClass class of the manager
	 */
	private void remove(Class<? extends Manager> managerClass) {
		managers.removeIf(manager -> manager.getClass() == managerClass);
	}

}
