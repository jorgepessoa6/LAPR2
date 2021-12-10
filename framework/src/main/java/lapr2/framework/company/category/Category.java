package lapr2.framework.company.category;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents a category that catalogues services, providing access to its ID and description.
 *
 * @author flow
 */
@AllArgsConstructor
public class Category implements Serializable {

	private static final long serialVersionUID = -718910889673668707L;

	/**
	 * The ID of the category.
	 */
	@Getter
	private final String id;

	/**
	 * The description of the category.
	 */
	@Getter
	private final String description;

	/**
	 * Verifies if a category is valid locally.
	 *
	 * @return <code>true</code> if the category is valid locally, <code>false</code> otherwise
	 */
	public boolean isValid() {
		return !id.isEmpty() &&
				!description.isEmpty();
	}

	/**
	 * Verifies if this category is equals to a given object.
	 *
	 * @param o the given object
	 * @return <code>true</code> if they are equal, <code>false</code> otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Category)) return false;
		Category category = (Category) o;
		return Objects.equals(getId(), category.getId()) &&
				Objects.equals(getDescription(), category.getDescription());
	}

	/**
	 * Generates a unique code for this instance.
	 *
	 * @return the code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(getId(), getDescription());
	}

	@Override
	public String toString() {
		return description;
	}
}
