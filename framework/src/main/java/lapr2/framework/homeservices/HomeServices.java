package lapr2.framework.homeservices;

import lapr2.framework.company.Company;
import lapr2.framework.config.FileConfiguration;
import lapr2.framework.logger.LoggerManager;
import lapr2.framework.session.CurrentSession;
import lombok.Getter;
import lombok.Setter;

import java.util.logging.Level;

/**
 * Represents the main class of the application, providing access to its logger and configuration
 * file. Members of this class, such as the company, the session and the user manager, can be
 * defined, if necessary.
 *
 * @author flow
 */
public class HomeServices {

	/**
	 * The application save path.
	 */
	public static final String FOLDER_PATH = System.getProperty("user.home") + "/Documents/flow/";

	/**
	 * The unique {@link HomeServices} instance.
	 */
	@Getter
	private static HomeServices instance = new HomeServices();

	/**
	 * The logger manager.
	 */
	@Getter
	private final LoggerManager loggerManager = new LoggerManager();

	/**
	 * The file responsible for handling the application's configurations.
	 */
	@Getter
	@Setter
	private FileConfiguration fileConfiguration;

	/**
	 * The {@link Company}. Not set if not required.
	 */
	@Getter
	@Setter
	private Company company;

	/**
	 * The {@link CurrentSession}. Not set if not required.
	 */
	@Getter
	@Setter
	private CurrentSession session;

	/**
	 * Constructs the class instance. The private constructor restricts the instantiation
	 * of the class to one single instance.
	 */
	private HomeServices() {
		loggerManager.getLogger().log(Level.INFO, "Singleton is now working");
	}
}
