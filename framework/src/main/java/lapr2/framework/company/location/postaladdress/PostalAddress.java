package lapr2.framework.company.location.postaladdress;

import lapr2.framework.company.location.postcode.PostCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a Postal Address, providing access to its address and post code.
 *
 * @author flow
 */
@AllArgsConstructor
public class PostalAddress implements Serializable {

	private static final long serialVersionUID = -6247006268082304836L;

    /**
     * The Address of the Postal Address.
     */
    @Getter
    private final String address;

    /**
     * The Post Code of the Postal Address.
     */
    @Getter
    private final PostCode postCode;

    /**
     * Verifies if a postal address is valid locally.
     *
     * @return <code>true</code> if the postal address is valid locally, <code>false</code> otherwise
     */
    public boolean isValid() {
        return postCode != null && postCode.isValid() && !address.isEmpty();
    }

	/**
	 * Verifies if this postal address is equals toa  given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PostalAddress)) return false;
		PostalAddress that = (PostalAddress) o;
		return Objects.equals(getAddress(), that.getAddress()) &&
				Objects.equals(getPostCode(), that.getPostCode());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAddress(), getPostCode());
	}

    @Override
    public String toString() {
        return postCode.toString();
    }
}
