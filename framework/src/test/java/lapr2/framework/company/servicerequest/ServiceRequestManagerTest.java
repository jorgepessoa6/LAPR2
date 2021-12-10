package lapr2.framework.company.servicerequest;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.user.client.Client;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceRequestManagerTest {

	@Test
	void createServiceRequest() {
		PostCode postCode = new PostCode("", "", "", 0, 0, "");
		PostalAddress postalAddress = new PostalAddress("", postCode);
		PostalAddress anotherPostalAddress = new PostalAddress("a", postCode);
		Client client = new Client("", "", "", "", postalAddress);

		ServiceRequest expected = new ServiceRequest(client, postalAddress);

		ServiceRequestManager manager = new ServiceRequestManager("");

		assertEquals(expected, manager.createServiceRequest(client, postalAddress));
		assertNotEquals(expected, manager.createServiceRequest(client, anotherPostalAddress));
	}

	@Test
	void getServicesRequests() {
		ServiceRequestManager manager = new ServiceRequestManager("");

		PostCode postCode = new PostCode("", "", "", 0, 0, "");
		PostalAddress postalAddress = new PostalAddress("", postCode);
		PostalAddress anotherPostalAddress = new PostalAddress("a", postCode);
		Client client = new Client("", "", "", "", postalAddress);
		Client client1 = new Client("a", "a", "a", "a", anotherPostalAddress);

		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest1 = new ServiceRequest(client1, postalAddress);

		manager.add(serviceRequest1);
		manager.add(serviceRequest);

		assertTrue(manager.getServicesRequests(client).contains(serviceRequest));
		assertTrue(manager.getServicesRequests(client1).contains(serviceRequest1));
	}
}