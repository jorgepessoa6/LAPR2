package lapr2.framework.company.servicerequest;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.service.LimitedService;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ServiceDescriptionListTest {

    @Test
    void createServiceDescription() {
        String validDescription = "description";
        int validDuration = 60;
        Category category = new Category("catId", "catDescription");
        LimitedService validService = new LimitedService("id", "briefDescription", "completeDescription", 20, category);

        ServiceDescription expected = new ServiceDescription(validDescription, validService, validDuration);

        ServiceDescriptionList serviceDescriptionList = new ServiceDescriptionList();
        ServiceDescription result = serviceDescriptionList.createServiceDescription(validDescription, validService, validDuration);

        assertEquals(expected, result);
    }

    @Test
    void addServiceDescription() {
        String validDescription = "description";
        int validDuration = 60;
        Category category = new Category("catId", "catDescription");
        LimitedService validService = new LimitedService("id", "briefDescription", "completeDescription", 20, category);

        ServiceDescription serviceDescription = new ServiceDescription(validDescription, validService, validDuration);

        ServiceDescriptionList serviceDescriptionList = new ServiceDescriptionList();
        serviceDescriptionList.addServiceDescription(serviceDescription);

        ArrayList<ServiceDescription> serviceDescriptionArrayList = new ArrayList<>();
        serviceDescriptionArrayList.add(serviceDescription);

        assertEquals(serviceDescriptionList.getServiceDescriptions(), serviceDescriptionArrayList);

    }

    @Test
    void isSecure() {
        String validDescription = "description";
        String invalidDescription = "";
        int validDuration = 60;
        float invalidDuration = -10;
        Category category = new Category("catId", "catDescription");
        LimitedService validService = new LimitedService("id", "briefDescription", "completeDescription", 20, category);
        LimitedService invalidService = new LimitedService("", "briefDescription", "completeDescription", 20, category);

        ServiceDescription serviceDescription = new ServiceDescription(validDescription, validService, validDuration);
        ServiceDescription serviceDescriptionRepeated = new ServiceDescription(validDescription, validService, validDuration);
        ServiceDescription invalidServiceDescription1 = new ServiceDescription(invalidDescription, validService, validDuration);
        ServiceDescription invalidServiceDescription2 = new ServiceDescription(validDescription, invalidService, validDuration);
        ServiceDescription invalidServiceDescription3 = new ServiceDescription(validDescription, validService, invalidDuration);

        ServiceDescriptionList serviceDescriptionList = new ServiceDescriptionList();

        assertTrue(serviceDescriptionList.isSecure(serviceDescription));
        serviceDescriptionList.addServiceDescription(serviceDescription);

        assertFalse(serviceDescriptionList.isSecure(serviceDescriptionRepeated));
        assertFalse(serviceDescriptionList.isSecure(invalidServiceDescription1));
        assertFalse(serviceDescriptionList.isSecure(invalidServiceDescription2));
        assertFalse(serviceDescriptionList.isSecure(invalidServiceDescription3));
    }

    @Test
    void isValid() {
        String validDescription = "description";
        int validDuration = 60;
        Category category = new Category("catId", "catDescription");
        LimitedService validService = new LimitedService("id", "briefDescription", "completeDescription", 20, category);

        ServiceDescription serviceDescription = new ServiceDescription(validDescription, validService, validDuration);

        ServiceDescriptionList serviceDescriptionList = new ServiceDescriptionList();

        assertTrue(serviceDescriptionList.isValid(serviceDescription));

        serviceDescriptionList.addServiceDescription(serviceDescription);

        assertFalse(serviceDescriptionList.isValid(serviceDescription));
    }

    @Test
    void equalsAndHashCode() {
		String validDescription = "description";
		int validDuration = 60;
		Category category = new Category("catId", "catDescription");
		LimitedService validService = new LimitedService("id", "briefDescription", "completeDescription", 20, category);

		ServiceDescription serviceDescription = new ServiceDescription(validDescription, validService, validDuration);


		ServiceDescriptionList list = new ServiceDescriptionList();
		list.addServiceDescription(serviceDescription);

		ServiceDescriptionList list1 = new ServiceDescriptionList();

		ServiceDescriptionList list2 = new ServiceDescriptionList();
		list2.addServiceDescription(serviceDescription);


		assertNotEquals(list, new Object());
		assertEquals(list, list);
		assertNotEquals(list, list1);
		assertEquals(list, list2);

		assertEquals(list.hashCode(), list.hashCode());
		assertNotEquals(list.hashCode(), list1.hashCode());
		assertEquals(list.hashCode(), list2.hashCode());
    }
}