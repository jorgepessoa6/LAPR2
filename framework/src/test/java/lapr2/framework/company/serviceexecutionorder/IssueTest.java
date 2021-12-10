package lapr2.framework.company.serviceexecutionorder;

import lapr2.framework.company.serviceexecutionorder.issues.Issue;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IssueTest {

    @Test
    void isValid() {
        Issue issueValid = new Issue("abc", "abc");
        Issue issueInvalid1 = new Issue("", "abc");
        Issue issueInvalid2 = new Issue("abc", "");

        assertTrue(issueValid.isValid());
        assertFalse(issueInvalid1.isValid());
        assertFalse(issueInvalid2.isValid());
    }

    @Test
    void equalsAndHashCode() {
        Issue issue = new Issue("abc", "abc");
        Issue issue1 = new Issue("abc", "abc");
        Issue issue2 = new Issue("abcd", "abc");
        Issue issue3 = new Issue("abc", "abcd");

        Object object = new Object();

        assertNotEquals(issue, object);
        assertEquals(issue, issue1);
        assertNotEquals(issue, issue2);
        assertNotEquals(issue, issue3);

        assertTrue(issue.equals(issue1) && issue1.equals(issue));
        assertFalse(issue.equals(issue2) && issue2.equals(issue));
        assertFalse(issue.equals(issue3) && issue3.equals(issue));

        assertFalse(issue.hashCode() == object.hashCode());
        assertTrue(issue.hashCode() == issue1.hashCode());
        assertFalse(issue.hashCode() == issue2.hashCode());
        assertFalse(issue.hashCode() == issue3.hashCode());
    }
}