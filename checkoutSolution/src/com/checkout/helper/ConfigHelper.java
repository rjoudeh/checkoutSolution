package com.checkout.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Helper Class to load the properties file needed by this service
 * 
 * @author Rania
 * 
 */

public class ConfigHelper {
	private Properties prop = new Properties();
	private boolean isLoaded = false;
	String offerPropFileName = "offersConfig.properties";
	private static ConfigHelper configHelper;

	/**
	 * Prevent creation instances (singleton class)
	 */
	private ConfigHelper() {
	}

	/**
	 * Get the single instance form this class
	 * 
	 * @return
	 */
	public static ConfigHelper getInstance() {
		if (configHelper == null) {
			configHelper = new ConfigHelper();
			configHelper.loadConfigProperties();
			configHelper.isLoaded = true;
		}

		// load the config file if not loaded!
		if (!configHelper.isLoaded) {
			configHelper.loadConfigProperties();
		}
		return configHelper;
	}

	/**
	 * Load config file
	 */
	private void loadConfigProperties() {
		try {
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream(offerPropFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '"
						+ offerPropFileName + "' not found in the classpath");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Return enableOffers value
	 * 
	 * @return
	 */
	public boolean isEnableOffers() {
		return Boolean
				.valueOf(prop.getProperty("enableOffers", "false").trim());
	}

	/**
	 * Return applesOffer value
	 * 
	 * @return
	 */
	public boolean isEnableApplesOffer() {
		return Boolean.valueOf(prop.getProperty("applesOffer", "false").trim());
	}

	/**
	 * Return orangesOffer value
	 * 
	 * @return
	 */
	public boolean isEnableOrangesOffer() {
		return Boolean
				.valueOf(prop.getProperty("orangesOffer", "false").trim());
	}

}
