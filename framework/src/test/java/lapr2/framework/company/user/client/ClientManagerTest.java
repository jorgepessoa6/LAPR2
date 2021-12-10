package lapr2.framework.company.user.client;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientManagerTest {

	@Test
	void createClient() {
		String name = "abc";
		String taxIdentificationNumber = "123";
		String phoneNumber = "123";
		String email = "abc@abc.com";
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);

		ClientManager manager = new ClientManager("");
		Client expected = new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress);

		assertEquals(expected, manager.createClient(name, taxIdentificationNumber, phoneNumber, email, postalAddress));
	}

	@Test
	void add() {
		ClientManager manager = new ClientManager("");

		String name = "abc";
		String taxIdentificationNumber = "123";
		String phoneNumber = "123";
		String email = "abc@abc.com";
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);

		Client client = manager.createClient(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		manager.add(client);

		assertTrue(manager.getElements().contains(client));
		assertFalse(manager.add(client));
	}

	@Test
	void isSecure() {
		String name = "abc";
		String taxIdentificationNumber = "999999990";
		String anotherTaxIdentificationNumber = "123456789";
		String phoneNumber = "912345678";
		String email = "abc@lapr.com";
		String anotherEmail = "123@lapr.com";
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostalAddress postalAddress = new PostalAddress("Porto", postCode);

		Client clientValid = new Client(name, taxIdentificationNumber, phoneNumber, email, postalAddress);
		Client clientInvalidId = new Client(name, "abc", phoneNumber, email, postalAddress);
		Client clientRepeatedId = new Client(name, taxIdentificationNumber, phoneNumber, anotherEmail, postalAddress);
		Client clientRepeatedEmail = new Client(name, anotherTaxIdentificationNumber, phoneNumber, email, postalAddress);

		ClientManager manager = new ClientManager("");

		assertTrue(manager.isSecure(clientValid));
		manager.add(clientValid);

		assertFalse(manager.isSecure(clientInvalidId));
		assertFalse(manager.isSecure(clientRepeatedId));
		assertFalse(manager.isSecure(clientRepeatedEmail));
	}

    @Test
    void getClient() {
        String name = "Abel";
        String name1 = "Marcio";
        String name2 = "Luis";
        String tin = "123456789";
        String tin1 = "012345678";
        String tin2 = "901234567";
        String phoneNumber = "123456789";
        String phoneNumber1 = "012345678";
        String phoneNumber2 = "901234567";
        String email = "abel@lapr.com";
        String email1 = "Marcio@lapr.com";
        String email2 = "Luis@lapr.com";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostalAddress postalAddress = new PostalAddress("Porto", postCode);

        Client client = new Client(name, tin, phoneNumber, email, postalAddress);
        Client client1 = new Client(name1, tin1, phoneNumber1, email1, postalAddress);
        Client client2 = new Client(name2, tin2, phoneNumber2, email2, postalAddress);
        ClientManager manager = new ClientManager("");

        manager.add(client);
        manager.add(client1);
        manager.add(client2);

		assertNull(manager.getClient("marcio@lapr.com"));
        assertEquals(manager.getClient("Marcio@lapr.com"), client1);

    }

    @Test
    void getClientTIN() {
        String name = "Abel";
        String name1 = "Marcio";
        String name2 = "Luis";
        String tin = "123456789";
        String tin1 = "012345678";
        String tin2 = "901234567";
        String phoneNumber = "123456789";
        String phoneNumber1 = "012345678";
        String phoneNumber2 = "901234567";
        String email = "Abel@lapr.com";
        String email1 = "Marcio@lapr.com";
        String email2 = "Luis@lapr.com";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
        PostalAddress postalAddress = new PostalAddress("Porto", postCode);

        Client client = new Client(name, tin, phoneNumber, email, postalAddress);
        Client client1 = new Client(name1, tin1, phoneNumber1, email1, postalAddress);
        Client client2 = new Client(name2, tin2, phoneNumber2, email2, postalAddress);
        ClientManager manager = new ClientManager("");

        manager.add(client);
        manager.add(client1);
        manager.add(client2);

        assertNull(manager.getClientTIN("123456780"));
        assertEquals(manager.getClientTIN("123456789"), client);
        assertEquals(manager.getClientTIN("012345678"), client1);
        assertEquals(manager.getClientTIN("901234567"), client2);

    }
}