package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceManagerTest {

	@Test
	void isSecure() {
		ServiceManager serviceManager = new ServiceManager("");
		LimitedService limitedService1 = new LimitedService("", "", "", 1, null);
		LimitedService limitedService2 = new LimitedService("2", "123", "1234", 1, new Category("123", "123"));

		assertFalse(serviceManager.isSecure(limitedService1));
		assertTrue(serviceManager.isSecure(limitedService2));

		serviceManager.add(limitedService2);

		assertFalse(serviceManager.isSecure(limitedService2));
	}

	@Test
	void isValid() {
		ServiceManager serviceManager = new ServiceManager("");
		LimitedService limitedService = new LimitedService("", "", "", 1, null);

		assertTrue(serviceManager.isValid(limitedService));

		serviceManager.add(limitedService);

		assertFalse(serviceManager.isValid(limitedService));
	}

	@Test
	void getServicesOf() {
		ServiceManager serviceManager = new ServiceManager("");

		Category category = new Category("a", "a");
		LimitedService service = new LimitedService("lalaa", "brief", "", 1, category);
		Category category1 = new Category("b", "b");
		ExpandableService service1 = new ExpandableService("b", "b", "", 0, category1);

		serviceManager.add(service1);
		serviceManager.add(service);

		List<Service> expectedServices = new ArrayList<>();
		expectedServices.add(service);
		List<Service> expectedServices1 = new ArrayList<>();
		expectedServices1.add(service1);

		assertEquals(service, serviceManager.getServicesOf(category).toArray()[0]);
		assertNotEquals(service1, serviceManager.getServicesOf(category).toArray()[0]);
		assertEquals(service1, serviceManager.getServicesOf(category1).toArray()[0]);
	}

	@Test
	void getService() {
		ServiceManager serviceManager = new ServiceManager("");

		Category category = new Category("a", "a");
		String id = "a";
		Service service = new LimitedService(id, "", "", 1, category);
		Category category1 = new Category("b", "b");
		String id1 = "b";
		Service service1 = new ExpandableService(id1, "b", "", 0, category1);

		serviceManager.add(service1);
		serviceManager.add(service);

		assertEquals(service, serviceManager.getService(id));
		assertNotEquals(service1, serviceManager.getService(id));
		assertEquals(service1, serviceManager.getService(id1));
	}
}