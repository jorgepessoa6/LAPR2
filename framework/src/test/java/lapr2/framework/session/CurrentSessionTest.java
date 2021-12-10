package lapr2.framework.session;

import lapr2.framework.company.Company;
import lapr2.framework.company.user.user.User;
import lapr2.framework.company.user.user.UserManager;
import lapr2.framework.exceptions.InvalidLoginException;
import lapr2.framework.homeservices.HomeServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrentSessionTest {

	@Test
	void login() {
		HomeServices homeServices = HomeServices.getInstance();

		homeServices.setSession(new CurrentSession());
		CurrentSession currentSession = homeServices.getSession();

		UserManager userManager = new UserManager("");

		userManager.add(new User("test", "test"));

		Company company = new Company("", "");
		company.getDataManager().set(userManager);

		HomeServices.getInstance().setCompany(company);

		assertThrows(InvalidLoginException.class, () -> currentSession.login("test1", "test"));

		assertThrows(InvalidLoginException.class, () -> currentSession.login("test", "test1"));

		assertDoesNotThrow(() -> currentSession.login("test", "test"));
	}

	@Test
	void logout() {
		HomeServices homeServices = HomeServices.getInstance();

		homeServices.setSession(new CurrentSession());
		CurrentSession currentSession = homeServices.getSession();

		UserManager userManager = new UserManager("");

		userManager.add(new User("test", "test"));

		Company company = new Company("", "");
		company.getDataManager().set(userManager);

		HomeServices.getInstance().setCompany(company);

		assertDoesNotThrow(() -> currentSession.login("test", "test"));

		currentSession.logout();

		assertNull(currentSession.getUser());
	}
}