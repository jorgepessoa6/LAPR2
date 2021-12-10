package lapr2.framework.company.serviceexecutionorder;

import lapr2.framework.company.affectation.Affectation;
import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.Service;
import lapr2.framework.company.serviceexecutionorder.issues.Issue;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.servicerequest.ServiceDescription;
import lapr2.framework.company.servicerequest.ServiceRequest;
import lapr2.framework.company.user.client.Client;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ServiceExecutionOrderTest {

    @Test
    void createIssue() {
        String issueDescription = "issueDescription";
        String troubleshootingDescription = "troubleshootingDescription";

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

        Issue expected = new Issue(issueDescription, troubleshootingDescription);
        Issue result = serviceExecutionOrder.createIssue(issueDescription, troubleshootingDescription);

        serviceExecutionOrder.setIssue(result);
        assertEquals(expected, result);
    }

	@Test
	void equals1() {
		String issueDescription = "issueDescription";
		String troubleshootingDescription = "troubleshootingDescription";

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
		Affectation affectation1 = new Affectation(serviceRequest, serviceDescription, serviceProvider, new Schedule(2000, 10, 10, 10, 10));

		ServiceExecutionOrder serviceExecutionOrder = new ServiceExecutionOrder(affectation);
		ServiceExecutionOrder serviceExecutionOrder1 = new ServiceExecutionOrder(affectation1);
		ServiceExecutionOrder serviceExecutionOrder2 = new ServiceExecutionOrder(affectation);
		ServiceExecutionOrder serviceExecutionOrder3 = new ServiceExecutionOrder(affectation);
		ServiceExecutionOrder serviceExecutionOrder4 = new ServiceExecutionOrder(affectation);
		ServiceExecutionOrder serviceExecutionOrder5 = new ServiceExecutionOrder(affectation);

		serviceExecutionOrder.setState(ServiceExecutionOrder.State.EVALUATED);
		serviceExecutionOrder2.setState(ServiceExecutionOrder.State.TO_DO);
		serviceExecutionOrder3.setState(ServiceExecutionOrder.State.EVALUATED);
		serviceExecutionOrder4.setState(ServiceExecutionOrder.State.EVALUATED);
		serviceExecutionOrder5.setState(ServiceExecutionOrder.State.EVALUATED);

		serviceExecutionOrder.setRateStatus(ServiceExecutionOrder.RateStatus.RATED);
		serviceExecutionOrder3.setRateStatus(ServiceExecutionOrder.RateStatus.TO_DO);
		serviceExecutionOrder4.setRateStatus(ServiceExecutionOrder.RateStatus.RATED);
		serviceExecutionOrder5.setRateStatus(ServiceExecutionOrder.RateStatus.RATED);

		serviceExecutionOrder.setIssue(serviceExecutionOrder.createIssue(issueDescription, troubleshootingDescription));
		serviceExecutionOrder4.setIssue(serviceExecutionOrder4.createIssue("no", "no"));
		serviceExecutionOrder5.setIssue(serviceExecutionOrder5.createIssue(issueDescription, troubleshootingDescription));

		assertEquals(serviceExecutionOrder, serviceExecutionOrder);
		assertNotEquals(serviceExecutionOrder, affectation);
		assertNotEquals(serviceExecutionOrder, serviceExecutionOrder1);
		assertNotEquals(serviceExecutionOrder, serviceExecutionOrder2);
		assertNotEquals(serviceExecutionOrder, serviceExecutionOrder3);
		assertNotEquals(serviceExecutionOrder, serviceExecutionOrder4);
		assertEquals(serviceExecutionOrder, serviceExecutionOrder5);
	}
}