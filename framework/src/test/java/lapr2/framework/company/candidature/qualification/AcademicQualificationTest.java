package lapr2.framework.company.candidature.qualification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class AcademicQualificationTest {

	@Test
	void equalsAndHashCode() {
		AcademicQualification qualification = new AcademicQualification("a", "a", "a");
		AcademicQualification qualification1 = new AcademicQualification("", "a", "a");
		AcademicQualification qualification2 = new AcademicQualification("a", "", "a");
		AcademicQualification qualification3 = new AcademicQualification("a", "a", "");
		AcademicQualification qualification4 = new AcademicQualification("a", "a", "a");

		assertNotEquals(qualification, new Object());
		assertEquals(qualification, qualification);
		assertNotEquals(qualification, qualification1);
		assertNotEquals(qualification, qualification2);
		assertNotEquals(qualification, qualification3);
		assertEquals(qualification, qualification4);

		assertEquals(qualification.hashCode(), qualification.hashCode());
		assertNotEquals(qualification.hashCode(), qualification1.hashCode());
		assertNotEquals(qualification.hashCode(), qualification2.hashCode());
		assertNotEquals(qualification.hashCode(), qualification3.hashCode());
		assertEquals(qualification.hashCode(), qualification4.hashCode());

	}

}