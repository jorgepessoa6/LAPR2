package lapr2.framework.company.category;

import lapr2.framework.company.manager.Manager;

/**
 * Stores and retrieves all the {@link Category}. Allowing also their creation and validation.
 *
 * @author flow
 */
public class CategoryManager extends Manager<Category> {

	/**
	 * Constructs a new category manager associating the path to its binary file.
	 *
	 * @param filePath the path to the binary file
	 */
	public CategoryManager(String filePath) {
		super(filePath);
	}

	/**
	 * Creates a new {@link Category}, returning it.
	 *
	 * @param id          the ID of the category
	 * @param description the description of the category
	 * @return the created category
	 */
	public Category createCategory(String id, String description) {
		return new Category(id, description);
	}

	/**
	 * Returns a category by its ID.
	 *
	 * @param id the id of the category
	 * @return the category
	 */
	public Category getCategory(String id) {
		for (Category category : elements)
			if (category.getId().equals(id))
				return category;

		return null;
	}

	/**
	 * Returns a category by its description.
	 *
	 * @param description the description of the category
	 * @return the category
	 */
	public Category getCategoryByDescription(String description) {
		for (Category category : elements)
			if (category.getDescription().equals(description))
				return category;

		return null;
	}

	/**
	 * Verifies if a {@link Category} is valid.
	 *
	 * @param category the category
	 * @return <code>true</code> if the category is valid, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(Category category) {
		return (category.isValid() && this.isValid(category));
	}

	/**
	 * Verifies if a category is valid globally.
	 * Does this verifying if its ID is already in the set.
	 *
	 * @param category the category
	 * @return <code>true</code> if the category is valid globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(Category category) {
		//Verifies if there is already a Category with the same ID.
		for (Category c : elements)
			if (c.getId().equals(category.getId()))
				return false;

		return true;
	}
}
