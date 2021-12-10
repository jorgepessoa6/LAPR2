package lapr2.framework.company.user.client;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postaladdress.PostalAddressList;
import lapr2.framework.utils.WordUtils;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the client, providing access to its name, tax ID, phone number, email and postal addresses.
 *
 * @author flow
 */
public class Client implements Serializable {

	private static final long serialVersionUID = 8179436238192165298L;

	/**
	 * The name of the client.
     */
    @Getter
    private final String name;

    /**
	 * The tax identification number of the client.
     */
    @Getter
    private final String taxIdentificationNumber;

    /**
	 * The telephone number of the client.
     */
    @Getter
    private final String phoneNumber;

    /**
	 * The email of the client.
     */
    @Getter
    private final String email;

    /**
	 * The postal addresses of the client.
     */
    @Getter
    private final PostalAddressList postalAddressList;

    /**
     * Constructs a new client with a single {@link PostalAddress}.
     *
     * @param name                    the name of the client
     * @param taxIdentificationNumber the tax identification number of the client
     * @param phoneNumber             the phone number of the client
     * @param email                   the email of the client
     * @param postalAddress           the postal address of the client
     */
    public Client(String name, String taxIdentificationNumber, String phoneNumber, String email, PostalAddress postalAddress) {
        this.name = name;
        this.taxIdentificationNumber = taxIdentificationNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;

        this.postalAddressList = new PostalAddressList();
		postalAddressList.add(postalAddress);
    }

    /**
     * Verifies if a client is valid locally.
     *
     * @return <code>true</code> if the client is valid locally, <code>false</code> otherwise
     */
    public boolean isValid() {
		return !name.isEmpty() &&
				!taxIdentificationNumber.isEmpty() &&
				!phoneNumber.isEmpty() &&
				!email.isEmpty() &&
				WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber) &&
				WordUtils.isPhoneNumberValid(phoneNumber) &&
				WordUtils.isEmailValid(email);
    }

	/**
	 * Verifies if this client is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Client)) return false;
		Client client = (Client) o;
		return Objects.equals(getName(), client.getName()) &&
				Objects.equals(getTaxIdentificationNumber(), client.getTaxIdentificationNumber()) &&
				Objects.equals(getPhoneNumber(), client.getPhoneNumber()) &&
				Objects.equals(getEmail(), client.getEmail()) &&
				Objects.equals(getPostalAddressList(), client.getPostalAddressList());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), getTaxIdentificationNumber(), getPhoneNumber(), getEmail(), getPostalAddressList());
	}
}
