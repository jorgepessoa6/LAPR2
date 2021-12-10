package lapr2.framework.company.affectation;

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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AffectationManagerTest {

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
		Schedule schedule1 = new Schedule(2019, 6, 10, 9, 30);
		Schedule schedule2 = new Schedule(2019, 6, 10, 10, 30);
		Schedule schedule3 = new Schedule(2019, 6, 10, 8, 30);
		Schedule schedule4 = new Schedule(2019, 6, 10, 12, 0);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		Affectation affectation1 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule1);
		Affectation affectation2 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule2);
		Affectation affectation3 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule3);
		Affectation affectation4 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule4);
		Affectation affectation5 = new Affectation(serviceRequest, serviceDescription, new ServiceProvider("", "", "", "", postCode), schedule4);

		AffectationManager manager = new AffectationManager("");

		assertTrue(manager.isSecure(affectation));
		manager.add(affectation);

		assertFalse(manager.isSecure(null));
		assertFalse(manager.isSecure(affectation1));
		assertFalse(manager.isSecure(affectation2));
		assertTrue(manager.isSecure(affectation3));
		assertTrue(manager.isSecure(affectation4));
		manager.getElements().clear();
		manager.add(affectation5);
		assertFalse(manager.isSecure(affectation5));
	}

	@Test
	void affect() {
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
		Schedule schedule1 = new Schedule(2019, 6, 10, 9, 30);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		Affectation affectation1 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule1);

		AffectationManager manager = new AffectationManager("");

		assertTrue(manager.affect(serviceRequest, serviceDescription, serviceProvider, schedule));
		assertTrue(manager.getElements().contains(affectation));
		assertFalse(manager.getElements().contains(affectation1));
	}
}