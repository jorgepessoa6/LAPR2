package lapr2.framework.company.user.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void addRole() {
		User user = new User("tests@lapr2.pt", "");

        assertTrue(user.addRole(User.Role.ADMINISTRATOR));
        assertFalse(user.addRole(null));
    }

    @Test
    void hasRole() {
		User user = new User("tests@lapr2.pt", "");

        user.addRole(User.Role.ADMINISTRATOR);

        assertTrue(user.hasRole(User.Role.ADMINISTRATOR));
        assertFalse(user.hasRole(null));
    }

    @Test
    void equalsAndHashCode() {
		User user1 = new User("tests@lapr2.pt", "");
		User user2 = new User("tests@lapr2.com.pt", "");
		User user3 = new User("tests@lapr2.pt", "");

		assertEquals(user1, user1);
		assertNotEquals(user1, new Object());
		assertNotEquals(user1, user2);
		assertEquals(user1, user3);

		assertNotEquals(user1.hashCode(), user2.hashCode());
		assertEquals(user1.hashCode(), user1.hashCode());
		assertEquals(user1.hashCode(), user3.hashCode());
    }

	@Test
	void isValid() {
		User user1 = new User("", "");
		User user2 = new User("tests@lapr2.pt", "");
		User user3 = new User("tests@lapr2.pt", "test");

		assertFalse(user1.isValid());
		assertFalse(user2.isValid());
		assertFalse(user3.isValid());

		user3.addRole(User.Role.ADMINISTRATOR);

		assertTrue(user3.isValid());
	}
}