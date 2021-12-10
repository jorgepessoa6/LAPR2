package lapr2.framework.company.user.serviceprovider.dailyavailability;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DailyAvailabilityTest {

	@Test
	void isValid() {
		LocalDateTime date1 = LocalDateTime.of(2019, 11, 22, 12, 5);
		LocalDateTime date2 = LocalDateTime.of(2019, 11, 22, 12, 6);

		DailyAvailability dailyAvailability1 = new DailyAvailability(date2, date1);
		DailyAvailability dailyAvailability2 = new DailyAvailability(date1, date2);

		assertFalse(dailyAvailability1.isValid());
		assertTrue(dailyAvailability2.isValid());
	}

	@Test
	void isOverlapping() {
		LocalDateTime date1 = LocalDateTime.of(2019, 11, 22, 6, 0);
		LocalDateTime date2 = LocalDateTime.of(2019, 11, 22, 7, 0);
		LocalDateTime date3 = LocalDateTime.of(2019, 11, 22, 8, 0);
		LocalDateTime date4 = LocalDateTime.of(2019, 11, 22, 9, 0);
		LocalDateTime date5 = LocalDateTime.of(2019, 11, 22, 10, 0);
		LocalDateTime date6 = LocalDateTime.of(2019, 11, 22, 11, 0);
		LocalDateTime date7 = LocalDateTime.of(2019, 11, 22, 12, 0);
		LocalDateTime date8 = LocalDateTime.of(2019, 11, 22, 13, 0);

		DailyAvailability dailyAvailability1 = new DailyAvailability(date1, date2);
		DailyAvailability dailyAvailability2 = new DailyAvailability(date2, date4);
		DailyAvailability dailyAvailability3 = new DailyAvailability(date3, date6);
		DailyAvailability dailyAvailability4 = new DailyAvailability(date5, date7);
		DailyAvailability dailyAvailability5 = new DailyAvailability(date7, date8);
		DailyAvailability dailyAvailability6 = new DailyAvailability(date4, date5);

		assertFalse(dailyAvailability1.isOverlapping(dailyAvailability3));
		assertTrue(dailyAvailability2.isOverlapping(dailyAvailability3));
		assertTrue(dailyAvailability4.isOverlapping(dailyAvailability3));
		assertFalse(dailyAvailability5.isOverlapping(dailyAvailability3));
		assertTrue(dailyAvailability6.isOverlapping(dailyAvailability3));
	}

	@Test
	void equalsAndHashCode() {
		LocalDateTime date1 = LocalDateTime.of(2019, 11, 22, 6, 0);
		LocalDateTime date2 = LocalDateTime.of(2019, 11, 22, 7, 0);
		LocalDateTime date3 = LocalDateTime.of(2019, 11, 22, 8, 0);
		LocalDateTime date4 = LocalDateTime.of(2019, 11, 22, 9, 0);
		LocalDateTime date5 = LocalDateTime.of(2019, 11, 22, 10, 0);
		LocalDateTime date6 = LocalDateTime.of(2019, 11, 22, 11, 0);
		LocalDateTime date7 = LocalDateTime.of(2019, 11, 22, 12, 0);
		LocalDateTime date8 = LocalDateTime.of(2019, 11, 22, 13, 0);

		DailyAvailability dailyAvailability1 = new DailyAvailability(date1, date2);
		DailyAvailability dailyAvailability2 = new DailyAvailability(date3, date2);
		DailyAvailability dailyAvailability3 = new DailyAvailability(date1, date3);
		DailyAvailability dailyAvailability4 = new DailyAvailability(date1, date2);

		assertNotEquals(dailyAvailability1, new Object());
		assertEquals(dailyAvailability1, dailyAvailability1);
		assertNotEquals(dailyAvailability1, dailyAvailability2);
		assertNotEquals(dailyAvailability1, dailyAvailability3);
		assertEquals(dailyAvailability1, dailyAvailability4);

		assertEquals(dailyAvailability1.hashCode(), dailyAvailability1.hashCode());
		assertNotEquals(dailyAvailability1.hashCode(), dailyAvailability2.hashCode());
		assertNotEquals(dailyAvailability1.hashCode(), dailyAvailability3.hashCode());
		assertEquals(dailyAvailability1.hashCode(), dailyAvailability4.hashCode());
	}

}