package lapr2.framework.company.location.postcode;

import lapr2.framework.company.manager.Manager;
import lapr2.framework.config.FileConfiguration;
import lapr2.framework.homeservices.HomeServices;
import lapr2.framework.utils.NumberUtils;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Stores and retrieves all the {@link PostCode}s of the Company. Also allows their
 * creation and validation.
 *
 * @author flow
 */
public class PostCodeManager extends Manager<PostCode> {

	/**
	 * Constructs a new postcode manager associating a path to its binary file.
	 *
	 * @param filePath the file path to its binary file
	 */
	public PostCodeManager(String filePath) {
		super(filePath);
	}

	/**
	 * Gets a post code by its designation/zip code. (E.g. 4575-242)
	 * Returns null if such post code does not exist.
	 *
	 * @param postCodeDesignation the post code designation or zip code
	 * @return the post code associated with the designation
	 */
	public PostCode getPostCode(String postCodeDesignation) {
		String[] arr = postCodeDesignation.split("-");
		int firstField, secondField;

		try {
			firstField = Integer.parseInt(arr[0]);
			secondField = Integer.parseInt(arr[1]);
		} catch (Exception e) {
			return null;
		}

		String postCodeStr = firstField + "-" + secondField;
		for (PostCode postCode : elements)
			if (postCode.getPostCodeDesignation().equals(postCodeStr))
				return postCode;

		return null;
	}

	/**
	 * Returns a random post code designation from the manager's elements.
	 *
	 * @return the post code designation
	 */
	public String getRandomPostCodeDesignation() {
		int randomIndex = NumberUtils.getRandomInt(0, elements.size() - 1);
		return elements.get(randomIndex).getPostCodeDesignation();
	}

	/**
	 * Checks if a postal code is secure, verifying its integrity locally and globally.
	 *
	 * @param postCode the postal code
	 * @return <code>true</code> if the postal code is valid locally and globally, <code>false</code> otherwise
	 */
	@Override
	public boolean isSecure(PostCode postCode) {
		return postCode.isValid() && isValid(postCode);
	}

	/**
	 * Checks if a postal code is already in the elements of the manager.
	 *
	 * @param postCode the postal code
	 * @return <code>true</code> if the postal code is not in elements of the manager, <code>false</code> otherwise
	 */
	@Override
	public boolean isValid(PostCode postCode) {
		return !elements.contains(postCode);
	}

	/**
	 * Loads post codes from a specific file.
	 *
	 * @return <code>true</code> if the post codes were successfully loaded, <code>false</code> otherwise
	 */
	@Override
	public boolean load() {
		if (new File(filePath).exists()) {
			return super.load();
		} else {
			loadDefaults();
			return true;
		}
	}

	/**
	 * Loads the default postcodes from the .csv file.
	 */
	private void loadDefaults() {
		FileConfiguration fileConfiguration = HomeServices.getInstance().getFileConfiguration();

		Scanner scanner = new Scanner(getClass().getResourceAsStream("/postcodes.csv"), "UTF-8");

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();

			if (line.isEmpty() || line.charAt(0) == '#') continue;

			String[] fields = line.split(fileConfiguration.getProperty("fieldSeparator"));

			for (int index = 0; index < fields.length; index++) {
				fields[index] = fields[index].trim();
			}

			NumberFormat numberFormat = fileConfiguration.getNumberFormat();

			try {
				double latitude = numberFormat.parse(fields[10]).doubleValue();
				double longitude = numberFormat.parse(fields[11]).doubleValue();

				PostCode postCode = new PostCode(fields[1], fields[2], fields[6], latitude, longitude, String.format("%s-%s", fields[7], fields[8]));

				if (isSecure(postCode)) {
					elements.add(postCode);
				}
			} catch (ParseException e) {
				HomeServices.getInstance().getLoggerManager().getLogger().log(Level.SEVERE, "Could not load postcodes from .csv file", e);
			}
		}

		save();
	}

}
