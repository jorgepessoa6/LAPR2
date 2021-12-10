package lapr2.framework.company.serviceexecutionorder.issues;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a issue from a service execution order
 *
 * @author flow
 */
@AllArgsConstructor
public class Issue implements Serializable {

    private static final long serialVersionUID = -2722028978648286337L;

    /**
     * The description of the issue.
     */
    @Getter
    private final String issueDescription;

    /**
     * The troubleshooting  of the issue.
     */
    @Getter
    private final String troubleshootingDescription;

    /**
     * Verifies if an issue is valid locally.
     *
     * @return <code>true</code> if an issue is valid locally, <code>false</code> otherwise
     */
    public boolean isValid() {
        return !issueDescription.isEmpty() && !troubleshootingDescription.isEmpty();
    }

	/**
	 * Verifies if this issue is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Issue)) return false;
		Issue issue = (Issue) o;
		return Objects.equals(getIssueDescription(), issue.getIssueDescription()) &&
				Objects.equals(getTroubleshootingDescription(), issue.getTroubleshootingDescription());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getIssueDescription(), getTroubleshootingDescription());
	}
}
