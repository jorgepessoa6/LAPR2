package lapr2.framework.logger;

import lapr2.framework.homeservices.HomeServices;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.*;

/**
 * Represents the manager for the application's {@link Logger}.
 *
 * @author flow
 */
public class LoggerManager {

	/**
	 * The path to save the logs.
	 */
	private static final String LOGS_PATH = HomeServices.FOLDER_PATH + "logs/";

	/**
	 * The {@link Logger}.
	 */
	private static final Logger LOGGER = Logger.getLogger(LoggerManager.class.getName());

	/**
	 * Constructs a new {@link LoggerManager}.
	 */
	public LoggerManager() {
		LOGGER.setUseParentHandlers(false);

		ConsoleHandler handler = new ConsoleHandler();
		LOGGER.addHandler(handler);

		Formatter formatter = new LoggerFormatter();
		handler.setFormatter(formatter);

		createFolder();
		setupFile();
	}

	/**
	 * Creates the parent folder for the logs.
	 */
	private boolean createFolder() {
		File file = new File(LOGS_PATH);

		if (!file.exists()) {
			return file.mkdirs();
		}

		return false;
	}

	/**
	 * Setups the file to save the log of every application execution.
	 */
	private void setupFile() {
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");

		try {
			FileHandler fileHandler = new FileHandler(LOGS_PATH + LocalDateTime.now().format(dateTimeFormatter) + ".txt");
			Formatter formatter = new LoggerFormatter();

			fileHandler.setEncoding("UTF-8");
			fileHandler.setFormatter(formatter);

			LOGGER.addHandler(fileHandler);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Exception occurred", e);
		}
	}

	/**
	 * Returns the {@link Logger}.
	 *
	 * @return the logger.
	 */
	public Logger getLogger() {
		return LOGGER;
	}
}
