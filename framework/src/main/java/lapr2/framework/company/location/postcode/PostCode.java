package lapr2.framework.company.location.postcode;

import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.homeservices.HomeServices;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

/**
 * Represents a postcode, providing access to its district, municipality, locality, latitude, longitude and post code designation.
 */
@AllArgsConstructor
public class PostCode implements Serializable {

	private static final long serialVersionUID = 5578844291903225277L;

	/**
	 * The district of the postcode.
	 */
	@Getter
	private String district;

	/**
	 * The municipality of the postcode.
	 */
	@Getter
	private String municipality;

	/**
	 * The locality of the postcode.
	 */
	@Getter
	private String locality;

	/**
	 * The latitude of the postcode.
	 */
	@Getter
	private double latitude;

	/**
	 * The longitude of the postcode.
	 */
	@Getter
	private double longitude;

	/**
	 * The postcode designation.
	 */
	@Getter
	private String postCodeDesignation;

	/**
	 * Computes the distance between two postcodes.
	 *
	 * @param postCode        a postcode
	 * @param anotherPostCode another postcode
	 * @return the distance between the two postcodes, in meters
	 */
	public static double getDistance(PostCode postCode, PostCode anotherPostCode) {
		final double R = 6371e3;

		double theta1 = Math.toRadians(postCode.getLatitude());
		double theta2 = Math.toRadians(anotherPostCode.getLatitude());

		double deltaTheta = Math.toRadians(anotherPostCode.getLatitude() - postCode.getLatitude());
		double deltaLambda = Math.toRadians(anotherPostCode.getLongitude() - postCode.getLongitude());

		double a = Math.sin(deltaTheta / 2) * Math.sin(deltaTheta / 2) +
				Math.cos(theta1) * Math.cos(theta2)
						* Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);

		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return R * c;
	}

	/**
	 * Computes the nearest geographical area of this postcode.
	 *
	 * @return the nearest geographical area
	 */
	public GeographicalArea getNearestGeographicalArea() {
		GeographicalAreaManager geographicalAreaManager = HomeServices.getInstance().getCompany().getDataManager().get(GeographicalAreaManager.class);

		ArrayList<GeographicalArea> areas = geographicalAreaManager.getElements();

		if (areas.isEmpty())
			return null;

		Iterator<GeographicalArea> iterator = areas.iterator();

		GeographicalArea closest = null;

		double nearestDistance = Integer.MAX_VALUE;

		while (iterator.hasNext() && nearestDistance != 0) {
			GeographicalArea geographicalArea = iterator.next();

			double distance = getDistance(geographicalArea.getCenteredPostCode(), this);

			if (distance > geographicalArea.getRadius()) continue;

			if (distance < nearestDistance) {
				nearestDistance = distance;
				closest = geographicalArea;
			}
		}

		return closest;
	}

	/**
	 * Verifies whether the postcode is valid locally.
	 *
	 * @return <code>true</code> if the postcode is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !district.isEmpty() && !municipality.isEmpty() && !locality.isEmpty() && !postCodeDesignation.isEmpty() && hasValidLatitude() && hasValidLongitude();
	}

	/**
	 * Verifies if this postcode is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof PostCode)) return false;
		PostCode postCode = (PostCode) o;
		return Double.compare(postCode.getLatitude(), getLatitude()) == 0 &&
				Double.compare(postCode.getLongitude(), getLongitude()) == 0 &&
				getDistrict().equals(postCode.getDistrict()) &&
				getMunicipality().equals(postCode.getMunicipality()) &&
				getLocality().equals(postCode.getLocality()) &&
				getPostCodeDesignation().equals(postCode.getPostCodeDesignation());
	}

	/**
	 * Generates a unique code to this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getDistrict(), getMunicipality(), getLocality(), getLatitude(), getLongitude(), getPostCodeDesignation());
	}

	@Override
	public String toString() {
		return postCodeDesignation;
	}

	/**
	 * Verifies the integrity of the latitude. The latitude values can be between -90 and 90.
	 *
	 * @return <code>true</code> if the latitude is valid, <code>false</code> otherwise
	 */
	private boolean hasValidLatitude() {
		return latitude >= -90 && latitude <= 90;
	}

	/**
	 * Verifies the integrity of the longitude. The latitude values can be between -180 and 180.
	 *
	 * @return <code>true</code> if the longitude is valid, <code>false</code> otherwise
	 */
	private boolean hasValidLongitude() {
		return longitude >= -180 && longitude <= 180;
	}
}
