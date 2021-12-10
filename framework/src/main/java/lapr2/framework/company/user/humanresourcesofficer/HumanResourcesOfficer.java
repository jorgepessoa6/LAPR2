package lapr2.framework.company.user.humanresourcesofficer;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Represents a company human resources officer, providing access to their email, password.
 *
 * @author flow
 */
@AllArgsConstructor
public class HumanResourcesOfficer implements Serializable {

	private static final long serialVersionUID = -3408569031133399356L;

	/**
	 * The name of the human resources officer.
	 */
	@Getter
	private String name;

	/**
	 * The email of the human resources officer.
	 */
	@Getter
	private String email;

}
