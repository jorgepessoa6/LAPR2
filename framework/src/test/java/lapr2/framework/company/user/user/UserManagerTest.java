package lapr2.framework.company.user.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    @Test
    void createUser() {
        UserManager userManager = new UserManager("");

        User expectedUser = new User("tests@lapr2", "");

		assertEquals(expectedUser, userManager.createUser("tests@lapr2", ""));

		userManager.add(expectedUser);
        assertEquals(expectedUser, userManager.createUser("tests@lapr2", ""));
    }

    @Test
    void addUser() {
        UserManager userManager = new UserManager("");

        User user = userManager.createUser("tests@lapr2", "");

        assertTrue(userManager.add(user));
        assertFalse(userManager.add(null));
    }

    @Test
    void getUser() {
        UserManager userManager = new UserManager("");

        User user = userManager.createUser("tests1@lapr2", "");
        User user2 = userManager.createUser("tests2@lapr2", "");

        userManager.add(user);

        assertEquals(user, userManager.getUser(user.getEmail()));
        assertNull(userManager.getUser(user2.getEmail()));
    }

	@Test
	void isSecure() {
		UserManager manager = new UserManager("");

		User user = new User("a", "a");
		User user1 = new User("a", "b");
		User user2 = new User("", "");

		user.addRole(User.Role.CLIENT);

		assertFalse(manager.isSecure(null));
		assertTrue(manager.isSecure(user));

		manager.add(user);

		assertFalse(manager.isSecure(user1));
		assertFalse(manager.isSecure(user2));
	}

	@Test
	void isValid() {
		UserManager manager = new UserManager("");

		User user = new User("a", "a");
		User user1 = new User("a", "b");
		User user2 = new User("b", "b");

		assertTrue(manager.isValid(user));

		manager.add(user);

		assertFalse(manager.isValid(user1));
		assertTrue(manager.isValid(user2));
	}
}