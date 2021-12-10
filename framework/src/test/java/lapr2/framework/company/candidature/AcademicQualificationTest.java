package lapr2.framework.company.candidature;

import lapr2.framework.company.candidature.qualification.AcademicQualification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AcademicQualificationTest {

	@Test
	void isValid() {
		String degree = "degree";
		String designation = "designation";
		String classification = "classification";

		String empty = "";

		AcademicQualification validAcademicQualification = new AcademicQualification(degree, designation, classification);
		AcademicQualification invalidDegreeAcademicQualification = new AcademicQualification(empty, designation, classification);
		AcademicQualification invalidDesignationAcademicQualification = new AcademicQualification(degree, empty, classification);
		AcademicQualification invalidClassificationAcademicQualification = new AcademicQualification(degree, designation, empty);

		assertTrue(validAcademicQualification.isValid());
		assertFalse(invalidDegreeAcademicQualification.isValid());
		assertFalse(invalidDesignationAcademicQualification.isValid());
		assertFalse(invalidClassificationAcademicQualification.isValid());
	}

	@Test
	void equals() {
		AcademicQualification academicQualification1 = new AcademicQualification("Lorem", "ipsum", "dolor");
		AcademicQualification academicQualification2 = new AcademicQualification("sit", "ipsum", "dolor");
		AcademicQualification academicQualification3 = new AcademicQualification("Lorem", "amet", "dolor");
		AcademicQualification academicQualification4 = new AcademicQualification("Lorem", "ipsum", "consectetur");
		Object object = new Object();

		assertEquals(academicQualification1, academicQualification1);
		assertNotEquals(academicQualification1, object);
		assertNotEquals(academicQualification1, academicQualification2);
		assertNotEquals(academicQualification1, academicQualification3);
		assertNotEquals(academicQualification1, academicQualification4);

	}
}