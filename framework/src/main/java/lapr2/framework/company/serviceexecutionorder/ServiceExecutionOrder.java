package lapr2.framework.company.serviceexecutionorder;


import lapr2.framework.company.affectation.Affectation;
import lapr2.framework.company.serviceexecutionorder.issues.Issue;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a service execution order, providing access to its affectation and state.
 *
 * @author flow
 */
public class ServiceExecutionOrder implements Serializable {

	private static final long serialVersionUID = -8631908635581621168L;

	/**
	 * The affectation of the service execution order.
	 */
	@Getter
	private final Affectation affectation;

	/**
	 * Represents the evaluation state of the service execution order by the provider.
	 */
	@Getter
	@Setter
	private State state;

	/**
	 * Represents the rate status of the service execution order.
	 */
	@Getter
	@Setter
	private RateStatus rateStatus;

	/**
	 * Represents the issue encountered during the effectuation of the service execution order.
	 */
	@Getter
	@Setter
	private Issue issue;

	/**
	 * Constructs a new service execution order with the state <code>WAITING</code>.
	 *
	 * @param affectation the related affectation
	 */
	public ServiceExecutionOrder(Affectation affectation) {
		this.affectation = affectation;
		this.state = State.TO_DO;
		this.rateStatus = RateStatus.TO_DO;
	}

	/**
	 * Creates a new {@link Issue} returning it.
	 *
	 * @param issueDescription           the issue description
	 * @param troubleshootingDescription the issue's troubleshooting description
	 * @return the created issue
	 */
	public Issue createIssue(String issueDescription, String troubleshootingDescription) {
		return new Issue(issueDescription, troubleshootingDescription);
	}

	/**
	 * Rates a service
	 *
	 * @param rating the rating
	 * @return <code>true</code> if the service was successfully rated, <code>false</code> otherwise
	 */
	public boolean rateServiceExecutionOrder(int rating) {
		this.rateStatus = RateStatus.RATED;
		return this.affectation.getServiceProvider().getRating().add(rating);
	}

	/**
	 * Verifies if this service execution order is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ServiceExecutionOrder)) return false;
		ServiceExecutionOrder that = (ServiceExecutionOrder) o;
		return getAffectation().equals(that.getAffectation()) &&
				getState() == that.getState() &&
				getRateStatus() == that.getRateStatus() &&
				Objects.equals(getIssue(), that.getIssue());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getAffectation(), getState(), getRateStatus(), getIssue());
	}

	@Override
	public String toString() {
		return affectation.toString();
	}

	/**
	 * Represents the evaluation by the provider state in which the service execution order can be.
	 */
	public enum State {
		TO_DO,
		EVALUATED
	}

	/**
	 * Represents the status of the rate done by the client.
	 */
	public enum RateStatus {
		TO_DO,
		RATED
	}
}
