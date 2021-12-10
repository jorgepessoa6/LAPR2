package lapr2.framework.company;

import lapr2.framework.config.FileConfiguration;
import lapr2.framework.homeservices.HomeServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompanyTest {

	private Company company;

	private FileConfiguration fileConfiguration;

	@BeforeEach
	void setUp() {
		HomeServices.getInstance().setFileConfiguration(new FileConfiguration());

		fileConfiguration = HomeServices.getInstance().getFileConfiguration();
		company = new Company(fileConfiguration.getProperty("company.name"), fileConfiguration.getProperty("company.taxIdentificationNumber"));
	}

	@Test
	void getDesignation() {
		String expected = fileConfiguration.getProperty("company.name");

		assertEquals(expected, company.getDesignation());
	}

	@Test
	void getTaxIdentificationNumber() {
		String expected = fileConfiguration.getProperties().getProperty("company.taxIdentificationNumber");

		assertEquals(expected, company.getTaxIdentificationNumber());
	}

}