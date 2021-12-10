package lapr2.framework.company.servicerequest;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ScheduleTest {

	@Test
	void equals() {
		int year = 2000, anotherYear = 2001;
		int month = 10, anotherMonth = 11;
		int dayOfMonth = 20, anotherDay = 21;
		int hour = 20, anotherHour = 21;
		int minute = 20, anotherMinute = 21;

		Schedule schedule = new Schedule(year, month, dayOfMonth, hour, minute);
		Schedule schedule1 = new Schedule(year, month, dayOfMonth, hour, minute);
		Schedule schedule2 = new Schedule(anotherYear, month, dayOfMonth, hour, minute);
		Schedule schedule3 = new Schedule(year, anotherMonth, dayOfMonth, hour, minute);
		Schedule schedule4 = new Schedule(year, month, anotherDay, hour, minute);
		Schedule schedule5 = new Schedule(year, month, dayOfMonth, anotherHour, minute);
		Schedule schedule6 = new Schedule(year, month, dayOfMonth, hour, anotherMinute);

		assertEquals(schedule, schedule);
		assertNotEquals(null, schedule);
		assertNotEquals(schedule, new Object());
		assertEquals(schedule, schedule1);
		assertNotEquals(schedule, schedule2);
		assertNotEquals(schedule, schedule3);
		assertNotEquals(schedule, schedule4);
		assertNotEquals(schedule, schedule5);
		assertNotEquals(schedule, schedule6);
	}
}