package lapr2.framework.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NumberUtilsTest {

	@Test
	void getRandomInt() {
		int min = 0, max = 10;
		int sameNumber = 5;

		int num = NumberUtils.getRandomInt(min, max);
		int num1 = NumberUtils.getRandomInt(max, min);
		int num2 = NumberUtils.getRandomInt(sameNumber, sameNumber);

		for (int i = 0; i < 50; i++) {
			assertTrue(num <= max && num >= min);
			assertTrue(num1 <= max && num1 >= min);
			assertEquals(sameNumber, num2);
		}

	}
}