package lapr2.framework.company.candidature.qualification;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents the academic qualification of a candidate, providing access to its degree, designation and qualification.
 *
 * @author flow
 */
@AllArgsConstructor
public class AcademicQualification implements Serializable {

	private static final long serialVersionUID = 4235368001967473503L;

	/**
	 * The degree of the academic qualification.
	 */
	@Getter
	private final String degree;

	/**
	 * The designation of the academic qualification.
	 */
	@Getter
	private final String designation;

	/**
	 * The classification of the academic qualification.
	 */
	@Getter
	private final String classification;

	/**
	 * Verifies if the academic qualification is valid.
	 *
	 * @return <code>true</code> if the academic qualification is valid, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !degree.isEmpty() &&
				!designation.isEmpty() &&
				!classification.isEmpty();
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
		if (!(o instanceof AcademicQualification)) return false;
		AcademicQualification that = (AcademicQualification) o;
		return Objects.equals(getDegree(), that.getDegree()) &&
				Objects.equals(getDesignation(), that.getDesignation()) &&
				Objects.equals(getClassification(), that.getClassification());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getDegree(), getDesignation(), getClassification());
	}
}
