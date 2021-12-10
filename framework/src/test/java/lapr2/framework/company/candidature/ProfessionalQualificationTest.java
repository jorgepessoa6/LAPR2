package lapr2.framework.company.candidature;

import lapr2.framework.company.candidature.qualification.ProfessionalQualification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfessionalQualificationTest {

	@Test
	void isValid() {
		String description = "description";

		ProfessionalQualification professionalQualification = new ProfessionalQualification(description);
		ProfessionalQualification professionalQualification1 = new ProfessionalQualification("");

		assertTrue(professionalQualification.isValid());
		assertFalse(professionalQualification1.isValid());
	}

	@Test
	void equals() {
		String description = "description";
		String otherDescription = "other description";
		ProfessionalQualification professionalQualification = new ProfessionalQualification(description);

		Object object = new Object();
		ProfessionalQualification professionalQualification1 = new ProfessionalQualification(description);
		ProfessionalQualification professionalQualification2 = new ProfessionalQualification(otherDescription);

		assertNotEquals(professionalQualification, object);
		assertEquals(professionalQualification, professionalQualification1);
		assertNotEquals(professionalQualification, professionalQualification2);
	}
}