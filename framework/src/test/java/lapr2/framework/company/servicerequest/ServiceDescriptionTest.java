package lapr2.framework.company.servicerequest;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.LimitedService;
import lapr2.framework.company.service.Service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceDescriptionTest {

    @Test
    void isValid() {
        String validDescription = "description";
        String invalidDescription = "";

		float validDuration = 1;
		float invalidDuration = -10;
		float invalidDuration1 = 0;
		float invalidDuration2 = 0.7f;

        Category category = new Category("catId", "catDescription");
        LimitedService validService = new LimitedService("id", "briefDescription", "completeDescription", 20, category);
        LimitedService invalidService = new LimitedService("", "briefDescription", "completeDescription", 20, category);

        ServiceDescription validServiceDescription = new ServiceDescription(validDescription, validService, validDuration);

        ServiceDescription invalidServiceDescription1 = new ServiceDescription(invalidDescription, validService, validDuration);
        ServiceDescription invalidServiceDescription2 = new ServiceDescription(validDescription, invalidService, validDuration);
        ServiceDescription invalidServiceDescription3 = new ServiceDescription(validDescription, validService, invalidDuration);
		ServiceDescription invalidServiceDescription4 = new ServiceDescription(validDescription, validService, invalidDuration1);
		ServiceDescription invalidServiceDescription5 = new ServiceDescription(validDescription, validService, invalidDuration2);

        assertTrue(validServiceDescription.isValid());

        assertFalse(invalidServiceDescription1.isValid());
        assertFalse(invalidServiceDescription2.isValid());
        assertFalse(invalidServiceDescription3.isValid());
		assertFalse(invalidServiceDescription4.isValid());
		assertFalse(invalidServiceDescription5.isValid());

    }

	@Test
	void equalsAndHashCode() {
		String description = "description";
		String description1 = "description1";
		Service service = new FixedService("id", "des", "desc", 0, new Category("id", "des"));
		Service service1 = new FixedService("id1", "des1", "desc1", 0, new Category("id", "des"));
		float duration = 1;
		float duration1 = 0.5f;

		ServiceDescription serviceDescription = new ServiceDescription(description, service, duration);
		ServiceDescription serviceDescription1 = new ServiceDescription(description, service, duration);
		ServiceDescription serviceDescription2 = new ServiceDescription(description1, service, duration);
		ServiceDescription serviceDescription3 = new ServiceDescription(description, service1, duration);

		assertEquals(serviceDescription, serviceDescription);
		assertNotEquals(null, serviceDescription);
		assertNotEquals(serviceDescription, new Object());
		assertEquals(serviceDescription, serviceDescription1);
		assertNotEquals(serviceDescription, serviceDescription2);
		assertNotEquals(serviceDescription, serviceDescription3);

		assertEquals(serviceDescription.hashCode(), serviceDescription1.hashCode());
		assertFalse(serviceDescription.hashCode() == serviceDescription2.hashCode());
		assertFalse(serviceDescription.hashCode() == serviceDescription3.hashCode());
	}
}