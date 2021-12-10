package lapr2.framework.company.user.client;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.manager.Manager;

/**
 * Stores and retrieves all the {@link Client}. Allowing also their creation and validation.
 *
 * @author flow
 */
public class ClientManager extends Manager<Client> {

	/**
	 * Constructs a new client manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public ClientManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link Client}, returning it.
	 *
	 * @param name                    the name of the client
	 * @param taxIdentificationNumber the tax identification number of the client
	 * @param phoneNumber             the phone number of the client
	 * @param email                   the email of the client
	 * @param postalAddress           the postal address of the client
	 * @return the created client
	 */
	public Client createClient(String name, String taxIdentificationNumber, String phoneNumber, String email, PostalAddress postalAddress) {
		return new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
	}

	/**
	 * Verifies if a {@link Client} is valid.
	 *
	 * @param client the client
	 * @return <code>true</code> if the client is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(Client client) {
		return (client.isValid() && this.isValid(client));
	}

	/**
	 * Verifies if a client is valid globally.
	 *
	 * @param client the client
	 * @return <code>true</code> if the client is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(Client client) {
		for (Client listedClient : elements)
			if (listedClient.getTaxIdentificationNumber().equals(client.getTaxIdentificationNumber()) || listedClient.getEmail().equals(client.getEmail()))
				return false;

		return true;
	}

	/**
	 * Returns the client associated with a given email.
	 * Returns null if such client does not exist.
	 *
	 * @param email the email
	 * @return the client associated with the email
	 */
	public Client getClient(String email) {
		for (Client client : elements) {
			if (client.getEmail().equals(email)) {
				return client;
			}
		}
		return null;
	}

	/**
	 * Returns the client associated with a given tax identification number.
	 * Returns null if such client does not exist.
	 *
	 * @param taxIdentificationNumber the tax identification number
	 * @return the client associated with the tax identification number
	 */
	public Client getClientTIN(String taxIdentificationNumber) {
		for (Client client : elements) {
			if (client.getTaxIdentificationNumber().equals(taxIdentificationNumber)) {
				return client;
			}
		}
		return null;
	}
}
