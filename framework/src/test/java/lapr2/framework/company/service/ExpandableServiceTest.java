package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpandableServiceTest {

	@Test
	void getCost() {
		ExpandableService expandableService = new ExpandableService("", "", "", 20d, null);

		double duration = 3.5d;

		double expected = 20d * duration;

		assertEquals(expected, expandableService.getCost(duration));
	}

	@Test
	void isValid() {
		ExpandableService expandableService1 = new ExpandableService("", "", "", -1d, null);
		ExpandableService expandableService2 = new ExpandableService("1", "", "test", 1d, new Category("1", "test"));
		ExpandableService expandableService3 = new ExpandableService("1", "test", "", -1d, null);
		ExpandableService expandableService4 = new ExpandableService("1", "test", "test", -1d, null);
		ExpandableService expandableService5 = new ExpandableService("1", "test", "test", 1d, null);
		ExpandableService expandableService6 = new ExpandableService("1", "test", "test", 1d, new Category("1", "test"));

		assertFalse(expandableService1.isValid());
		assertFalse(expandableService2.isValid());
		assertFalse(expandableService3.isValid());
		assertFalse(expandableService4.isValid());
		assertFalse(expandableService5.isValid());
		assertTrue(expandableService6.isValid());
	}

	@Test
	void hasAdditionalAttributes() {
		ExpandableService expandableService1 = new ExpandableService("", "", "", -1d, null);

		assertFalse(expandableService1.hasAdditionalAttributes());
	}

	@Test
	void equals1() {
		ExpandableService expandableService = new ExpandableService("1", "test", "test", 1d, new Category("1", "test"));
		LimitedService limitedService = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));
		ExpandableService expandableService1 = new ExpandableService("2", "test", "test", 1d, new Category("1", "test"));
		ExpandableService expandableService2 = new ExpandableService("1", "test", "test", 1d, new Category("1", "test"));

		assertEquals(expandableService, expandableService);
		assertNotEquals(null, expandableService);
		assertNotEquals(expandableService, limitedService);
		assertNotEquals(expandableService, expandableService1);
		assertEquals(expandableService, expandableService2);
	}

	@Test
	void hashCode1() {
		ExpandableService expandableService = new ExpandableService("1", "test", "test", 1d, new Category("1", "test"));
		ExpandableService expandableService1 = new ExpandableService("2", "test", "test", 1d, new Category("1", "test"));
		ExpandableService expandableService2 = new ExpandableService("1", "test", "test", 1d, new Category("1", "test"));

		assertEquals(expandableService.hashCode(), expandableService.hashCode());
		assertNotEquals(expandableService.hashCode(), expandableService1.hashCode());
		assertEquals(expandableService.hashCode(), expandableService2.hashCode());

	}
}