package lapr2.framework.company.user.user;

import lapr2.framework.company.manager.Manager;

/**
 * Stores all {@link User}s providing access to each one of them.
 *
 * @author flow
 */
public class UserManager extends Manager<User> {

	/**
	 * Constructs a new user manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public UserManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link User}.
	 *
	 * @param email    the email of the user
	 * @param password the password of the user
	 * @return the created user
	 */
	public User createUser(String email, String password) {
		User user = getUser(email);

		if (user == null) {
			return new User(email, password);
		} else {
			return user;
		}
	}

	/**
	 * Returns a user given an email. Returns <code>null</code> if no user is found.
	 *
	 * @param email the email
	 * @return the user
	 */
	public User getUser(String email) {
		User user = null;

		for (User setUser : elements) {
			if (setUser.getEmail().equalsIgnoreCase(email)) {
				user = setUser;
				break;
			}
		}

		return user;
	}

	/**
	 * Verifies if a user is valid.
	 *
	 * @param user the user
	 * @return <code>true</code> if the user is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(User user) {
		return user != null && user.isValid() && isValid(user);
	}

	/**
	 * Verifies if a user is valid globally.
	 *
	 * @param user the user
	 * @return <code>true</code> if the user is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(User user) {
		return !elements.contains(user);
	}
}
