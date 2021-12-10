package lapr2.framework.algorithm;

import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.client.Client;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FirstComeFirstServedSchedulingTest {

	@Test
	void orderRequests() {
		PostCode postCode = new PostCode("", "", "", 0, 0, "");
		PostalAddress postalAddress = new PostalAddress("", postCode), postalAddress1 = new PostalAddress("123", postCode);
		Client client = new Client("", "", "", "", postalAddress);
		ServiceRequest request = new ServiceRequest(client, postalAddress);
		ServiceRequest request1 = new ServiceRequest(client, postalAddress1);

		List<ServiceRequest> serviceRequests = new ArrayList<>();
		serviceRequests.add(request);
		serviceRequests.add(request1);
		List<ServiceRequest> serviceRequestsUnordered = new ArrayList<>();
		serviceRequestsUnordered.add(request1);
		serviceRequestsUnordered.add(request);

		FirstComeFirstServedScheduling scheduling = new FirstComeFirstServedScheduling();

		assertEquals(serviceRequests, scheduling.orderRequests(serviceRequests));
		assertNotEquals(serviceRequests, scheduling.orderRequests(serviceRequestsUnordered));
	}
}