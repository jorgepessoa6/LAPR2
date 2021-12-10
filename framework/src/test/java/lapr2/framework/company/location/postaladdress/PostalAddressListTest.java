package lapr2.framework.company.location.postaladdress;

import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostalAddressListTest {

    @Test
    void createPostalAddress() {
        String address = "Porto";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostalAddress expected = new PostalAddress(address, postCode);

        PostalAddressList postalAddressList = new PostalAddressList();

        assertEquals(expected, postalAddressList.createPostalAddress(address, postCode));

    }

    @Test
    void addPostalAddress() {

        String address = "Porto";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");

        PostalAddressList postalAddressList = new PostalAddressList();

        PostalAddress postalAddress = postalAddressList.createPostalAddress(address, postCode);
		postalAddressList.add(postalAddress);

        assertTrue(postalAddressList.getElements().contains(postalAddress));
		assertFalse(postalAddressList.add(postalAddress));
    }

    @Test
    void isSecure() {

        PostalAddressList postalAddressList = new PostalAddressList();

        String address = "Porto";
        PostCode postCodeInvalid = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", 100, -170d, "TestPostCode");
        PostCode postCodeValid = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        PostalAddress postalAddressValid = new PostalAddress(address, postCodeValid);
        PostalAddress postalAddressRepeated = new PostalAddress(address, postCodeValid);
        PostalAddress postalAddressInvalid = new PostalAddress(address, postCodeInvalid);
        PostalAddress postalAddressInvalid1 = new PostalAddress("", postCodeValid);
        PostalAddress postalAddressInvalid2 = new PostalAddress(address, null);

        assertTrue(postalAddressList.isSecure(postalAddressValid));
		postalAddressList.add(postalAddressValid);

        assertFalse(postalAddressList.isSecure(postalAddressInvalid));
        assertFalse(postalAddressList.isSecure(postalAddressInvalid1));
        assertFalse(postalAddressList.isSecure(postalAddressInvalid2));
        assertFalse(postalAddressList.isSecure(postalAddressRepeated));
		assertFalse(postalAddressList.isSecure(null));
    }

	@Test
	void getPostalAddress() {
		PostalAddressList list = new PostalAddressList();

		String address = "Porto";
		String designation = "postcode";
		PostCode postCodeValid = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, designation);

		PostalAddress postalAddressValid = new PostalAddress(address, postCodeValid);

		list.add(postalAddressValid);

		assertEquals(postalAddressValid, list.getPostalAddress(designation));
		assertNull(list.getPostalAddress("no"));
	}

	@Test
	void equalsAndHashCode() {
		PostalAddressList list = new PostalAddressList();
		PostalAddressList list1 = new PostalAddressList();
		PostalAddressList list2 = new PostalAddressList();

		String address = "Porto";
		PostCode postCodeValid = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		PostalAddress postalAddressValid = new PostalAddress(address, postCodeValid);

		list.add(postalAddressValid);
		list2.add(postalAddressValid);

		assertNotEquals(list, new Object());
		assertEquals(list, list);
		assertNotEquals(list, list1);
		assertEquals(list, list2);

		assertEquals(list.hashCode(), list.hashCode());
		assertNotEquals(list.hashCode(), list1.hashCode());
		assertEquals(list.hashCode(), list2.hashCode());
	}
}