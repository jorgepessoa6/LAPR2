package lapr2.framework.company.candidature;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CandidatureStateTest {

	@Test
	void equalsAndHashCode() {
		LocalDateTime localDateTime = LocalDateTime.of(2019, 6, 5, 20, 14);

		CandidatureState candidatureState = new CandidatureState(localDateTime);

		Object object = new Object();
		CandidatureState candidatureState1 = new CandidatureState(localDateTime);
		CandidatureState candidatureState2 = new CandidatureState(localDateTime);
		candidatureState2.setCurrentState(CandidatureState.State.ACCEPTED);

		assertNotEquals(candidatureState, object);
		assertEquals(candidatureState, candidatureState);
		assertEquals(candidatureState, candidatureState1);
		assertNotEquals(candidatureState, candidatureState2);

		assertEquals(candidatureState.hashCode(), candidatureState.hashCode());
		assertEquals(candidatureState.hashCode(), candidatureState1.hashCode());
		assertNotEquals(candidatureState.hashCode(), candidatureState2.hashCode());
	}

	@Test
	void setCurrentState() {
		LocalDateTime localDateTime = LocalDateTime.now();

		CandidatureState candidatureState = new CandidatureState(localDateTime);

		CandidatureState.State expected = CandidatureState.State.ACCEPTED;
		CandidatureState.State notExpected = CandidatureState.State.SUBMITTED;

		candidatureState.setCurrentState(expected);

		assertEquals(expected, candidatureState.getCurrentState());
		assertNotEquals(notExpected, candidatureState.getCurrentState());
	}
}