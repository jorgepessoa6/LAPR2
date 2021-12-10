package lapr2.framework.company.location.geographicalservice;

import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.location.postcode.PostCodeManager;
import lapr2.framework.homeservices.HomeServices;

import java.util.HashMap;

/**
 * Represents an implementation of the {@link ExternalGeographicalService}.
 */
public class GeographicalService implements ExternalGeographicalService {

	/**
	 * Computes the acting of a centered postcode.
	 *
	 * @param postCode the centered postcode
	 * @param radius   the radius of acting
	 * @return the acting
	 */
	@Override
	public HashMap<PostCode, Double> getActing(PostCode postCode, double radius) {
		HashMap<PostCode, Double> acting = new HashMap<>();

		PostCodeManager postCodeManager = HomeServices.getInstance().getCompany().getDataManager().get(PostCodeManager.class);

		for (PostCode listPostCode : postCodeManager.getElements()) {
			double distance = PostCode.getDistance(postCode, listPostCode);

			if (distance <= radius) {
				acting.put(listPostCode, distance);
			}
		}

		return acting;
	}
}
