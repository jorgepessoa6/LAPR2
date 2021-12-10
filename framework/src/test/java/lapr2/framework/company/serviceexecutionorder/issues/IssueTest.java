package lapr2.framework.company.serviceexecutionorder.issues;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class IssueTest {

	@Test
	void equalsAndHashCode() {
		Issue issue = new Issue("iss", "toru");
		Issue issue1 = new Issue("", "toru");
		Issue issue2 = new Issue("iss", "");
		Issue issue3 = new Issue("iss", "toru");

		assertNotEquals(issue, new Object());
		assertEquals(issue, issue);
		assertNotEquals(issue, issue1);
		assertNotEquals(issue, issue2);
		assertEquals(issue, issue3);

		assertEquals(issue.hashCode(), issue.hashCode());
		assertNotEquals(issue.hashCode(), issue1.hashCode());
		assertNotEquals(issue.hashCode(), issue2.hashCode());
		assertEquals(issue.hashCode(), issue3.hashCode());
	}
}