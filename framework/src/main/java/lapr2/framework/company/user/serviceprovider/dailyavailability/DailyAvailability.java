package lapr2.framework.company.user.serviceprovider.dailyavailability;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the daily availability of a {@link lapr2.framework.company.user.serviceprovider.ServiceProvider}.
 */
@AllArgsConstructor
public class DailyAvailability implements Serializable {

	private static final long serialVersionUID = -5145314340305339865L;

	/**
	 * The start time.
	 */
	@Getter
	private LocalDateTime startDate;

	/**
	 * The end time.
	 */
	@Getter
	private LocalDateTime endDate;

	/**
	 * Returns whether a daily availability is valid or not.
	 *
	 * @return <code>true</code> if the end time is bigger than the start time, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return startDate.compareTo(endDate) < 0;
	}

	/**
	 * Returns whether a daily availability is overlapping this one or not.
	 *
	 * @param dailyAvailability the daily availability
	 * @return <code>true</code> if the daily availability overlaps this one, <code>false</code> otherwise
	 */
	public boolean isOverlapping(DailyAvailability dailyAvailability) {
		return dailyAvailability.getEndDate().isAfter(this.getStartDate()) && dailyAvailability.getStartDate().isBefore(this.getEndDate());
	}

	/**
	 * Verifies if this availability is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equals, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof DailyAvailability)) return false;
		DailyAvailability that = (DailyAvailability) o;
		return Objects.equals(getStartDate(), that.getStartDate()) &&
				Objects.equals(getEndDate(), that.getEndDate());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getStartDate(), getEndDate());
	}
}
