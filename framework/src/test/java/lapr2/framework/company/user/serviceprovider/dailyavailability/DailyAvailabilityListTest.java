package lapr2.framework.company.user.serviceprovider.dailyavailability;

import lapr2.framework.company.servicerequest.Schedule;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DailyAvailabilityListTest {

	@Test
	void isSecure() {
		DailyAvailabilityList dailyAvailabilityList = new DailyAvailabilityList();

		LocalDateTime date1 = LocalDateTime.of(2019, 11, 22, 12, 5);
		LocalDateTime date2 = LocalDateTime.of(2019, 11, 22, 12, 6);

		DailyAvailability dailyAvailability1 = new DailyAvailability(date2, date1);
		DailyAvailability dailyAvailability2 = new DailyAvailability(date1, date2);

		assertFalse(dailyAvailabilityList.isSecure(dailyAvailability1));
		assertTrue(dailyAvailabilityList.isSecure(dailyAvailability2));

		dailyAvailabilityList.getElements().add(dailyAvailability2);

		assertFalse(dailyAvailabilityList.isSecure(dailyAvailability2));
	}

	@Test
	void isAvailableAt() {
		DailyAvailabilityList list = new DailyAvailabilityList();

		Schedule schedule1 = new Schedule(2019, 12, 10, 10, 30);
		Schedule schedule2 = new Schedule(2020, 12, 10, 11, 0);
		Schedule schedule3 = new Schedule(2019, 12, 10, 10, 0);
		Schedule schedule4 = new Schedule(2020, 12, 10, 10, 0);
		Schedule schedule5 = new Schedule(2018, 12, 10, 10, 0);
		Schedule schedule6 = new Schedule(2028, 12, 10, 10, 0);

		float halfAnHour = 0.5f;
		float oneHour = 1;
		float twoHours = 2;

		DailyAvailability dailyAvailability = new DailyAvailability(LocalDateTime.of(2019, 12, 10, 10, 0), LocalDateTime.of(2019, 12, 10, 12, 0));
		DailyAvailability dailyAvailability1 = new DailyAvailability(LocalDateTime.of(2020, 12, 10, 10, 0), LocalDateTime.of(2020, 12, 10, 12, 0));

		list.getElements().add(dailyAvailability);
		list.getElements().add(dailyAvailability1);

		assertTrue(list.isAvailableAt(schedule1, halfAnHour));
		assertTrue(list.isAvailableAt(schedule2, oneHour));
		assertTrue(list.isAvailableAt(schedule3, oneHour));
		assertTrue(list.isAvailableAt(schedule4, twoHours));
		assertFalse(list.isAvailableAt(schedule5, twoHours));
		assertFalse(list.isAvailableAt(schedule6, halfAnHour));
	}

	@Test
	public void isOverlapping() {
		DailyAvailabilityList list = new DailyAvailabilityList();

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
		DailyAvailability dailyAvailability7 = new DailyAvailability(date3.minusMonths(1), date6.minusMonths(1));

		list.add(dailyAvailability1);
		list.add(dailyAvailability2);
		list.add(dailyAvailability4);
		list.add(dailyAvailability5);
		list.add(dailyAvailability6);

		assertTrue(list.isOverlapping(dailyAvailability3));
		assertFalse(list.isOverlapping(dailyAvailability7));
	}

	@Test
	public void add() {
		DailyAvailabilityList list = new DailyAvailabilityList();

		LocalDateTime date1 = LocalDateTime.of(2019, 11, 22, 6, 0);
		LocalDateTime date2 = LocalDateTime.of(2019, 11, 22, 7, 0);
		LocalDateTime date3 = LocalDateTime.of(2019, 11, 21, 6, 0);
		LocalDateTime date4 = LocalDateTime.of(2019, 11, 21, 8, 0);

		DailyAvailability dailyAvailability1 = new DailyAvailability(date1, date2);
		DailyAvailability dailyAvailability2 = new DailyAvailability(date3, date4);

		assertTrue(list.add(dailyAvailability1, TimePattern.DAILY, 2));
		assertFalse(list.add(dailyAvailability2, TimePattern.DAILY, 3));
	}
}