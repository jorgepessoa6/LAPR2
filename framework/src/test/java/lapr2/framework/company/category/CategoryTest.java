package lapr2.framework.company.category;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

	@Test
	void isValid() {
		String id = "id";
		String description = "description";

		Category categoryValid = new Category(id, description);
		Category categoryEmptyId = new Category("", description);
		Category categoryEmptyDescription = new Category(id, "");

		assertTrue(categoryValid.isValid());
		assertFalse(categoryEmptyId.isValid());
		assertFalse(categoryEmptyDescription.isValid());
	}

	@Test
	void equalsAndHashCode() {
		String id = "id";
		String description = "description";

		Category category = new Category(id, description);
		Object object = new Object();
		Category category1 = new Category(id, description);
		Category category2 = new Category("a", description);
		Category category3 = new Category(id, "a");

		assertNotEquals(category, object);
		assertEquals(category, category);
		assertEquals(category, category1);
		assertNotEquals(category, category2);
		assertNotEquals(category, category3);

		assertFalse(category.hashCode() == object.hashCode());
		assertTrue(category.hashCode() == category1.hashCode());
		assertFalse(category.hashCode() == category2.hashCode());
		assertFalse(category.hashCode() == category3.hashCode());
	}
}