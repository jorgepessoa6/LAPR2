package lapr2.framework.company.candidature;

import lapr2.framework.company.candidature.qualification.AcademicQualification;
import lapr2.framework.company.candidature.qualification.ProfessionalQualification;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidatureTest {

	@Test
	void isValid() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		PostalAddress wrongPostalAddress = new PostalAddress("", postCode);

		Candidature candidature = new Candidature("abc", "999999990", "912345678", "abcd@gmail.com", postalAddress);
		Candidature candidature1 = new Candidature("", "999999990", "912345678", "abcd@gmail.com", postalAddress);
		Candidature candidature2 = new Candidature("abc", "", "912345678", "abcd@gmail.com", postalAddress);
		Candidature candidature3 = new Candidature("abc", "999999990", "", "abcd@gmail.com", postalAddress);
		Candidature candidature4 = new Candidature("abc", "999999990", "912345678", "", postalAddress);
		Candidature candidature5 = new Candidature("abc", "999999990", "912345678", "abcd@gmail.com", wrongPostalAddress);
		Candidature candidature6 = new Candidature("abc", "abc", "912345678", "abcd@gmail.com", postalAddress);
		Candidature candidature7 = new Candidature("abc", "999999990", "abc", "abcd@gmail.com", postalAddress);
		Candidature candidature8 = new Candidature("abc", "999999990", "912345678", "abcd", postalAddress);

		assertTrue(candidature.isValid());
		assertFalse(candidature1.isValid());
		assertFalse(candidature2.isValid());
		assertFalse(candidature3.isValid());
		assertFalse(candidature4.isValid());
		assertFalse(candidature5.isValid());
		assertFalse(candidature6.isValid());
		assertFalse(candidature7.isValid());
		assertFalse(candidature8.isValid());

	}

	@Test
	void createAcademicQualification() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		Candidature candidature = new Candidature("abc", "1234", "123", "abcd@gmail.com", postalAddress);

		String degree = "degree";
		String designation = "designation";
		String classification = "classification";
		AcademicQualification academicQualification = new AcademicQualification(degree, designation, classification);

		assertEquals(candidature.createAcademicQualification(degree, designation, classification), academicQualification);
	}

	@Test
	void addAcademicQualification() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		Candidature candidature = new Candidature("abc", "1234", "123", "abcd@gmail.com", postalAddress);

		AcademicQualification academicQualification = new AcademicQualification("5", "abc", "18");
		candidature.addAcademicQualification(academicQualification);

		assertTrue(candidature.getAcademicQualifications().contains(academicQualification));
		assertFalse(candidature.getAcademicQualifications().add(academicQualification));
	}

	@Test
	void createProfessionalQualification() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		Candidature candidature = new Candidature("abc", "1234", "123", "abcd@gmail.com", postalAddress);

		String designation = "designation";
		ProfessionalQualification professionalQualification = new ProfessionalQualification(designation);

		assertEquals(candidature.createProfessionalQualification(designation), professionalQualification);
	}

	@Test
	void addProfessionalQualification() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		Candidature candidature = new Candidature("abc", "1234", "123", "abcd@gmail.com", postalAddress);

		ProfessionalQualification professionalQualification = new ProfessionalQualification("abc");
		candidature.addProfessionalQualification(professionalQualification);

		assertTrue(candidature.getProfessionalQualifications().contains(professionalQualification));
		assertFalse(candidature.getProfessionalQualifications().add(professionalQualification));

	}

	@Test
	void equalsAndHashCode() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		PostalAddress otherPostalAddress = new PostalAddress("Lisboa", postCode);
		String name = "name";
		String taxIdentificationNumber = "123";
		String phoneNumber = "1234";
		String email = "abcd@gmail.com";

		Candidature candidature = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);

		Object object = new Object();
		Candidature candidature1 = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Candidature candidature2 = new Candidature("", taxIdentificationNumber, phoneNumber, email, postalAddress);
		Candidature candidature3 = new Candidature(name, "", phoneNumber, email, postalAddress);
		Candidature candidature4 = new Candidature(name, taxIdentificationNumber, "", email, postalAddress);
		Candidature candidature5 = new Candidature(name, taxIdentificationNumber, phoneNumber, "", postalAddress);
		Candidature candidature6 = new Candidature(name, taxIdentificationNumber, phoneNumber, email, otherPostalAddress);
		Candidature candidature7 = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Candidature candidature8 = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Candidature candidature9 = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Candidature candidature10 = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);

		candidature1.addAcademicQualification(new AcademicQualification("", "", ""));
		candidature8.addAcademicQualification(new AcademicQualification("", "", ""));
		candidature9.addAcademicQualification(new AcademicQualification("", "", ""));
		candidature10.addAcademicQualification(new AcademicQualification("", "", ""));
		candidature.addAcademicQualification(new AcademicQualification("", "", ""));

		candidature1.addProfessionalQualification(new ProfessionalQualification(""));
		candidature9.addProfessionalQualification(new ProfessionalQualification(""));
		candidature10.addProfessionalQualification(new ProfessionalQualification(""));
		candidature.addProfessionalQualification(new ProfessionalQualification(""));

		candidature1.addCategory(new Category("", ""));
		candidature10.addCategory(new Category("", ""));
		candidature.addCategory(new Category("", ""));

		candidature1.getCandidatureState().setCurrentState(CandidatureState.State.REJECTED);
		candidature.getCandidatureState().setCurrentState(CandidatureState.State.REJECTED);

		assertNotEquals(candidature, object);
		assertEquals(candidature, candidature);
		assertEquals(candidature, candidature1);
		assertNotEquals(candidature, candidature2);
		assertNotEquals(candidature, candidature3);
		assertNotEquals(candidature, candidature4);
		assertNotEquals(candidature, candidature5);
		assertNotEquals(candidature, candidature6);
		assertNotEquals(candidature, candidature7);
		assertNotEquals(candidature, candidature8);
		assertNotEquals(candidature, candidature9);
		assertNotEquals(candidature, candidature10);

		assertEquals(candidature.hashCode(), candidature.hashCode());
		assertEquals(candidature.hashCode(), candidature1.hashCode());
		assertNotEquals(candidature.hashCode(), candidature2.hashCode());
		assertNotEquals(candidature.hashCode(), candidature3.hashCode());
		assertNotEquals(candidature.hashCode(), candidature4.hashCode());
		assertNotEquals(candidature.hashCode(), candidature5.hashCode());
		assertNotEquals(candidature.hashCode(), candidature6.hashCode());
		assertNotEquals(candidature.hashCode(), candidature7.hashCode());
		assertNotEquals(candidature.hashCode(), candidature8.hashCode());
		assertNotEquals(candidature.hashCode(), candidature9.hashCode());
		assertNotEquals(candidature.hashCode(), candidature10.hashCode());
	}


	@Test
	void addCategory() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		Candidature candidature = new Candidature("abc", "1234", "123", "abcd@gmail.com", postalAddress);

		String id = "id";
		String description = "description";
		Category category = new Category(id, description);

		assertTrue(candidature.addCategory(category));

		candidature.addCategory(category);
		assertTrue(candidature.getCategories().contains(category));
		assertFalse(candidature.addCategory(category));
	}
}