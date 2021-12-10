package lapr2.framework.company.user.administrator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

/**
 * Represents a company administrator, providing access to their email, password.
 *
 * @author flow
 */
@AllArgsConstructor
public class Administrator implements Serializable {

	private static final long serialVersionUID = -7049926613194649534L;

	/**
	 * The name of the administrator.
	 */
	@Getter
	private String name;

	/**
	 * The email of the administrator.
	 */
	@Getter
	private String email;

}
