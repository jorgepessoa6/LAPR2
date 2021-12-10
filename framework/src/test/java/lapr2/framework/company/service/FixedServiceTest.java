package lapr2.framework.company.service;

import lapr2.framework.company.category.Category;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FixedServiceTest {

	@Test
	void getCost() {
		FixedService fixedService = new FixedService("", "", "", 20d, null);

		double duration = 3.5d;

		double expected = 20d * duration;

		assertEquals(expected, fixedService.getCost(duration));
	}

	@Test
	void isValid() {
		FixedService fixedService = new FixedService("", "", "", -1d, null);
		FixedService fixedService1 = new FixedService("1", "", "", -1d, null);
		FixedService fixedService2 = new FixedService("1", "test", "", -1d, null);
		FixedService fixedService3 = new FixedService("1", "test", "test", -1d, null);
		FixedService fixedService4 = new FixedService("1", "test", "test", 1d, null);
		FixedService fixedService5 = new FixedService("1", "test", "test", 1d, new Category("1", "test"));
		FixedService fixedService6 = new FixedService("1", "test", "test", 1d, new Category("1", "test"));

		fixedService5.setPredefinedDuration(-1);
		fixedService6.setPredefinedDuration(1d);

		assertFalse(fixedService.isValid());
		assertFalse(fixedService1.isValid());
		assertFalse(fixedService2.isValid());
		assertFalse(fixedService3.isValid());
		assertFalse(fixedService4.isValid());
		assertFalse(fixedService5.isValid());
		assertTrue(fixedService6.isValid());
	}

	@Test
	void hasAdditionalAttributes() {
		FixedService fixedService1 = new FixedService("", "", "", -1d, null);

		assertTrue(fixedService1.hasAdditionalAttributes());
	}


	@Test
	void equals1() {
		FixedService fixedService = new FixedService("1", "test", "test", 1d, new Category("1", "test"));
		LimitedService limitedService = new LimitedService("1", "test", "test", 1d, new Category("1", "test"));
		FixedService fixedService1 = new FixedService("2", "test", "test", 1d, new Category("1", "test"));
		FixedService fixedService2 = new FixedService("1", "test", "test", 1d, new Category("1", "test"));

		assertEquals(fixedService, fixedService);
		assertNotEquals(null, fixedService);
		assertNotEquals(fixedService, limitedService);
		assertNotEquals(fixedService, fixedService1);
		assertEquals(fixedService, fixedService2);
	}
}