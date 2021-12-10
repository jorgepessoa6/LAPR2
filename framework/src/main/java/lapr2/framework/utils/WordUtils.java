package lapr2.framework.utils;

/**
 * Represents a utility class with methods related to name conventions and syntax.
 *
 * @author flow
 */
public class WordUtils {

	/**
	 * Creates an empty utility class.
	 * Utility classes, which are collections of static members, are not meant to be instantiated.
	 * Thus, the private constructor prevents the instantiation of the class outside it.
	 */
	private WordUtils() {
	}

	/**
	 * Capitalizes every word in a text.
	 *
	 * @param text the text
	 * @return the capitalized text
	 */
	public static String capitalize(String text) {
		String[] words = text.toLowerCase().split("[ _]");

		StringBuilder capitalizedText = new StringBuilder();

		for (String word : words) {
			capitalizedText.append(String.format("%s ", capitalizeFirstLetter(word)));
		}

		return capitalizedText.toString().trim();
	}

	/**
	 * Capitalizes the first letter of a text.
	 *
	 * @param text the text
	 * @return the capitalized text
	 */
	public static String capitalizeFirstLetter(String text) {
		return String.format("%s%s", text.substring(0, 1).toUpperCase(), text.substring(1).toLowerCase());
	}

	/**
	 * Verifies if a text is an {@link Integer}.
	 *
	 * @param text the text
	 * @return <code>true</code> if the text is an integer, <code>false</code> otherwise
	 */
	public static boolean isInteger(String text) {
		try {
			Long.parseLong(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifies if a text is an {@link Double}.
	 *
	 * @param text the text
	 * @return <code>true</code> if the text is a double, <code>false</code> otherwise
	 */
	public static boolean isDouble(String text) {
		try {
			Double.parseDouble(text);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * Verifies if an email is valid.
	 *
	 * @param email email
	 * @return <code>true</code> if the email is valid, <code>false</code> otherwise
	 */
	public static boolean isEmailValid(String email) {
		String[] fields = email.split("@");

		if (fields.length != 2)
			return false;

		return fields[1].contains(".");
	}

	/**
	 * Verifies if a tax identification number is valid.
	 *
	 * @param taxIdentificationNumber the tax identification number
	 * @return <code>true</code> if the tax identification number is valid, <code>false</code> otherwise
	 */
	public static boolean isTaxIdentificationNumberValid(String taxIdentificationNumber) {
		if (taxIdentificationNumber == null || !isInteger(taxIdentificationNumber)) return false;

		final int EXPECTED_DIGITS = 9;

		return taxIdentificationNumber.length() == EXPECTED_DIGITS;

	}

	/**
	 * Verifies if a phone number is valid.
	 * This method only applies to portuguese phone numbers.
	 *
	 * @param phoneNumber the phone number
	 * @return <code>true</code> if the phone number is valid, <code>false</code> otherwise
	 */
	public static boolean isPhoneNumberValid(String phoneNumber) {
		if (phoneNumber == null || (phoneNumber.length() != 9 && phoneNumber.length() != 13)) return false;

		boolean isValid = true;

		String firstNumber = phoneNumber.substring(0, 1);

		if (phoneNumber.length() == 9) {
			isValid = firstNumber.equals("9") || firstNumber.equals("2");
		} else {
			if (!firstNumber.equals("+")) isValid = false;
			else
				isValid = phoneNumber.substring(1, 4).equals("351") && (phoneNumber.substring(4, 5).equals("9") || phoneNumber.substring(4, 5).equals("2"));
		}
		return isValid;
	}

	/**
	 * Verifies if a post code is valid.
	 *
	 * @param postCode the post code
	 * @return <code>true</code> if the post code is valid, <code>false</code> otherwise
	 */
	public static boolean isPostCodeValid(String postCode) {
		if (postCode == null) return false;
		String[] fields = postCode.split("-");
		return fields.length == 2 && isInteger(fields[0]) && isInteger(fields[1]) && fields[0].length() == 4 && fields[1].length() >= 1 && fields[1].length() <= 3;
	}

	/**
	 * Generates a random password.
	 *
	 * @param size the size of the password
	 * @return the generated password
	 */
	public static String generatePassword(int size) {
		StringBuilder password = new StringBuilder();

		for (int i = 0; i < size; i++)
			password.append(getRandomCharacter());

		return password.toString();
	}

	/**
	 * Generates a number or letter, capital or lower case.
	 *
	 * @return the generated character
	 */
	private static char getRandomCharacter() {
		int randomNumber = NumberUtils.getRandomInt(48, 109);

		if (randomNumber > 57)
			randomNumber += 7;
		if (randomNumber > 90)
			randomNumber += 6;

		return (char) randomNumber;
	}
}
