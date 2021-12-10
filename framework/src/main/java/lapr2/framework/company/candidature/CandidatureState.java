package lapr2.framework.company.candidature;

import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents the currentState of a candidature to service provider, providing access to its
 * date and current state.
 *
 * @author flow
 */
public class CandidatureState implements Serializable {

	private static final long serialVersionUID = 3068114680336796103L;

	/**
	 * The default state.
	 */
	private static final State DEFAULT_STATE = State.SUBMITTED;

	/**
	 * The date the candidature state was set.
	 */
	@Getter
	private LocalDateTime date;

	/**
	 * The current state of the candidature.
	 */
	@Getter
	private State currentState;

	/**
	 * Creates a new candidature state, assigning the default state and the actual date.
	 */
	public CandidatureState() {
		setCurrentDate();
		this.currentState = DEFAULT_STATE;
	}

	/**
	 * Creates a new candidature state, the default state and a custom date.
	 *
	 * @param date custom date
	 */
	public CandidatureState(LocalDateTime date) {
		this.date = LocalDateTime.of(date.toLocalDate(), date.toLocalTime());
		this.currentState = DEFAULT_STATE;
	}

	/**
	 * Sets the current state, applying the date as the current one.
	 *
	 * @param state current state
	 */
	public void setCurrentState(State state) {
		this.currentState = state;
		setCurrentDate();
	}

	/**
	 * Sets the date of the state.
	 *
	 * @param date date
	 */
	public void setDate(LocalDateTime date) {
		this.date = LocalDateTime.of(date.toLocalDate(), date.toLocalTime());
	}

	/**
	 * Verifies if this state is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof CandidatureState)) return false;
		CandidatureState that = (CandidatureState) o;
		return getCurrentState() == that.getCurrentState();
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getCurrentState());
	}

	/**
	 * Sets the date as the current one.
	 */
	private void setCurrentDate() {
		this.date = LocalDateTime.now();
	}

	/**
	 * Represents the possible states.
	 */
	public enum State {
		SUBMITTED, ACCEPTED, REJECTED
	}

}
