package lapr2.framework.company.servicerequest;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents a schedule preference that catalogues a local data time, constituted by a date and a time in hours and minutes.
 *
 * @author flow
 */
public class Schedule implements Serializable {

	private static final long serialVersionUID = -4196459563513408780L;

	/**
	 * The default format of the {@link LocalDateTime} formatter.
	 */
	public static final String TIME_FORMAT = "yyyy-MM-dd_HH-mm";

	/**
	 * The date and time of the schedule preference.
	 */
	@Getter
	private final LocalDateTime startTime;

	/**
	 * Constructs a new schedule preference
	 *
	 * @param year       the year of the schedule preference
	 * @param month      the month of the schedule preference
	 * @param dayOfMonth the day of the schedule preference
	 * @param hour       the hour of the schedule preference
	 * @param minute     the minute of the schedule preference
	 */
	public Schedule(int year, int month, int dayOfMonth, int hour, int minute) {
		this.startTime = LocalDateTime.of(year, month, dayOfMonth, hour, minute);
	}

	/**
	 * Verifies if this schedule is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Schedule)) return false;
		Schedule schedule = (Schedule) o;
		return getStartTime().equals(schedule.getStartTime());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getStartTime());
	}

	public String toTimeFormattedString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
		return formatter.format(this.startTime);

	}

	/**
	 * Returns a string that describes the schedule.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return formatter.format(this.startTime);
	}

}
