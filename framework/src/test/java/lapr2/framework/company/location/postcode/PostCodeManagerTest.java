package lapr2.framework.company.location.postcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostCodeManagerTest {


	@Test
	void getPostCode() {
		String district = "district";
		String municipality = "municipality";
		String locality = "locality";
		double latitude = 0;
		double longitude = 0;
		String designation = "4560-123", anotherDesignation = "1234-123";
		PostCode postCode = new PostCode(district, municipality, locality, latitude, longitude, designation);

		PostCodeManager manager = new PostCodeManager("");
		manager.add(postCode);

		assertEquals(postCode, manager.getPostCode(designation));
		assertNotEquals(postCode, manager.getPostCode(anotherDesignation));
		assertNull(manager.getPostCode(anotherDesignation));
	}


	@Test
	void isSecure() {
		String district = "district";
		String municipality = "municipality";
		String locality = "locality";
		double latitude = 0;
		double longitude = 0;
		String designation = "4560-123", anotherDesignation = "1234-123";
		PostCode postCode = new PostCode(district, municipality, locality, latitude, longitude, designation);
		PostCode postCode1 = new PostCode(district, municipality, locality, latitude, longitude, designation);
		PostCode postCode2 = new PostCode(district, municipality, locality, latitude, longitude, anotherDesignation);
        PostCode postCode3 = new PostCode("", municipality, locality, latitude, longitude, anotherDesignation);
        PostCode postCode4 = new PostCode(district, "", locality, latitude, longitude, anotherDesignation);
        PostCode postCode5 = new PostCode(district, municipality, "", latitude, longitude, anotherDesignation);
        PostCode postCode6 = new PostCode(district, municipality, locality, 200, longitude, anotherDesignation);
        PostCode postCode7 = new PostCode(district, municipality, locality, latitude, 200, anotherDesignation);
        PostCode postCode8 = new PostCode(district, municipality, locality, 200, longitude, "");


		PostCodeManager manager = new PostCodeManager("");

		assertTrue(manager.isSecure(postCode));
		manager.add(postCode);

		assertFalse(manager.isSecure(postCode1));
		assertTrue(manager.isSecure(postCode2));
        assertFalse(manager.isSecure(postCode3));
        assertFalse(manager.isSecure(postCode4));
        assertFalse(manager.isSecure(postCode5));
        assertFalse(manager.isSecure(postCode6));
        assertFalse(manager.isSecure(postCode7));
        assertFalse(manager.isSecure(postCode8));
	}
}