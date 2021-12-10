package lapr2.framework.company.servicerequest;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleListTest {

	@Test
	void createSchedulePreference() {
		int year = 2019;
		int month = 7;
		int dayOfMonth = 6;
		int hour = 20;
		int minute = 30;

		Schedule expected = new Schedule(year, month, dayOfMonth, hour, minute);

		ScheduleList schedulePreferenceList = new ScheduleList();
		Schedule result = schedulePreferenceList.createSchedulePreference(year, month, dayOfMonth, hour, minute);

		assertEquals(expected, result);
	}

	@Test
	void addSchedulePreference() {
		int year = 2019;
		int month = 7;
		int dayOfMonth = 6;
		int hour = 20;
		int minute = 30;

		Schedule schedule1 = new Schedule(year, month, dayOfMonth, hour, minute);
		ScheduleList schedulePreferenceList = new ScheduleList();

		schedulePreferenceList.addSchedulePreference(schedule1);

		assertTrue(schedulePreferenceList.getSchedulePreferencesList().contains(schedule1));
	}

	@Test
	void isSecure() {
		int year = 2019;
		int month = 7;
		int dayOfMonth = 6;
		int hour = 20;
		int minute = 30;

		Schedule schedule1 = new Schedule(year, month, dayOfMonth, hour, minute);
		ScheduleList schedulePreferenceList = new ScheduleList();

		assertTrue(schedulePreferenceList.isSecure(schedule1));

		schedulePreferenceList.addSchedulePreference(schedule1);

		assertFalse(schedulePreferenceList.isSecure(schedule1));
	}

	@Test
	void isValid() {
		int year = 2019;
		int month = 7;
		int dayOfMonth = 6;
		int hour = 20;
		int minute = 30;

		Schedule schedule1 = new Schedule(year, month, dayOfMonth, hour, minute);
		ScheduleList schedulePreferenceList = new ScheduleList();

		assertTrue(schedulePreferenceList.isValid(schedule1));

		schedulePreferenceList.addSchedulePreference(schedule1);

		assertFalse(schedulePreferenceList.isValid(schedule1));
	}

	@Test
	void getSchedulePreferencesList() {
		int year = 2019;
		int month = 7;
		int dayOfMonth = 6;
		int hour = 20;
		int minute = 30;

		Schedule schedule1 = new Schedule(year, month, dayOfMonth, hour, minute);

		ArrayList<Schedule> expected = new ArrayList<>();
		expected.add(schedule1);

		ScheduleList result = new ScheduleList();
		result.addSchedulePreference(schedule1);

		assertEquals(expected, result.getSchedulePreferencesList());
	}

	@Test
	void equalsAndHashCode() {
		ScheduleList list = new ScheduleList();
		ScheduleList list1 = new ScheduleList();
		ScheduleList list2 = new ScheduleList();
		int year = 2019;
		int month = 7;
		int dayOfMonth = 6;
		int hour = 20;
		int minute = 30;

		Schedule schedule1 = new Schedule(year, month, dayOfMonth, hour, minute);
		list.addSchedulePreference(schedule1);
		list2.addSchedulePreference(schedule1);

		assertNotEquals(list, new Object());
		assertEquals(list, list);
		assertNotEquals(list, list1);
		assertEquals(list, list2);

		assertEquals(list.hashCode(), list.hashCode());
		assertNotEquals(list.hashCode(), list1.hashCode());
		assertEquals(list.hashCode(), list2.hashCode());

	}

}