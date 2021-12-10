package lapr2.framework.company.user.serviceprovider.dailyavailability;

import lapr2.framework.company.servicerequest.Schedule;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores the daily availabilities of the {@link lapr2.framework.company.user.serviceprovider.ServiceProvider}.
 *
 * @author flow
 */
@EqualsAndHashCode //TODO: remove lombok tag and make tests
public class DailyAvailabilityList implements Serializable {

	private static final long serialVersionUID = 3166404358074933685L;

	/**
	 * The collection of daily availabilities.
	 */
	@Getter
	private List<DailyAvailability> elements;

	/**
	 * Constructs a new daily availability list.
	 */
	public DailyAvailabilityList() {
		this.elements = new ArrayList<>();
	}

	/**
	 * Creates a daily availability.
	 *
	 * @param startTime the start time
	 * @param endTime   the end time
	 * @return the daily availability
	 */
	public DailyAvailability createDailyAvailability(LocalDateTime startTime, LocalDateTime endTime) {
		return new DailyAvailability(startTime, endTime);
	}

	/**
	 * Verifies if a daily availability is valid.
	 *
	 * @param dailyAvailability the object
	 * @return <code>true</code> if the daily availability is valid, <code>false</code> otherwise
	 */
	public boolean isSecure(DailyAvailability dailyAvailability) {
		return dailyAvailability.isValid() && !isOverlapping(dailyAvailability) && isValid(dailyAvailability);
	}

	/**
	 * Returns whether a daily availability is overlapping the others or not.
	 *
	 * @param dailyAvailability the daily availability
	 * @return <code>true</code> if the daily availability overlaps the others, <code>false</code> otherwise
	 */
	public boolean isOverlapping(DailyAvailability dailyAvailability) {
		for (DailyAvailability availability : elements) {
			if (dailyAvailability.isOverlapping(availability)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Adds a daily availability to the list.
	 *
	 * @param dailyAvailability the daily availability
	 * @return <code>true</code> if the the daily availability was successfully added, <code>false</code> otherwise
	 */
	public boolean add(DailyAvailability dailyAvailability) {
		return this.elements.add(dailyAvailability);
	}

	/**
	 * Adds a daily availability to the list.
	 *
	 * @param dailyAvailability the daily availability
	 * @param timePattern       the time pattern
	 * @param repetitions       the number of repetitions
	 * @return <code>true</code> if the all the daily availabilities were successfully added, <code>false</code> otherwise
	 */
	public boolean add(DailyAvailability dailyAvailability, TimePattern timePattern, int repetitions) {
		boolean success = true;

		if (timePattern == null || repetitions == 0) {
			success = add(dailyAvailability);
		} else {
			for (int i = 0; i < repetitions; i++) {
				DailyAvailability copiedDailyAvailability = copyDailyAvailability(dailyAvailability, timePattern, i);

				if (isSecure(copiedDailyAvailability)) {
					boolean current = add(copiedDailyAvailability);

					if (success) {
						success = current;
					}
				} else {
					success = false;
				}
			}
		}


		return success;
	}

	/**
	 * Copies a daily availability based on a time pattern and the repetition number.
	 *
	 * @param dailyAvailability the daily availability
	 * @param timePattern       the time pattern
	 * @param i                 the number of repetitions
	 * @return the copied daily availability
	 */
	private DailyAvailability copyDailyAvailability(DailyAvailability dailyAvailability, TimePattern timePattern, int i) {
		Long j = (long) i;
		DailyAvailability copiedDailyAvailability;
		switch (timePattern) {
			case DAILY:
				copiedDailyAvailability = createDailyAvailability(dailyAvailability.getStartDate().plusDays(j),
						dailyAvailability.getEndDate().plusDays(j));
				break;
			case WEEKLY:
				copiedDailyAvailability = createDailyAvailability(dailyAvailability.getStartDate().plusWeeks(j),
						dailyAvailability.getEndDate().plusWeeks(j));
				break;
			case MONTHLY:
				copiedDailyAvailability = createDailyAvailability(dailyAvailability.getStartDate().plusMonths(j),
						dailyAvailability.getEndDate().plusMonths(j));
				break;
			case QUARTERLY:
				copiedDailyAvailability = createDailyAvailability(dailyAvailability.getStartDate().plusMonths(j * 3),
						dailyAvailability.getEndDate().plusMonths(j * 3));
				break;
			case SEMIANNUALLY:
				copiedDailyAvailability = createDailyAvailability(dailyAvailability.getStartDate().plusMonths(j * 6),
						dailyAvailability.getEndDate().plusMonths(j * 6));
				break;
			case ANNUALLY:
				copiedDailyAvailability = createDailyAvailability(dailyAvailability.getStartDate().plusYears(j),
						dailyAvailability.getEndDate().plusYears(j));
				break;
			default:
				throw new IllegalStateException("Unexpected value: " + timePattern);
		}
		return copiedDailyAvailability;
	}

	/**
	 * Verifies if there is availability at a given schedule and duration.
	 *
	 * @param schedule the schedule
	 * @param duration the duration
	 * @return <code>true</code> if there is availability, <code>false</code> otherwise
	 */
	public boolean isAvailableAt(Schedule schedule, float duration) {
		LocalDateTime start = schedule.getStartTime();
		LocalDateTime finish = start.plusMinutes((long) (duration * 60));

		LocalDateTime availabilityStart;
		LocalDateTime availabilityFinish;

		for (DailyAvailability dailyAvailability : elements) {
			availabilityStart = dailyAvailability.getStartDate();
			availabilityFinish = dailyAvailability.getEndDate();

			if ((start.isAfter(availabilityStart) || start.isEqual(availabilityStart)) &&
					(finish.isBefore(availabilityFinish) || finish.isEqual(availabilityFinish))) {
				return true;
			}
		}

		return false;

	}

	/**
	 * Verifies if a daily availability is valid globally.
	 *
	 * @param dailyAvailability the object
	 * @return <code>true</code> if the daily availability is valid, <code>false</code> otherwise
	 */
	private boolean isValid(DailyAvailability dailyAvailability) {
		return !elements.contains(dailyAvailability);
	}
}
