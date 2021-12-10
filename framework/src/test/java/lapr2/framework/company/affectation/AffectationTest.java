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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AffectationTest {

	@Test
	void equalsAnsHashCode() {
		PostCode postCode = new PostCode("district", "municipal", "local", 0, 0, "postcode");
		PostalAddress postalAddress = new PostalAddress("address", postCode);
		PostalAddress postalAddress1 = new PostalAddress("address123", postCode);
		Client client = new Client("name", "tax", "phone", "email", postalAddress);
		ServiceRequest serviceRequest = new ServiceRequest(client, postalAddress);
		ServiceRequest serviceRequest1 = new ServiceRequest(client, postalAddress1);

		Category category = new Category("id", "description");
		Service service = new FixedService("id", "brief", "complete", 0, category);
		ServiceDescription serviceDescription = new ServiceDescription("description", service, 1);
		ServiceDescription serviceDescription1 = new ServiceDescription("description", service, 123);
        PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        ServiceProvider serviceProvider = new ServiceProvider("mec", "cname", "aname", "email", postCode1);
        ServiceProvider serviceProvider1 = new ServiceProvider("mec1", "cname", "aname", "email", postCode1);

		Schedule schedule = new Schedule(2019, 6, 10, 10, 0);
		Schedule schedule1 = new Schedule(2019, 6, 10, 9, 30);

		Affectation affectation = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		Affectation affectation1 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);
		Affectation affectation2 = new Affectation(serviceRequest1, serviceDescription, serviceProvider, schedule);
		Affectation affectation3 = new Affectation(serviceRequest, serviceDescription1, serviceProvider, schedule);
		Affectation affectation4 = new Affectation(serviceRequest, serviceDescription, serviceProvider1, schedule);
		Affectation affectation5 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule1);
		Affectation affectation6 = new Affectation(serviceRequest, serviceDescription, serviceProvider, schedule);

		affectation.setState(Affectation.State.CONFIRMED);
		affectation1.setState(Affectation.State.CONFIRMED);

		assertEquals(affectation, affectation);
		assertNotEquals(affectation, new Object());
		assertEquals(affectation, affectation1);
		assertNotEquals(affectation, affectation2);
		assertNotEquals(affectation, affectation3);
		assertNotEquals(affectation, affectation4);
		assertNotEquals(affectation, affectation5);
		assertNotEquals(null, affectation);
		assertNotEquals(affectation, affectation6);

		assertEquals(affectation.hashCode(), affectation.hashCode());
		assertEquals(affectation.hashCode(), affectation1.hashCode());
		assertNotEquals(affectation.hashCode(), affectation2.hashCode());
		assertNotEquals(affectation.hashCode(), affectation3.hashCode());
		assertNotEquals(affectation.hashCode(), affectation4.hashCode());
		assertNotEquals(affectation.hashCode(), affectation5.hashCode());
		assertNotEquals(affectation.hashCode(), affectation6.hashCode());
	}
}