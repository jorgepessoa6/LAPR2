package lapr2.framework.company.manager;

import lapr2.framework.company.category.CategoryManager;
import lapr2.framework.company.user.client.ClientManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest {

	@Test
	void get() {
		DataManager dataManager = new DataManager();

		CategoryManager categoryManager = new CategoryManager("");

		dataManager.set(categoryManager);

		assertEquals(categoryManager, dataManager.get(CategoryManager.class));
		assertNull(dataManager.get(ClientManager.class));
	}

	@Test
	void set() {
		DataManager dataManager = new DataManager();

		CategoryManager a = new CategoryManager("a");
		CategoryManager b = new CategoryManager("b");
		ClientManager c = new ClientManager("c");

		dataManager.set(a);
		dataManager.set(b);
		dataManager.set(c);

		assertNotEquals(a, dataManager.get(CategoryManager.class));
		assertEquals(b, dataManager.get(CategoryManager.class));
		assertEquals(c, dataManager.get(ClientManager.class));
	}

	@Test
	void has() {
		DataManager dataManager = new DataManager();

		dataManager.set(new CategoryManager(""));

		assertTrue(dataManager.has(CategoryManager.class));
		assertFalse(dataManager.has(ClientManager.class));
	}
}