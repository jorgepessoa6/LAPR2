package lapr2.framework.company.location.postaladdress;

import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostalAddressTest {

    @Test
    void isValid() {
        String address = "Porto";
        PostCode postCodeInvalid = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", 100, -170d, "TestPostCode");
        PostCode postCodeValid = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        PostalAddress postalAddressValid = new PostalAddress(address, postCodeValid);
        PostalAddress postalAddressInvalid = new PostalAddress(address, postCodeInvalid);
        PostalAddress postalAddressInvalid1 = new PostalAddress("", postCodeValid);
        PostalAddress postalAddressInvalid2 = new PostalAddress(address, null);

        assertTrue(postalAddressValid.isValid());
        assertFalse(postalAddressInvalid.isValid());
        assertFalse(postalAddressInvalid1.isValid());
        assertFalse(postalAddressInvalid2.isValid());
    }

    @Test
    void equalsAndHashCode() {
        String address = "Porto";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -70d, -170d, "TestPostCode");

        PostalAddress postalAddress = new PostalAddress(address, postCode);
        PostalAddress postalAddress1 = new PostalAddress(address, postCode);
        PostalAddress postalAddress2 = new PostalAddress(address, postCode1);
        PostalAddress postalAddress3 = new PostalAddress("Lisboa", postCode);

        Object object = new Object();

        assertNotEquals(postalAddress, object);
		assertEquals(postalAddress, postalAddress);
		assertEquals(postalAddress, postalAddress1);
        assertNotEquals(postalAddress, postalAddress2);
        assertNotEquals(postalAddress, postalAddress3);

        assertFalse(postalAddress.hashCode() == object.hashCode());
		assertEquals(postalAddress.hashCode(), postalAddress1.hashCode());
		assertEquals(postalAddress.hashCode(), postalAddress.hashCode());
        assertFalse(postalAddress.hashCode() == postalAddress2.hashCode());
        assertFalse(postalAddress.hashCode() == postalAddress3.hashCode());
    }

}