package lapr2.framework.company.servicetype;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.service.ExpandableService;
import lapr2.framework.company.service.FixedService;
import lapr2.framework.company.service.LimitedService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTypeTest {

	@Test
	void createService() {
		ServiceTypeManager serviceTypeManager = new ServiceTypeManager();

		ServiceType fixed = serviceTypeManager.get(ServiceType.Type.FIXED);
		ServiceType limited = serviceTypeManager.get(ServiceType.Type.LIMITED);
		ServiceType expandable = serviceTypeManager.get(ServiceType.Type.EXPANDABLE);

		assertEquals(FixedService.class, fixed.createService("", "", "", 1f, null).getClass());
		assertEquals(LimitedService.class, limited.createService("", "", "", 1f, null).getClass());
		assertEquals(ExpandableService.class, expandable.createService("", "", "", 1f, null).getClass());
	}

	@Test
	void hasAdditionalData() {
		ServiceTypeManager serviceTypeManager = new ServiceTypeManager();

		ServiceType fixed = serviceTypeManager.get(ServiceType.Type.FIXED);
		ServiceType expandable = serviceTypeManager.get(ServiceType.Type.EXPANDABLE);

		assertTrue(fixed.hasAdditionalData());
		assertFalse(expandable.hasAdditionalData());
	}

	@Test
	void equals1() {
		ServiceType serviceType = new ServiceType(ServiceType.Type.FIXED, "a");
		Category category = new Category("", "");
		ServiceType serviceType1 = new ServiceType(ServiceType.Type.LIMITED, "a");
		ServiceType serviceType2 = new ServiceType(ServiceType.Type.FIXED, "b");
		ServiceType serviceType3 = new ServiceType(ServiceType.Type.FIXED, "a");

		assertEquals(serviceType, serviceType);
		assertNotEquals(serviceType, category);
		assertNotEquals(serviceType, serviceType1);
		assertNotEquals(serviceType, serviceType2);
		assertEquals(serviceType, serviceType3);
	}

	@Test
	void hashCode1() {
		ServiceType serviceType = new ServiceType(ServiceType.Type.FIXED, "a");
		ServiceType serviceType1 = new ServiceType(ServiceType.Type.LIMITED, "a");
		ServiceType serviceType2 = new ServiceType(ServiceType.Type.FIXED, "b");
		ServiceType serviceType3 = new ServiceType(ServiceType.Type.FIXED, "a");

		assertEquals(serviceType.hashCode(), serviceType.hashCode());
		assertNotEquals(serviceType.hashCode(), serviceType1.hashCode());
		assertNotEquals(serviceType.hashCode(), serviceType2.hashCode());
		assertEquals(serviceType.hashCode(), serviceType3.hashCode());
	}
}