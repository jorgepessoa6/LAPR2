package lapr2.framework.company.candidature;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.manager.Manager;

/**
 * Stores and retrieves all the {@link Candidature}. Also allows their creation and validation.
 *
 * @author flow
 */
public class CandidatureManager extends Manager<Candidature> {

	/**
	 * Constructs a new candidature manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public CandidatureManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link Candidature} returning it.
	 *
	 * @param name                    the complete name of the candidate
	 * @param taxIdentificationNumber the Tax Identification Number of the candidate
	 * @param phoneNumber             the Phone number of the candidate
	 * @param email                   the email of the candidate
	 * @param postalAddress           the Postal address of the candidate
	 * @return the created candidature
	 */
	public Candidature createCandidature(String name, String taxIdentificationNumber, String phoneNumber, String email, PostalAddress postalAddress) {
		return new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
	}

	/**
	 * Verifies if a {@link Candidature} is valid.
	 *
	 * @param candidature the Candidature
	 * @return <code>true</code> if the Candidature is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(Candidature candidature) {
		return (candidature.isValid() && this.isValid(candidature));
	}


	/**
	 * Verifies if a candidature is valid globally.
	 *
	 * @param candidature the candidature
	 * @return <code>true</code> if the candidature is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(Candidature candidature) {
		for (Candidature listedCandidature : elements)
			if (listedCandidature.getTaxIdentificationNumber().equals(candidature.getTaxIdentificationNumber()) || listedCandidature.getEmail().equals(candidature.getEmail()))
				return false;

		return true;
	}

	/**
	 * Returns the candidature associated with a given tax identification number.
	 * Returns null if such candidature does not exist.
	 *
	 * @param taxIdentificationNumber the tax identification number
	 * @return the candidature associated with the tax identification number
	 */
	public Candidature getCandidature(String taxIdentificationNumber) {
		for (Candidature candidature : elements) {
			if (candidature.getTaxIdentificationNumber().equals(taxIdentificationNumber)) {
				return candidature;
			}
		}
		return null;
	}
}
