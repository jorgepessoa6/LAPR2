package lapr2.framework.company.candidature.qualification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ProfessionalQualificationTest {

	@Test
	void equalsAndHashCode() {
		ProfessionalQualification qualification = new ProfessionalQualification("desi");
		ProfessionalQualification qualification1 = new ProfessionalQualification("desia");
		ProfessionalQualification qualification2 = new ProfessionalQualification("desi");

		assertNotEquals(qualification, new Object());
		assertEquals(qualification, qualification);
		assertNotEquals(qualification, qualification1);
		assertEquals(qualification, qualification2);

		assertEquals(qualification.hashCode(), qualification.hashCode());
		assertNotEquals(qualification.hashCode(), qualification1.hashCode());
		assertEquals(qualification.hashCode(), qualification2.hashCode());
	}
}