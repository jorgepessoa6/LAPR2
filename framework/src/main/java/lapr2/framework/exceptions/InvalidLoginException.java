package lapr2.framework.exceptions;

/**
 * Signals than an exception on a login attempt has occurred.
 *
 * @author flow
 */
public class InvalidLoginException extends Exception {

	/**
	 * Constructs a new invalid login exception with a given message.
	 *
	 * @param message the exception message
	 */
	public InvalidLoginException(String message) {
		super(message);
	}
}
