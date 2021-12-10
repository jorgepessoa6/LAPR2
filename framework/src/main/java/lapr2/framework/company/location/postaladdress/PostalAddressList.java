package lapr2.framework.company.location.postaladdress;

import lapr2.framework.company.location.postcode.PostCode;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a List of Postal Addresses
 *
 * @author flow
 */
public class PostalAddressList implements Serializable {

	private static final long serialVersionUID = -7962315193686490922L;

	/**
	 * Collection of postal Address of the given client.
	 */
	@Getter
	private final List<PostalAddress> elements = new ArrayList<>();

	/**
	 * Creates a new {@link PostalAddress} returning it.
	 *
	 * @param address  the address of the PostalAddress
	 * @param postCode the PostCode of the PostalAddress
	 * @return the created PostalAddress
	 */
	public PostalAddress createPostalAddress(String address, PostCode postCode) {
		return new PostalAddress(address, postCode);
	}

	/**
	 * Adds a {@link PostalAddress} to the collection.
	 *
	 * @param postalAddress the postal address
	 * @return <code>true</code> if the postal address was successfully added, <code>false</code> otherwise
	 */
	public boolean add(PostalAddress postalAddress) {
		if (elements.contains(postalAddress)) return false;
		return elements.add(postalAddress);
	}

	/**
	 * Verifies if a {@link PostalAddress} is valid.
	 *
	 * @param postalAddress the Postal Address
	 * @return <code>true</code> if the postalAddress is valid, <code>false</code> otherwise
	 */
	public boolean isSecure(PostalAddress postalAddress) {
		return (postalAddress != null && postalAddress.isValid() && this.isValid(postalAddress));
	}

	/**
	 * Verifies if a category is valid globally.
	 *
	 * @param postalAddress the postal address
	 * @return <code>true</code> if the postal address is valid globally, <code>false</code> otherwise
	 */
	public boolean isValid(PostalAddress postalAddress) {
        return !elements.contains(postalAddress);
	}

	/**
	 * Returns a postal address by its post code designation.
	 * Returns null if such postal address does not exist.
	 *
	 * @param postCodeDesignation the post code designation
	 * @return the postal address
	 */
	public PostalAddress getPostalAddress(String postCodeDesignation) {
		for (PostalAddress postalAddress : elements)
			if (postalAddress.getPostCode().getPostCodeDesignation().equals(postCodeDesignation))
				return postalAddress;

		return null;
	}

	/**
	 * Verifies if this list is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PostalAddressList)) return false;
		PostalAddressList that = (PostalAddressList) o;
		return Objects.equals(getElements(), that.getElements());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getElements());
	}
}
