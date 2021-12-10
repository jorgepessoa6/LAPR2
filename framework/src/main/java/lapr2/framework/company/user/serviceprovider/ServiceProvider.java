package lapr2.framework.company.user.serviceprovider;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailabilityList;
import lapr2.framework.company.user.serviceprovider.rating.Rating;
import lapr2.framework.utils.WordUtils;
import lombok.Getter;

import java.io.Serializable;
import java.util.*;


/**
 * Represents a service provider, providing access to its service provider.
 *
 * @author flow
 */
public class ServiceProvider implements Serializable {

	private static final long serialVersionUID = -3532639812759544821L;

	/**
	 * The complete name of the service provider.
	 */
	@Getter
	private final String completeName;

	/**
	 * The abbreviated name of the service provider.
	 */
	@Getter
	private final String abbreviatedName;

	/**
	 * The mechanographical number of the service provider.
	 */
	@Getter
	private final String mechanographicalNumber;

	/**
	 * The email of the service provider.
	 */
	@Getter
	private final String email;

	/**
	 * The post code of the service provider.
	 */
	@Getter
	private final PostCode postCode;

	/**
	 * The geographical areas of the service provider;
	 */
	@Getter
	private final List<GeographicalArea> geographicalAreas;

	/**
	 * The categories of the service provider;
	 */
	@Getter
	private final Set<Category> categories;

	/**
	 * The list that contains all their daily availabilities.
	 */
	@Getter
	private final DailyAvailabilityList dailyAvailabilityList;

	/**
	 * The rating of the service provider;
	 */
	@Getter
	private final Rating rating;

	/**
	 * Constructs a new candidature without the categories and geographical areas.
	 *
	 * @param mechanographicalNumber the mechanographical number of the service provider
	 * @param completeName           the complete name of the service provider
	 * @param abbreviatedName        the abbreviated name of the service provider
	 * @param email                  the email of the service provider
	 */
	public ServiceProvider(String mechanographicalNumber, String completeName, String abbreviatedName, String email, PostCode postCode) {
		this.mechanographicalNumber = mechanographicalNumber;
		this.completeName = completeName;
		this.abbreviatedName = abbreviatedName;
		this.email = email;
		this.postCode = postCode;
		this.geographicalAreas = new ArrayList<>();
		this.categories = new HashSet<>();
		this.dailyAvailabilityList = new DailyAvailabilityList();
		this.rating = new Rating();
	}

	/**
	 * Verifies if a service provider is valid locally.
	 *
	 * @return <code>true</code> if the service provider is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !mechanographicalNumber.isEmpty() &&
				!completeName.isEmpty() &&
				!abbreviatedName.isEmpty() &&
				!email.isEmpty() &&
				postCode.isValid() &&
				WordUtils.isEmailValid(email);
	}

	/**
	 * Returns whether a service provider is eligible to work in a postal address or not.
	 *
	 * @param postalAddress the postal address
	 * @return <code>true</code> if the service provider is eligible to work in the postal address, <code>false</code> otherwise
	 */
	public boolean isEligible(PostalAddress postalAddress) {
		boolean eligible = false;

		for (GeographicalArea geographicalArea : geographicalAreas) {
			if (geographicalArea.getActing().containsKey(postalAddress.getPostCode())) {
				eligible = true;
				break;
			}
		}

		return eligible;
	}

	/**
	 * Adds a {@link Category} to the collection.
	 *
	 * @param category the category
	 * @return <code>true</code> if the category was successfully added, <code>false</code> otherwise
	 */
	public boolean addCategory(Category category) {
		return categories.add(category);
	}

	/**
	 * Adds a {@link GeographicalArea} to the collection.
	 *
	 * @param geographicalArea the geographical area
	 * @return <code>true</code> if the geographical area was successfully added, <code>false</code> otherwise
	 */
	public boolean addGeographicalArea(GeographicalArea geographicalArea) {
		if (geographicalAreas.contains(geographicalArea)) return false;
		return geographicalAreas.add(geographicalArea);
	}

	/**
	 * Gets the closes geographical area of a given post code.
	 *
	 * @param postCode the given post code
	 * @return the closes geographical area
	 */
	public GeographicalArea getClosestGeographicalArea(PostCode postCode) {
		GeographicalArea closestArea = geographicalAreas.get(0);
		double lowerDistance = PostCode.getDistance(closestArea.getCenteredPostCode(), postCode);

		for (GeographicalArea geographicalArea : geographicalAreas) {
			double distance = PostCode.getDistance(geographicalArea.getCenteredPostCode(), postCode);

			if (distance < lowerDistance) {
				lowerDistance = distance;
				closestArea = geographicalArea;
			}
		}

		return closestArea;
	}

	/**
	 * Verifies if this provider is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceProvider)) return false;
		ServiceProvider provider = (ServiceProvider) o;
		return getCompleteName().equals(provider.getCompleteName()) &&
				getAbbreviatedName().equals(provider.getAbbreviatedName()) &&
				getMechanographicalNumber().equals(provider.getMechanographicalNumber()) &&
				getEmail().equals(provider.getEmail()) &&
				getPostCode().equals(provider.getPostCode()) &&
				Objects.equals(getGeographicalAreas(), provider.getGeographicalAreas()) &&
				Objects.equals(getCategories(), provider.getCategories()) &&
				Objects.equals(getDailyAvailabilityList(), provider.getDailyAvailabilityList()) &&
				Objects.equals(getRating(), provider.getRating());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getCompleteName(), getAbbreviatedName(), getMechanographicalNumber(), getEmail(), getPostCode(), getGeographicalAreas(), getCategories(), getDailyAvailabilityList(), getRating());
	}
}
