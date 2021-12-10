package lapr2.framework.company.user.user;

import lapr2.framework.utils.WordUtils;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a company user, providing access to their email, password and roles.
 *
 * @author flow
 */
public class User implements Serializable {

    private static final long serialVersionUID = -6062461248531958345L;

    /**
     * The roles of the user.
     */
	@Getter
	private final HashSet<Role> roles = new HashSet<>();

    /**
     * The email of the user.
     */
    @Getter
    private String email;

    /**
     * The password of the user.
     */
    @Getter
    private String password;

    /**
     * Constructs a new user with an email and password.
     *
     * @param email    the email of the user
     * @param password the password of the user
     */
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Adds a role to the user.
     *
     * @param role the role
     * @return <code>true</code> if the role was successfully added, <code>false</code> otherwise
     */
    public boolean addRole(Role role) {
        if (role == null) return false;
        return roles.add(role);
    }

    /**
     * Verifies if a user has a given role.
     *
     * @param role the role
     * @return <code>true</code> if the user has the role, <code>false</code> otherwise
     */
    public boolean hasRole(Role role) {
        if (role == null) return false;
        return roles.contains(role);
    }

    /**
     * Verifies if a user is valid locally.
     *
     * @return <code>true</code> if the user is valid locally, <code>false</code> otherwise
     */
    public boolean isValid() {
        return !this.email.isEmpty() &&
                !this.password.isEmpty() &&
                !this.roles.isEmpty();
    }

	/**
	 * Verifies if this user is equals to a given a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return getEmail().equals(user.getEmail());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getEmail());
	}

    /**
     * Represents the role of a {@link User}.
     */
    public enum Role {
        ADMINISTRATOR,
        CLIENT,
        HUMAN_RESOURCES_OFFICER,
        SERVICE_PROVIDER;

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
