package lapr2.framework.company.serviceexecutionorder;

import lapr2.framework.company.affectation.Affectation;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.Service;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceExecutionOrderManagerTest {

	@Test
	void createServiceExecutionOrder() {
		PostCode postCode = new PostCode("district", "municipal", "local", 0, 0, "postcode");
		PostalAddress postalAddress = new PostalAddress("address", postCode);
		Client client = new Client("name", "tax", "phone", "email", postalAddress);
		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);

		Category category = new Category("id", "description");
		Service service = new FixedService("id", "brief", "complete", 0, category);
		ServiceDescription serviceDescription = new ServiceDescription("description", service, 1);
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		ServiceProvider serviceProvider = new ServiceProvider("mec", "cname", "aname", "email", postCode1);
		Schedule schedule = new Schedule(2019, 6, 10, 10, 0);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		ServiceExecutionOrder expected = new ServiceExecutionOrder(affectation);

		ServiceExecutionOrderManager serviceExecutionOrderManager = new ServiceExecutionOrderManager("");
		ServiceExecutionOrder result = serviceExecutionOrderManager.createServiceExecutionOrder(affectation);

		assertEquals(expected, result);
	}

	@Test
	void isSecure() {
		PostCode postCode = new PostCode("district", "municipal", "local", 0, 0, "postcode");
		PostalAddress postalAddress = new PostalAddress("address", postCode);
		Client client = new Client("name", "tax", "phone", "email", postalAddress);
		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);

		Category category = new Category("id", "description");
		Service service = new FixedService("id", "brief", "complete", 0, category);
		ServiceDescription serviceDescription = new ServiceDescription("description", service, 1);
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		ServiceProvider serviceProvider = new ServiceProvider("mec", "cname", "aname", "email", postCode1);
		Schedule schedule = new Schedule(2019, 6, 10, 10, 0);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		ServiceExecutionOrder serviceExecutionOrder = new ServiceExecutionOrder(affectation);

		ServiceExecutionOrderManager serviceExecutionOrderManager = new ServiceExecutionOrderManager("");

		assertTrue(serviceExecutionOrderManager.isSecure(serviceExecutionOrder));

		serviceExecutionOrderManager.add(serviceExecutionOrder);

		assertFalse(serviceExecutionOrderManager.isSecure(serviceExecutionOrder));
	}

	@Test
	void isValid() {
		PostCode postCode = new PostCode("district", "municipal", "local", 0, 0, "postcode");
		PostalAddress postalAddress = new PostalAddress("address", postCode);
		Client client = new Client("name", "tax", "phone", "email", postalAddress);
		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);

		Category category = new Category("id", "description");
		Service service = new FixedService("id", "brief", "complete", 0, category);
		ServiceDescription serviceDescription = new ServiceDescription("description", service, 1);
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		ServiceProvider serviceProvider = new ServiceProvider("mec", "cname", "aname", "email", postCode1);
		Schedule schedule = new Schedule(2019, 6, 10, 10, 0);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		ServiceExecutionOrder serviceExecutionOrder = new ServiceExecutionOrder(affectation);

		ServiceExecutionOrderManager serviceExecutionOrderManager = new ServiceExecutionOrderManager("");

		assertTrue(serviceExecutionOrderManager.isValid(serviceExecutionOrder));

		serviceExecutionOrderManager.add(serviceExecutionOrder);

		assertFalse(serviceExecutionOrderManager.isValid(serviceExecutionOrder));
	}

	@Test
	void getServicesWaitingEvaluation() {
		List<ServiceExecutionOrder> expected = new ArrayList<>();

		PostCode postCode = new PostCode("district", "municipal", "local", 0, 0, "postcode");
		PostalAddress postalAddress = new PostalAddress("address", postCode);
		Client client = new Client("name", "tax", "phone", "email", postalAddress);
		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);
		Category category = new Category("id", "description");
		Service service = new FixedService("id", "brief", "complete", 0, category);
		ServiceDescription serviceDescription = new ServiceDescription("description", service, 1);
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");
		ServiceProvider serviceProvider = new ServiceProvider("mec", "cname", "aname", "email", postCode1);
		Schedule schedule = new Schedule(2019, 6, 10, 10, 0);
		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		ServiceExecutionOrder serviceExecutionOrderWithWaitingState = new ServiceExecutionOrder(affectation);

		PostCode postCode2 = new PostCode("district1", "municipal1", "local1", 1, 1, "postcode1");
		PostalAddress postalAddress1 = new PostalAddress("address1", postCode1);
		Client client1 = new Client("name1", "tax1", "phone1", "email1", postalAddress1);
		ServiceRequest serviceRequest1 = new ServiceRequest(client1, postalAddress1);
		Category category1 = new Category("id1", "description1");
		Service service1 = new FixedService("id1", "brief1", "complete1", 1, category1);
		ServiceDescription serviceDescription1 = new ServiceDescription("description1", service1, 2);

		Schedule schedule1 = new Schedule(2020, 6, 10, 10, 0);
		Affectation affectation1 = new Affectation(serviceRequest1, serviceDescription1, serviceProvider, schedule1);
		ServiceExecutionOrder serviceExecutionOrderWithToDoState = new ServiceExecutionOrder(affectation1);

		ServiceExecutionOrder.State state = ServiceExecutionOrder.State.TO_DO;
		serviceExecutionOrderWithWaitingState.setState(state);

		expected.add(serviceExecutionOrderWithWaitingState);
		ServiceExecutionOrderManager serviceExecutionOrderManager = new ServiceExecutionOrderManager("");
		serviceExecutionOrderManager.add(serviceExecutionOrderWithWaitingState);
		serviceExecutionOrderManager.add(serviceExecutionOrderWithToDoState);

		ServiceProvider serviceProvider1 = new ServiceProvider("a", "a", "a", "a", postCode);
		List<ServiceExecutionOrder> result = serviceExecutionOrderManager.getServicesWaitingEvaluation(serviceProvider);
		List<ServiceExecutionOrder> result1 = serviceExecutionOrderManager.getServicesWaitingEvaluation(serviceProvider1);

		assertEquals(expected, result);
		assertNotEquals(expected, result1);

		serviceExecutionOrderWithWaitingState.setState(ServiceExecutionOrder.State.EVALUATED);
		assertNotEquals(expected, serviceExecutionOrderManager.getServicesWaitingEvaluation(serviceProvider));
	}

	@Test
	void getOrdersOfClient() {
		PostCode postCode = new PostCode("district", "municipal", "local", 0, 0, "postcode");
		PostalAddress postalAddress = new PostalAddress("address", postCode);
		Client client = new Client("name", "tax", "phone", "email", postalAddress);
		Client client1 = new Client("name1", "tax", "phone", "email", postalAddress);
		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest1 = new ServiceRequest(client1, postalAddress);

		Category category = new Category("id", "description");
		Service service = new FixedService("id", "brief", "complete", 0, category);
		ServiceDescription serviceDescription = new ServiceDescription("description", service, 1);
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		ServiceProvider serviceProvider = new ServiceProvider("mec", "cname", "aname", "email", postCode1);

		Schedule schedule = new Schedule(2019, 6, 10, 10, 0);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		Affectation affectation1 = new Affectation(serviceRequest1, serviceDescription, serviceProvider, schedule);

		ServiceExecutionOrder serviceExecutionOrder = new ServiceExecutionOrder(affectation);
		ServiceExecutionOrder serviceExecutionOrder1 = new ServiceExecutionOrder(affectation1);

		ServiceExecutionOrderManager manager = new ServiceExecutionOrderManager("");
		manager.add(serviceExecutionOrder);
		manager.add(serviceExecutionOrder1);

		assertTrue(manager.getOrdersOfClient(client).contains(serviceExecutionOrder));
		assertTrue(manager.getOrdersOfClient(client1).contains(serviceExecutionOrder1));
		assertFalse(manager.getOrdersOfClient(client).contains(serviceExecutionOrder1));
		assertFalse(manager.getOrdersOfClient(client1).contains(serviceExecutionOrder));
	}

	@Test
	void getCompletedServiceExecutionOrdersOfClient() {

	}

	@Test
	void getCompletedUnratedServiceExecutionOrdersOfClient() {
	}

	@Test
	void getCompletedUnratedServiceExecutionOrdersOfServiceRequest() {
	}
}