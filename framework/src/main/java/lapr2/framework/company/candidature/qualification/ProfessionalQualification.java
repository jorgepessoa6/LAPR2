package lapr2.framework.company.candidature.qualification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a professional qualification of a candidate, providing access to its description.
 *
 * @author flow
 */
@AllArgsConstructor
public class ProfessionalQualification implements Serializable {

	private static final long serialVersionUID = -8152592361473311965L;

	/**
	 * The description of the professional qualification.
	 */
	@Getter
	private final String description;

	/**
	 * Verifies if the professional qualification is valid.
	 *
	 * @return <code>true</code> if the professional qualification is valid, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !description.isEmpty();
	}

	/**
	 * Verifies if this qualification is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ProfessionalQualification)) return false;
		ProfessionalQualification that = (ProfessionalQualification) o;
		return Objects.equals(getDescription(), that.getDescription());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getDescription());
	}
}
