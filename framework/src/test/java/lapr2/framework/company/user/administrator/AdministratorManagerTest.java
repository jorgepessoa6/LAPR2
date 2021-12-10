package lapr2.framework.company.user.administrator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AdministratorManagerTest {

	@Test
	void getAdministrator() {
		AdministratorManager manager = new AdministratorManager();

		String email = "email";
		Administrator administrator = new Administrator("name", email);
		String email1 = "email1";
		Administrator administrator1 = new Administrator("adm1", email1);

		manager.add(administrator1);
		manager.add(administrator);

		assertEquals(administrator, manager.getAdministrator(email));
		assertNotEquals(administrator1, manager.getAdministrator(email));
		assertEquals(administrator1, manager.getAdministrator(email1));
	}
}