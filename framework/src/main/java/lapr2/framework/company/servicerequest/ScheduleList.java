package lapr2.framework.company.servicerequest;

import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a list of Schedule Preferences of a service request.
 *
 * @author flow
 */
public class ScheduleList implements Serializable {

	private static final long serialVersionUID = 4112488986160681182L;

	/**
	 * Collection of Schedule Preferences of a service request
	 */
	@Getter
	private ArrayList<Schedule> schedulePreferencesList = new ArrayList<>();

	/**
	 * Creates a new {@link Schedule} returning it.
	 *
	 * @param year       the year of the SchedulePreference
	 * @param month      the month of the SchedulePreference
	 * @param dayOfMonth the day of the SchedulePreference
	 * @param hour       the hour of the SchedulePreference
	 * @param minute     the minute of the SchedulePreference
	 * @return the created SchedulePreference
	 */
	public Schedule createSchedulePreference(int year, int month, int dayOfMonth, int hour, int minute) {
		return new Schedule(year, month, dayOfMonth, hour, minute);
	}

	/**
	 * Adds a {@link Schedule} to the colletion.
	 *
	 * @param schedule the schedule preference
	 * @return <code>true</code> if the schedule preference was successfully added, <code>false</code> otherwise
	 */
	public boolean addSchedulePreference(Schedule schedule) {
		return schedulePreferencesList.add(schedule);
	}

	/**
	 * Verifies if a {@link Schedule} is valid.
	 *
	 * @param schedule the schedule preference
	 * @return <code>true</code> if the schedule preference is valid, <code>false</code> otherwise
	 */
	public boolean isSecure(Schedule schedule) {
		return this.isValid(schedule);
	}

	/**
	 * Verifies if this list is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ScheduleList)) return false;
		ScheduleList that = (ScheduleList) o;
		return Objects.equals(getSchedulePreferencesList(), that.getSchedulePreferencesList());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getSchedulePreferencesList());
	}

	/**
	 * Verifies if a schedule preference is valid globally.
	 *
	 * @param schedule the schedule preference
	 * @return <code>true</code> if the schedule preference is valid globally, <code>false</code> otherwise
	 */
	protected boolean isValid(Schedule schedule) {
		return !schedulePreferencesList.contains(schedule);
	}
}
