package lapr2.framework.company.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryManagerTest {

	@Test
	void createCategory() {
		String id = "123";
		String description = "test";

		CategoryManager manager = new CategoryManager("");
		Category expected = new Category(id, description);

		assertEquals(expected, manager.createCategory(id, description));
	}

	@Test
	void isSecure() {
		String id1 = "123";
		String id2 = "456";
		String description = "test";

		Category categoryValid1 = new Category(id1, description);
		Category categoryValid2 = new Category(id2, description);
		Category categoryRepeated = new Category(id1, description);
		Category categoryInvalid = new Category("", description);

		CategoryManager manager = new CategoryManager("");

		assertTrue(manager.isSecure(categoryValid1));
		manager.add(categoryValid1);

		assertTrue(manager.isSecure(categoryValid2));
		assertFalse(manager.isSecure(categoryRepeated));
		assertFalse(manager.isSecure(categoryInvalid));
	}

	@Test
	void add() {
		String id = "123";
		String description = "test";

		CategoryManager manager = new CategoryManager("");
		Category category = new Category(id, description);

		manager.add(category);

		assertTrue(manager.getElements().contains(category));
	}

	@Test
	void getCategory() {
		String id = "123", id1 = "456";
		String description = "test";

		CategoryManager manager = new CategoryManager("");
		Category category = new Category(id, description);
		Category category1 = new Category(id1, description);

		assertNull(manager.getCategory(id));

		manager.add(category1);
		manager.add(category);

		assertEquals(category, manager.getCategory(id));
	}
}