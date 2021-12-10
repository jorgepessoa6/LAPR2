package lapr2.framework.company.location.geographicalarea;


import lapr2.framework.company.location.postcode.PostCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Represents a geographical area, providing access to its designation, post code, radius, travel cost and acting.
 *
 * @author flow
 */
@AllArgsConstructor
public class GeographicalArea implements Serializable {

	private static final long serialVersionUID = 5029564888413202510L;

	/**
	 * The designation of the geographical area.
	 */
	@Getter
	private final String designation;

	/**
	 * The central post code of the geographical area.
	 */
	@Getter
	private final PostCode centeredPostCode;

	/**
	 * The radius of the geographical area, in meters.
	 */
	@Getter
	private final double radius;

	/**
	 * The travel cost of the geographical area.
	 */
	@Getter
	private final double travelCost;

	/**
	 * The post codes in the geographical area and respective distances to the area's center post code.
	 */
	@Getter
	private final HashMap<PostCode, Double> acting;

	/**
	 * Verifies if a geographical area is valid locally. Does this verifying if its designation is not empty and if its post code is valid.
	 *
	 * @return <code>true</code> if the category is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return (!designation.isEmpty() && centeredPostCode.isValid());
	}

	/**
	 * Verifies if this geographical area is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GeographicalArea)) return false;
		GeographicalArea that = (GeographicalArea) o;
		return Double.compare(that.getRadius(), getRadius()) == 0 &&
				Double.compare(that.getTravelCost(), getTravelCost()) == 0 &&
				Objects.equals(getDesignation(), that.getDesignation()) &&
				Objects.equals(getCenteredPostCode(), that.getCenteredPostCode());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getDesignation(), getCenteredPostCode(), getRadius(), getTravelCost());
	}

	@Override
    public String toString() {
        return designation;
    }
}
