package lapr2.framework.company.candidature;

import lapr2.framework.company.candidature.qualification.AcademicQualification;
import lapr2.framework.company.candidature.qualification.ProfessionalQualification;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.utils.WordUtils;
import lombok.Getter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a candidature to service provider. Providing access to the candidate's complete name,
 * tax identification number, phone number, email, postal address and academic and professional qualifications.
 *
 * @author flow
 */
public class Candidature implements Serializable {

	private static final long serialVersionUID = 7184043445187227960L;

	/**
	 * The complete name of the candidate.
	 */
	@Getter
	private final String name;

	/**
	 * The tax identification number of the candidate.
	 */
	@Getter
	private final String taxIdentificationNumber;

	/**
	 * The phone number of the candidate.
	 */
	@Getter
	private final String phoneNumber;

	/**
	 * The email of the candidate.
	 */
	@Getter
	private final String email;

	/**
	 * The postal address of the candidate.
	 */
	@Getter
	private final PostalAddress postalAddress;

	/**
	 * The collection of academic qualifications.
	 */
	@Getter
	private final HashSet<AcademicQualification> academicQualifications;

	/**
	 * The collection of professional qualifications.
	 */
	@Getter
	private final HashSet<ProfessionalQualification> professionalQualifications;

	/**
	 * The collection of categories.
	 */
	@Getter
	private final Set<Category> categories;

	/**
	 * The actual currentState of the candidature.
	 */
	@Getter
	private CandidatureState candidatureState;

	/**
	 * Constructs a new candidature with no qualifications.
	 *
	 * @param name                    the complete name of the candidate
	 * @param taxIdentificationNumber the Tax Identification Number of the candidate.
	 * @param phoneNumber             the Phone number of the candidate.
	 * @param email                   the email of the candidate.
	 * @param postalAddress           the Postal address of the candidate.
	 */
	public Candidature(String name, String taxIdentificationNumber, String phoneNumber, String email, PostalAddress postalAddress) {
		this.name = name;
		this.taxIdentificationNumber = taxIdentificationNumber;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.postalAddress = postalAddress;
		this.academicQualifications = new HashSet<>();
		this.professionalQualifications = new HashSet<>();
		this.candidatureState = new CandidatureState();
		this.categories = new HashSet<>();
	}

	/**
	 * Verifies if a candidature is valid locally.
	 *
	 * @return <code>true</code> if the candidature is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !name.isEmpty() &&
				!taxIdentificationNumber.isEmpty() &&
				!phoneNumber.isEmpty() &&
				!email.isEmpty() &&
				postalAddress.isValid() &&
				WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber) &&
				WordUtils.isPhoneNumberValid(phoneNumber) &&
				WordUtils.isEmailValid(email);

	}

	/**
	 * Creates a new {@link AcademicQualification} returning it.
	 *
	 * @param degree         The degree of the academic qualification
	 * @param designation    The designation of the academic qualification
	 * @param classification The classification of the academic qualification
	 * @return the created academic qualification
	 */
	public AcademicQualification createAcademicQualification(String degree, String designation, String classification) {
		return new AcademicQualification(degree, designation, classification);
	}

	/**
	 * Adds a {@link AcademicQualification} to the collection.
	 *
	 * @param academicQualification the academic qualification
	 * @return <code>true</code> if the academic qualification was successfully added, <code>false</code> otherwise
	 */
	public boolean addAcademicQualification(AcademicQualification academicQualification) {
		return academicQualifications.add(academicQualification);
	}

	/**
	 * Creates a new {@link ProfessionalQualification} returning it.
	 *
	 * @param description The description of the professional qualification
	 * @return the created professional qualification
	 */
	public ProfessionalQualification createProfessionalQualification(String description) {
		return new ProfessionalQualification(description);
	}

	/**
	 * Adds a {@link ProfessionalQualification} to the collection.
	 *
	 * @param professionalQualification the professional qualification
	 * @return <code>true</code> if the professional qualification was successfully added, <code>false</code> otherwise
	 */
	public boolean addProfessionalQualification(ProfessionalQualification professionalQualification) {
		return professionalQualifications.add(professionalQualification);
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
	 * Verifies if this candidature is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Candidature)) return false;
		Candidature that = (Candidature) o;
		return Objects.equals(getName(), that.getName()) &&
				Objects.equals(getTaxIdentificationNumber(), that.getTaxIdentificationNumber()) &&
				Objects.equals(getPhoneNumber(), that.getPhoneNumber()) &&
				Objects.equals(getEmail(), that.getEmail()) &&
				Objects.equals(getPostalAddress(), that.getPostalAddress()) &&
				Objects.equals(getAcademicQualifications(), that.getAcademicQualifications()) &&
				Objects.equals(getProfessionalQualifications(), that.getProfessionalQualifications()) &&
				Objects.equals(getCategories(), that.getCategories()) &&
				Objects.equals(getCandidatureState(), that.getCandidatureState());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getName(), getTaxIdentificationNumber(), getPhoneNumber(), getEmail(), getPostalAddress(), getAcademicQualifications(), getProfessionalQualifications(), getCategories(), getCandidatureState());
	}

	@Override
	public String toString() {
		return (name + " (" + taxIdentificationNumber + ")");
	}

}
