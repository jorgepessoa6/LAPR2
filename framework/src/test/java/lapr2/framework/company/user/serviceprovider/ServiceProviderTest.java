package lapr2.framework.company.user.serviceprovider;

import lapr2.framework.company.category.Category;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailability;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServiceProviderTest {

	@Test
	void isValid() {
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", 100, -170d, "TestPostCode");
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");
		ServiceProvider serviceProvider = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProviderInvalid = new ServiceProvider("", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProviderInvalid2 = new ServiceProvider("123", "", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProviderInvalid3 = new ServiceProvider("123", "abc", "", "abc@gmail.com", postCode);
		ServiceProvider serviceProviderInvalid4 = new ServiceProvider("123", "abc", "abc", "", postCode);
		ServiceProvider serviceProviderInvalid5 = new ServiceProvider("123", "abc", "abc", "abc", postCode);
		ServiceProvider serviceProviderInvalid6 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode1);


		assertTrue(serviceProvider.isValid());
		assertFalse(serviceProviderInvalid.isValid());
		assertFalse(serviceProviderInvalid2.isValid());
		assertFalse(serviceProviderInvalid3.isValid());
		assertFalse(serviceProviderInvalid4.isValid());
		assertFalse(serviceProviderInvalid5.isValid());
		assertFalse(serviceProviderInvalid6.isValid());
	}

	@Test
	void addGeographicalArea() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");
		ServiceProvider serviceProvider = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);

		String designation = "designationTeste";
		PostCode postCode1 = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
		double radius = 27.34;
		double travelCost = 49.99;
		GeographicalArea geographicalAreaValid = new GeographicalArea(designation, postCode1, radius, travelCost, null);

		serviceProvider.addGeographicalArea(geographicalAreaValid);

		assertTrue(serviceProvider.getGeographicalAreas().contains(geographicalAreaValid));
		assertFalse(serviceProvider.addGeographicalArea(geographicalAreaValid));

	}

	@Test
	void addCategory() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");
		ServiceProvider serviceProvider = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);

		String id = "id";
		String description = "description";
		Category categoryValid = new Category(id, description);

		serviceProvider.addCategory(categoryValid);

		assertTrue(serviceProvider.getCategories().contains(categoryValid));
		assertFalse(serviceProvider.addCategory(categoryValid));
	}

	@Test
	void equalsAndHashcode() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");
		ServiceProvider serviceProvider1 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider2 = new ServiceProvider("1234", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider3 = new ServiceProvider("123", "abcd", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider4 = new ServiceProvider("123", "abc", "abcd", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider5 = new ServiceProvider("123", "abc", "abc", "abcd@gmail.com", postCode);
		ServiceProvider serviceProvider6 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", new PostCode("", "", "", 0, 0, ""));
		ServiceProvider serviceProvider7 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider8 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider9 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider10 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);
		ServiceProvider serviceProvider11 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);

		GeographicalArea geographicalArea = new GeographicalArea("desig", postCode, 20, 20, null);
		serviceProvider1.addGeographicalArea(geographicalArea);
		serviceProvider8.addGeographicalArea(geographicalArea);
		serviceProvider9.addGeographicalArea(geographicalArea);
		serviceProvider10.addGeographicalArea(geographicalArea);
		serviceProvider11.addGeographicalArea(geographicalArea);

		Category category = new Category("cat", "cat");
		serviceProvider1.addCategory(category);
		serviceProvider9.addCategory(category);
		serviceProvider10.addCategory(category);
		serviceProvider11.addCategory(category);

		DailyAvailability dailyAvailability = new DailyAvailability(LocalDateTime.of(2000, 10, 10, 10, 10), LocalDateTime.of(2000, 10, 20, 20, 20));
		serviceProvider1.getDailyAvailabilityList().add(dailyAvailability);
		serviceProvider10.getDailyAvailabilityList().add(dailyAvailability);
		serviceProvider11.getDailyAvailabilityList().add(dailyAvailability);

		serviceProvider1.getRating().add(5);
		serviceProvider11.getRating().add(5);

		Object object = new Object();

		assertEquals(serviceProvider1, serviceProvider1);
		assertNotEquals(serviceProvider1, object);
		assertNotEquals(serviceProvider1, serviceProvider2);
		assertNotEquals(serviceProvider1, serviceProvider3);
		assertNotEquals(serviceProvider1, serviceProvider4);
		assertNotEquals(serviceProvider1, serviceProvider5);
		assertNotEquals(serviceProvider1, serviceProvider6);
		assertNotEquals(serviceProvider1, serviceProvider7);
		assertNotEquals(serviceProvider1, serviceProvider8);
		assertNotEquals(serviceProvider1, serviceProvider9);
		assertNotEquals(serviceProvider1, serviceProvider10);

		assertEquals(serviceProvider1.hashCode(), serviceProvider1.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider2.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider3.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider4.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider5.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider6.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider7.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider8.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider9.hashCode());
		assertNotEquals(serviceProvider1.hashCode(), serviceProvider10.hashCode());
	}

	@Test
	void isEligible() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");
		ServiceProvider serviceProvider1 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", postCode);

		HashMap<PostCode, Double> acting = new HashMap<>();
		acting.put(postCode, 5d);
		GeographicalArea geographicalArea = new GeographicalArea("desig", postCode, 20, 20, acting);
		serviceProvider1.addGeographicalArea(geographicalArea);

		PostalAddress postalAddress = new PostalAddress("", postCode);
		PostalAddress postalAddress1 = new PostalAddress("", new PostCode("", "", "", 0, 0, ""));

		assertTrue(serviceProvider1.isEligible(postalAddress));
		assertFalse(serviceProvider1.isEligible(postalAddress1));
	}

	@Test
	void getClosestGeographicalArea() {
		PostCode sagres = new PostCode("sagres", "sagres", "sagres", 37.040581, -8.952902, "sagres");
		PostCode matosinhos = new PostCode("matosinhos", "matosinhos", "matosinhos", 41.205235, -8.687804, "matosinhos");
		ServiceProvider serviceProvider1 = new ServiceProvider("123", "abc", "abc", "abc@gmail.com", sagres);

		GeographicalArea algarve = new GeographicalArea("algarve", sagres, 20, 20, null);
		GeographicalArea porto = new GeographicalArea("porto", matosinhos, 20, 20, null);
		serviceProvider1.addGeographicalArea(algarve);
		serviceProvider1.addGeographicalArea(porto);

		PostCode gaia = new PostCode("gaia", "gaia", "gaia", 41.128603, -8.618074, "gaia");
		PostCode portimao = new PostCode("portimao", "portimao", "portimao", 37.132532, -8.550763, "portimao");

		assertEquals(serviceProvider1.getClosestGeographicalArea(gaia), porto);
		assertEquals(serviceProvider1.getClosestGeographicalArea(portimao), algarve);
	}
}