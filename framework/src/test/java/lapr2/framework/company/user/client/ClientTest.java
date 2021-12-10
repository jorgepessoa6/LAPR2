package lapr2.framework.company.user.client;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

	@Test
	void isValid() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		String name = "name";
		String taxIdentificationNumber = "999999990";
		String phoneNumber = "912345678";
		String email = "email@email.com";

		Client client = new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Client client1 = new Client("", taxIdentificationNumber, phoneNumber, email, postalAddress);
		Client client2 = new Client(name, "", phoneNumber, email, postalAddress);
		Client client3 = new Client(name, taxIdentificationNumber, "", email, postalAddress);
		Client client4 = new Client(name, taxIdentificationNumber, phoneNumber, "", postalAddress);
		Client client5 = new Client(name, "acb", phoneNumber, email, postalAddress);
		Client client6 = new Client(name, taxIdentificationNumber, "acb", email, postalAddress);
		Client client7 = new Client(name, taxIdentificationNumber, phoneNumber, "email.com", postalAddress);

		assertTrue(client.isValid());
		assertFalse(client1.isValid());
		assertFalse(client2.isValid());
		assertFalse(client3.isValid());
		assertFalse(client4.isValid());
		assertFalse(client5.isValid());
		assertFalse(client6.isValid());
		assertFalse(client7.isValid());
	}

	@Test
	void equalsAndHashCode() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);
		PostalAddress postalAddress1 = new PostalAddress("Lisboa", postCode);
		String name = "name";
		String taxIdentificationNumber = "999999990";
		String phoneNumber = "912345678";
		String email = "email@email.com";

		Client client = new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Client client1 = new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Client client2 = new Client(name, "99999991", phoneNumber, email, postalAddress);
		Client client3 = new Client(name, taxIdentificationNumber, "93214212", email, postalAddress);
		Client client4 = new Client(name, taxIdentificationNumber, phoneNumber, "email1@email.com", postalAddress);
		Client client5 = new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress1);
		Client client6 = new Client("name1", taxIdentificationNumber, phoneNumber, email, postalAddress);

		Object object = new Object();

		assertNotEquals(client, object);
		assertEquals(client, client);
		assertEquals(client, client1);
		assertNotEquals(client, client2);
		assertNotEquals(client, client3);
		assertNotEquals(client, client4);
		assertNotEquals(client, client5);
		assertNotEquals(client, client6);

		assertFalse(client.hashCode() == object.hashCode());
		assertEquals(client.hashCode(), client1.hashCode());
		assertEquals(client.hashCode(), client.hashCode());
		assertFalse(client.hashCode() == client2.hashCode());
		assertFalse(client.hashCode() == client3.hashCode());
		assertFalse(client.hashCode() == client4.hashCode());
		assertFalse(client.hashCode() == client5.hashCode());
	}

}