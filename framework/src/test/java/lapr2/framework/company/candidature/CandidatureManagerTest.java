package lapr2.framework.company.candidature;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CandidatureManagerTest {

    @Test
    void createCandidature() {
        CandidatureManager manager = new CandidatureManager("");

        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		String name = "abc";
		String taxIdentificationNumber = "123";
		String phoneNumber = "123";
		String email = "abcd@gmail.com";

		Candidature expected = new Candidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress);

		assertEquals(expected, manager.createCandidature(name, taxIdentificationNumber, phoneNumber, email, postalAddress));
    }

    @Test
	void add() {
        CandidatureManager manager = new CandidatureManager("");
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostalAddress postalAddress = new PostalAddress("Porto", postCode);
        Candidature candidature = new Candidature("abc", "123", "123", "abc", postalAddress);
		Candidature candidature1 = new Candidature("abc", "123", "123", "abc", postalAddress);
		candidature1.getCandidatureState().setDate(candidature.getCandidatureState().getDate());

		manager.add(candidature);

        assertTrue(manager.getElements().contains(candidature));
		assertFalse(manager.add(candidature));
		assertFalse(manager.add(candidature1));
    }

    @Test
    void isSecure() {
        CandidatureManager manager = new CandidatureManager("");
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostalAddress postalAddress = new PostalAddress("Porto", postCode);

		Candidature candidature = new Candidature("abc", "999999990", "912345678", "abc@gmail.com", postalAddress);
		Candidature candidature1 = new Candidature("", "999999990", "912345678", "abc@gmail.com", postalAddress);
		Candidature candidature2 = new Candidature("abc", "", "912345678", "abc@gmail.com", postalAddress);
		Candidature candidature3 = new Candidature("abc", "999999990", "", "abc@gmail.com", postalAddress);
		Candidature candidature4 = new Candidature("abc", "999999990", "912345678", "", postalAddress);
		Candidature candidature5 = new Candidature("abc", "999999990", "912345678", "abc@gmail.com", postalAddress);
		Candidature candidature6 = new Candidature("abc", "999999990", "912345678", "abc@gmail.com", postalAddress);

        assertTrue(manager.isSecure(candidature));
		manager.add(candidature);

        assertFalse(manager.isSecure(candidature1));
        assertFalse(manager.isSecure(candidature2));
        assertFalse(manager.isSecure(candidature3));
        assertFalse(manager.isSecure(candidature4));
        assertFalse(manager.isSecure(candidature5));
        assertFalse(manager.isSecure(candidature6));

    }

	@Test
	void getCandidature() {
		CandidatureManager manager = new CandidatureManager("");

		String taxId = "taxId";
		String taxId1 = "taxId1";

		assertNull(manager.getCandidature(taxId));

		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);

		Candidature candidature = new Candidature("abc", taxId, "912345678", "abc@gmail.com", postalAddress);
		Candidature candidature1 = new Candidature("abc", taxId1, "912345678", "abc@gmail.com", postalAddress);

		manager.add(candidature1);
		manager.add(candidature);

		assertEquals(candidature, manager.getCandidature(taxId));
	}
}