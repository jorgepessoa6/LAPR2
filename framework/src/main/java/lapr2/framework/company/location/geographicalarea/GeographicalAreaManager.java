package lapr2.framework.company.location.geographicalarea;

import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.manager.Manager;

import java.util.HashMap;

/**
 * Stores and retrieves all the {@link GeographicalArea} of the Company. Also allows their
 * creation and validation.
 *
 * @author flow
 */
public class GeographicalAreaManager extends Manager<GeographicalArea> {

    /**
     * Constructs a new geographical area manager associating the path to its binary file.
     *
     * @param filePath the path to the binary file
     */
    public GeographicalAreaManager(String filePath) {
        super(filePath);
    }

    /**
     * Creates a new {@link GeographicalArea} returning it.
     *
     * @param designation the designation of the geographical area
     * @param postCode    the central post code of the geographical area
     * @param radius      the radius of the geographical area
     * @param travelCost  the travel cost of the geographical area
     * @param acting      the acting of the geographical area
     * @return the created geographical area
     */
	public GeographicalArea createGeographicalArea(String designation, PostCode postCode, double radius, double travelCost, HashMap<PostCode, Double> acting) {
        return new GeographicalArea(designation, postCode, radius, travelCost, acting);
    }

    /**
     * Verifies if a {@link GeographicalArea} is valid.
     *
     * @param geographicalArea the geographical area
     * @return <code>true</code> if the geographical area is valid, <code>false</code> otherwise
     */
    @Override
    public boolean isSecure(GeographicalArea geographicalArea) {
        return geographicalArea.isValid() && isValid(geographicalArea);
    }

    /**
     * Verifies if a geographical area is valid globally.
     *
     * @param geographicalArea the geographical area
     * @return <code>true</code> if the geographical area is valid globally, <code>false</code> otherwise
     */
    @Override
	public boolean isValid(GeographicalArea geographicalArea) {
        for (GeographicalArea listedGeographicalArea : elements)
            if (listedGeographicalArea.getDesignation().equals(geographicalArea.getDesignation())
                    || (listedGeographicalArea.getCenteredPostCode().equals(geographicalArea.getCenteredPostCode()) && listedGeographicalArea.getRadius() == geographicalArea.getRadius()))
                return false;

        return true;
    }

    /**
     * Returns the closest geographical area from a post code.
     *
     * @param postCode the post code to calculate the closest geographical area
     * @return the closest geographical area
     */
    public GeographicalArea getClosestGeographicalArea(PostCode postCode) {
		double distance;
        double closestDistance = 99999999;
        GeographicalArea closestGeographicalArea = null;

        for (GeographicalArea geographicalArea : elements) {
            PostCode geographicalAreaPostCode = geographicalArea.getCenteredPostCode();
            distance = PostCode.getDistance(postCode, geographicalAreaPostCode);
            if (distance <= closestDistance) {
                closestDistance = distance;
                closestGeographicalArea = geographicalArea;
            }
        }

        return closestGeographicalArea;
    }

	/**
	 * Returns a geographical area by its designation.
	 * Returns null f there is no such area,
	 *
	 * @param designation the designation
	 * @return the geographical area
	 */
	public GeographicalArea getGeographicalArea(String designation) {
		for (GeographicalArea geographicalArea : elements)
			if (geographicalArea.getDesignation().equals(designation))
				return geographicalArea;

		return null;
	}
}
