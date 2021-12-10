package lapr2.framework.company.user.administrator;

import lapr2.framework.company.manager.Manager;

/**
 * Represents the administrator manager, storing administrators.
 *
 * @author flow
 */
public class AdministratorManager extends Manager<Administrator> {

	/**
	 * Constructs a new administrator manager.
	 */
	public AdministratorManager() {
		super(null);
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
	public boolean isSecure(Administrator administrator) {
		return false;
	}

	/**
	 * Returns the administrator associated with a given email.
	 * Returns null if such administrator does not exist.
	 *
	 * @param email the email
	 * @return the administrator associated with the email
	 */
	public Administrator getAdministrator(String email) {
		Administrator administrator = null;

		for (Administrator admin : elements) {
			if (admin.getEmail().equals(email)) {
				administrator = admin;
				break;
			}
		}

		return administrator;
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
	public boolean isValid(Administrator administrator) {
		return false;
	}
}
