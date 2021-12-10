package lapr2.framework.company.manager;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.category.CategoryManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ManagerTest {

	@Test
	void add() {
		Manager<Category> manager = new CategoryManager("");

		Category category = new Category("id", "description");
		Category category1 = new Category("id", "description");

		manager.add(category);

		assertTrue(manager.elements.contains(category));
		assertFalse(manager.add(category1));
	}
}