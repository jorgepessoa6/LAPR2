package lapr2.framework.company.user.humanresourcesofficer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HumanResourcesOfficerManagerTest {

	@Test
	void getHumanResourcesOfficer() {
		HumanResourcesOfficerManager manager = new HumanResourcesOfficerManager();

		String email = "email";
		HumanResourcesOfficer hro = new HumanResourcesOfficer("name", email);
		String email1 = "email1";
		HumanResourcesOfficer hro1 = new HumanResourcesOfficer("adm1", email1);

		manager.add(hro1);
		manager.add(hro);

		assertEquals(hro, manager.getHumanResourcesOfficer(email));
		assertNotEquals(hro1, manager.getHumanResourcesOfficer(email));
		assertEquals(hro1, manager.getHumanResourcesOfficer(email1));
	}
}