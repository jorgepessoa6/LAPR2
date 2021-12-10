package lapr2.framework.company.servicerequest;

import lapr2.framework.company.Company;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.service.ExpandableService;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.LimitedService;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.homeservices.HomeServices;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ServiceRequestTest {

	@Test
	void getTotalCost() {
		Company company = new Company("Test1", "Test2");

		HomeServices.getInstance().setCompany(company);

		GeographicalAreaManager geographicalAreaManager = new GeographicalAreaManager("");

		company.getDataManager().set(geographicalAreaManager);

		PostCode postCode = new PostCode("Porto", "Porto", "Avenida da Boavista", 41.160925d, -8.647292d, "test");

		GeographicalArea geographicalArea = new GeographicalArea("Grande Porto 1", postCode, 3000, 10, null);

		PostCode postCode1 = new PostCode("Porto", "Porto", "Rua de Egas Moniz", 41.162721d, -8.620486d, "test");

		geographicalAreaManager.add(geographicalArea);

		PostalAddress postalAddress = new PostalAddress("Test", postCode1);

		ServiceRequest serviceRequest = new ServiceRequest(null, postalAddress);

		LimitedService limitedService = new LimitedService("", "", "", 20d, null);

		ExpandableService expandableService = new ExpandableService("", "", "", 30d, null);

		ServiceDescription serviceDescription = new ServiceDescription("", limitedService, 2f);
		ServiceDescription serviceDescription1 = new ServiceDescription("", expandableService, 2.5f);

		serviceRequest.getServiceDescriptionList().getServiceDescriptions().addAll(Arrays.asList(serviceDescription, serviceDescription1));

		double expectedCost = 20d * 2f + 30d * 2.5f + geographicalArea.getTravelCost();

		assertEquals(expectedCost, serviceRequest.getTotalCost());

		//TODO : this test its failing
	}

	@Test
	void equalsAndHashCode() {
		PostCode postCode = new PostCode("", "", "", 0, 0, "");
		PostalAddress postalAddress = new PostalAddress("", postCode);
		PostalAddress anotherPostalAddress = new PostalAddress("a", postCode);
		Client client = new Client("", "", "", "", postalAddress);
		Client anotherClient = new Client("a", "a", "a", "a", postalAddress);

		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest1 = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest2 = new ServiceRequest(null, postalAddress);
		ServiceRequest serviceRequest3 = new ServiceRequest(client, null);
		ServiceRequest serviceRequest4 = new ServiceRequest(anotherClient, postalAddress);
		ServiceRequest serviceRequest5 = new ServiceRequest(client, anotherPostalAddress);
		ServiceRequest serviceRequest6 = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest7 = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest8 = new ServiceRequest(client, postalAddress);
		Object object = new Object();

		ServiceDescription serviceDescription = new ServiceDescription("", new FixedService("", "", "", 0, new Category("", "")), 1);
		serviceRequest.getServiceDescriptionList().addServiceDescription(serviceDescription);
		serviceRequest1.getServiceDescriptionList().addServiceDescription(serviceDescription);
		serviceRequest7.getServiceDescriptionList().addServiceDescription(serviceDescription);
		serviceRequest8.getServiceDescriptionList().addServiceDescription(serviceDescription);

		Schedule schedule = new Schedule(2000, 10, 10, 10, 10);
		serviceRequest.getScheduleList().addSchedulePreference(schedule);
		serviceRequest1.getScheduleList().addSchedulePreference(schedule);
		serviceRequest8.getScheduleList().addSchedulePreference(schedule);

		serviceRequest8.setCost(200);

		assertNotEquals(null, serviceRequest);
		assertEquals(serviceRequest, serviceRequest);
		assertEquals(serviceRequest, serviceRequest1);
		assertNotEquals(serviceRequest, serviceRequest2);
		assertNotEquals(serviceRequest, serviceRequest3);
		assertNotEquals(serviceRequest, serviceRequest4);
		assertNotEquals(serviceRequest, serviceRequest5);
		assertNotEquals(serviceRequest, serviceRequest6);
		assertNotEquals(serviceRequest, serviceRequest7);
		assertNotEquals(serviceRequest, serviceRequest8);
		assertNotEquals(serviceRequest, object);
	}
}