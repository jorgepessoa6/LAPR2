package lapr2.framework.company.user.humanresourcesofficer;

import lapr2.framework.company.manager.Manager;

/**
 * Represents the administrator manager, storing administrators.
 *
 * @author flow
 */
public class HumanResourcesOfficerManager extends Manager<HumanResourcesOfficer> {

	/**
	 * Constructs a new administrator manager.
	 */
	public HumanResourcesOfficerManager() {
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
	 * Returns the human resources officer associated with a given email.
	 * Returns null if such human resources officer does not exist.
	 *
	 * @param email the email
	 * @return the human resources officer associated with the email
	 */
	public HumanResourcesOfficer getHumanResourcesOfficer(String email) {
		for (HumanResourcesOfficer humanResourcesOfficer : elements) {
			if (humanResourcesOfficer.getEmail().equals(email)) {
				return humanResourcesOfficer;
			}
		}
		return null;
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
	public boolean isSecure(HumanResourcesOfficer humanResourcesOfficer) {
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
	public boolean isValid(HumanResourcesOfficer humanResourcesOfficer) {
		return false;
	}

}

