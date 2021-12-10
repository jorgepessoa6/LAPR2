package lapr2.framework.session;

import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.exceptions.InvalidLoginException;
import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents the current session, providing access to the current logged {@link User}.
 *
 * @author flow
 */
@NoArgsConstructor
public class CurrentSession {

	/**
	 * The current logged user.
	 */
	@Getter
	private User user;

	/**
	 * Logs in the {@link User} with a specific email and password in the system.
	 *
	 * @param email    the email of the user
	 * @param password the password of the user
	 * @throws InvalidLoginException if the does not exist user with such email or
	 *                               if the password does not match with the email
	 */
	public void login(String email, String password) throws InvalidLoginException {
		UserManager userManager = HomeServices.getInstance().getCompany().getDataManager().get(UserManager.class);

		User currentUser = userManager.getUser(email);

		if (currentUser == null) {
			throw new InvalidLoginException(String.format("User %s could not be found.", email));
		}

		if (!currentUser.getPassword().equals(password)) {
			throw new InvalidLoginException(String.format("Incorrect password for user %s.", currentUser.getEmail()));
		}

		this.user = currentUser;
	}

	/**
	 * Logs out the user.
	 */
	public void logout() {
		user = null;
	}

}
