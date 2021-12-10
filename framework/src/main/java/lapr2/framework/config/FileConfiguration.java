package lapr2.framework.config;

import lapr2.framework.homeservices.HomeServices;
import lombok.Getter;

import java.io.*;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Represents a base class for the configuration file of the application, providing access
 * to its properties.
 */
public class FileConfiguration {

	/**
	 * The properties filename.
	 */
	private static final String FILENAME = "config.properties";

	/**
	 * The properties object.
	 */
	@Getter
	private Properties properties;

	/**
	 * Constructs a new {@link FileConfiguration}.
	 */
	public FileConfiguration() {
		createFolder();
		load();
	}

	/**
	 * Returns the default values of the properties.
	 *
	 * @return the {@link Properties} object with default values
	 */
	private Properties getDefaults() {
		Properties defaultProperties = new Properties();

		try {
			defaultProperties.load(getClass().getResourceAsStream("/" + FILENAME));
		} catch (IOException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Exception occurred", e);
		}

		return defaultProperties;
	}

	/**
	 * Searches for the property with the specified key in this property list.
	 * If the key is not found in this property list, the default property list,
	 * and its defaults, recursively, are then checked. The method returns
	 * {@code null} if the property is not found.
	 *
	 * @param key the property key.
	 * @return the value in this property list with the specified key value.
	 */
	public String getProperty(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Returns the number format specified in the properties file. The number format
	 * is defined with language tags, dot separator uses the 'en' tag and coma separator
	 * uses the 'fr' tag.
	 *
	 * @return the number format
	 */
	public NumberFormat getNumberFormat() {
		NumberFormat numberFormat = NumberFormat.getInstance(Locale.forLanguageTag(getProperty("locale")));

		numberFormat.setMaximumFractionDigits(2);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setRoundingMode(RoundingMode.UP);

		return numberFormat;
	}

	/**
	 * Returns the currency specified in the properties file.
	 *
	 * @return the currency
	 */
	public Currency getCurrency() {
		return Currency.getInstance(getProperty("currencyCode"));
	}

	/**
	 * Creates the parent folder for the configuration file, if it not exists.
	 */
	private boolean createFolder() {
		File file = new File(HomeServices.FOLDER_PATH);
		File binaryFolder = new File(HomeServices.FOLDER_PATH + "bin");
		File ordersFolder = new File(HomeServices.FOLDER_PATH + "orders");

		boolean success = false;

		if (!file.exists()) {
			success = file.mkdirs();
		}

		if (!binaryFolder.exists()) {
			success = binaryFolder.mkdirs();
		}

		if (!ordersFolder.exists()) {
			success = ordersFolder.mkdirs();
		}

		return success;
	}

	/**
	 * Loads the properties file. If the properties file does not exist the default
	 * values are copied.
	 */
	private void load() {
		File file = new File(HomeServices.FOLDER_PATH + FILENAME);

		if (file.exists()) {
			Properties defaultProperties = new Properties();

			try {
				defaultProperties.load(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), StandardCharsets.ISO_8859_1));
			} catch (IOException e) {
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Exception occurred.", e);
			}

			this.properties = defaultProperties;
		} else {
			try {
				getDefaults().store(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), StandardCharsets.ISO_8859_1), null);
				this.properties = getDefaults();
			} catch (IOException e) {
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Exception occurred.", e);
			}
		}
	}

	/**
	 * Saves the properties file.
	 */
	public void save() {
		File file = new File(HomeServices.FOLDER_PATH + FILENAME);

		try {
			getProperties().store(new OutputStreamWriter(new FileOutputStream(file.getAbsolutePath()), StandardCharsets.ISO_8859_1), null);
		} catch (IOException e) {
			HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Exception occurred.", e);
		}
	}
}
