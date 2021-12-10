package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LimitedServiceTest {

	@Test
	void getCost() {
		LimitedService limitedService = new LimitedService("", "", "", 20d, null);

		double duration = 3.5d;

		double expected = 20d * duration;

		assertEquals(expected, limitedService.getCost(duration));
	}

	@Test
	void isValid() {
		LimitedService limitedService1 = new LimitedService("", "", "", -1d, null);
		LimitedService limitedService2 = new LimitedService("1", "", "", -1d, null);
		LimitedService limitedService3 = new LimitedService("1", "test", "", -1d, null);
		LimitedService limitedService4 = new LimitedService("1", "test", "test", -1d, null);
		LimitedService limitedService5 = new LimitedService("1", "test", "test", 1d, null);
		LimitedService limitedService6 = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));

		assertFalse(limitedService1.isValid());
		assertFalse(limitedService2.isValid());
		assertFalse(limitedService3.isValid());
		assertFalse(limitedService4.isValid());
		assertFalse(limitedService5.isValid());
		assertTrue(limitedService6.isValid());
	}

	@Test
	void hasAdditionalAttributes() {
		LimitedService limitedService1 = new LimitedService("", "", "", -1d, null);

		assertFalse(limitedService1.hasAdditionalAttributes());
	}

	@Test
	void equals1() {
		LimitedService limitedService = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));
		ExpandableService expandableService = new ExpandableService("1", "test", "test", 1d, new Category("1", "test"));
		LimitedService limitedService1 = new LimitedService("2", "test", "test", 1d, new Category("1", "test"));
		LimitedService limitedService2 = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));

		assertEquals(limitedService, limitedService);
		assertNotEquals(null, limitedService);
		assertNotEquals(limitedService, expandableService);
		assertNotEquals(limitedService, limitedService1);
		assertEquals(limitedService, limitedService2);
	}

	@Test
	void hashCode1() {
		LimitedService limitedService = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));
		LimitedService limitedService1 = new LimitedService("2", "test", "test", 1d, new Category("1", "test"));
		LimitedService limitedService2 = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));

		assertEquals(limitedService.hashCode(), limitedService.hashCode());
		assertNotEquals(limitedService.hashCode(), limitedService1.hashCode());
		assertEquals(limitedService.hashCode(), limitedService2.hashCode());
	}

}