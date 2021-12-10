package lapr2.framework.company.user.serviceprovider;

import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.postaladdress.PostalAddress;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.servicerequest.Schedule;
import lapr2.framework.company.user.serviceprovider.dailyavailability.DailyAvailability;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ServiceProviderManagerTest {

    @Test
    void createServiceProvider() {
        String completeName = "Abel Almeida";
        String abbreviatedName = "Abel";
        String mechanographicalNumber = "1180855";
        String email = "1180855@isep.ipp.pt";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        ServiceProvider expected = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
        ServiceProviderManager manager = new ServiceProviderManager("");
        ServiceProvider result = manager.createServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);

        assertEquals(expected, result);

    }

    @Test
    void isSecure() {
        String completeName = "Abel Almeida";
        String abbreviatedName = "Abel";
        String mechanographicalNumber = "1180855";
        String email = "1180855@isep.ipp.pt";
        String invalidEmail = "";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");


        ServiceProvider serviceProvider = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
        ServiceProvider sp = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, invalidEmail, postCode);
        ServiceProviderManager manager = new ServiceProviderManager("");

        assertFalse(manager.isSecure(sp));
        assertTrue(manager.isSecure(serviceProvider));
        manager.add(serviceProvider);
        assertFalse(manager.isSecure(serviceProvider));
    }


    @Test
    void getServiceProvider() {
        String completeName = "Abel Almeida";
        String abbreviatedName = "Abel";
        String mechanographicalNumber = "1180855";
        String email = "1180855@isep.ipp.pt";
        String email2 = "1180761@isep.ipp.pt";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        ServiceProvider expected = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
        ServiceProvider expected2 = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email2, postCode);
        ServiceProviderManager manager = new ServiceProviderManager("");
        manager.add(expected);

        assertEquals(expected, manager.getServiceProvider(email));
        assertNotEquals(expected2, manager.getServiceProvider(email2));
    }

    @Test
    void getAvailableServiceProviders() {
        ServiceProviderManager manager = new ServiceProviderManager("");

        String completeName = "Abel Almeida", completeName1 = "another name";
        String abbreviatedName = "Abel";
        String mechanographicalNumber = "1180855";
        String email = "1180855@isep.ipp.pt";
        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        DailyAvailability dailyAvailability = new DailyAvailability(LocalDateTime.of(2019, 12, 10, 10, 0), LocalDateTime.of(2019, 12, 10, 12, 0));
        DailyAvailability dailyAvailability1 = new DailyAvailability(LocalDateTime.of(2020, 12, 10, 10, 0), LocalDateTime.of(2020, 12, 10, 12, 0));

        ServiceProvider serviceProvider = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
        serviceProvider.getDailyAvailabilityList().getElements().add(dailyAvailability);

        ServiceProvider serviceProvider1 = new ServiceProvider(mechanographicalNumber, completeName1, abbreviatedName, email, postCode);
        serviceProvider1.getDailyAvailabilityList().getElements().add(dailyAvailability1);

        manager.add(serviceProvider);
        manager.add(serviceProvider1);

        Schedule twentyNineteen = new Schedule(2019, 12, 10, 10, 0);
        Schedule twentyTwenty = new Schedule(2020, 12, 10, 10, 0);

        float halfHour = 0.5f;
        float threeHours = 3;

        assertTrue(manager.getAvailableServiceProviders(twentyNineteen, halfHour).contains(serviceProvider));
        assertFalse(manager.getAvailableServiceProviders(twentyNineteen, halfHour).contains(serviceProvider1));
        assertTrue(manager.getAvailableServiceProviders(twentyTwenty, halfHour).contains(serviceProvider1));
        assertFalse(manager.getAvailableServiceProviders(twentyTwenty, halfHour).contains(serviceProvider));
        assertFalse(manager.getAvailableServiceProviders(twentyTwenty, threeHours).contains(serviceProvider1));
    }

	@Test
	void getAvailableServiceProviders1() {
		ServiceProviderManager manager = new ServiceProviderManager("");

		String completeName = "Abel Almeida", completeName1 = "another name";
		String abbreviatedName = "Abel";
		String mechanographicalNumber = "1180855";
		String email = "1180855@isep.ipp.pt";
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		DailyAvailability dailyAvailability = new DailyAvailability(LocalDateTime.of(2019, 12, 10, 10, 0), LocalDateTime.of(2019, 12, 10, 12, 0));
		DailyAvailability dailyAvailability1 = new DailyAvailability(LocalDateTime.of(2020, 12, 10, 10, 0), LocalDateTime.of(2020, 12, 10, 12, 0));

		ServiceProvider serviceProvider = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
		serviceProvider.getDailyAvailabilityList().getElements().add(dailyAvailability);
		HashMap<PostCode, Double> acting = new HashMap<>();
		acting.put(postCode, 0d);
		GeographicalArea geographicalArea = new GeographicalArea("", postCode, 20, 20, acting);
		serviceProvider.addGeographicalArea(geographicalArea);

		ServiceProvider serviceProvider1 = new ServiceProvider(mechanographicalNumber, completeName1, abbreviatedName, email, postCode);
		serviceProvider1.getDailyAvailabilityList().getElements().add(dailyAvailability1);
		serviceProvider1.addGeographicalArea(geographicalArea);

		manager.add(serviceProvider);
		manager.add(serviceProvider1);

		Schedule twentyNineteen = new Schedule(2019, 12, 10, 10, 0);
		Schedule twentyTwenty = new Schedule(2020, 12, 10, 10, 0);

		float halfHour = 0.5f;
		float threeHours = 3;

		PostalAddress postalAddress = new PostalAddress("", postCode);
		PostalAddress postalAddress1 = new PostalAddress("", new PostCode("", "", "", 0, 0, ""));

		assertTrue(manager.getAvailableServiceProviders(twentyNineteen, halfHour, postalAddress).contains(serviceProvider));
		assertFalse(manager.getAvailableServiceProviders(twentyNineteen, halfHour, postalAddress).contains(serviceProvider1));
		assertTrue(manager.getAvailableServiceProviders(twentyTwenty, halfHour, postalAddress).contains(serviceProvider1));
		assertFalse(manager.getAvailableServiceProviders(twentyTwenty, halfHour, postalAddress).contains(serviceProvider));
		assertFalse(manager.getAvailableServiceProviders(twentyTwenty, threeHours, postalAddress).contains(serviceProvider1));
		assertFalse(manager.getAvailableServiceProviders(twentyTwenty, threeHours, postalAddress1).contains(serviceProvider1));
		assertFalse(manager.getAvailableServiceProviders(twentyTwenty, threeHours, postalAddress1).contains(serviceProvider));
	}

	@Test
	void getServiceProviderMN() {
		ServiceProviderManager manager = new ServiceProviderManager("");

		String completeName = "Abel Almeida", completeName1 = "another name";
		String abbreviatedName = "Abel";
		String mechanographicalNumber = "1180855";
		String email = "1180855@isep.ipp.pt";
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		DailyAvailability dailyAvailability = new DailyAvailability(LocalDateTime.of(2019, 12, 10, 10, 0), LocalDateTime.of(2019, 12, 10, 12, 0));
		DailyAvailability dailyAvailability1 = new DailyAvailability(LocalDateTime.of(2020, 12, 10, 10, 0), LocalDateTime.of(2020, 12, 10, 12, 0));

		ServiceProvider serviceProvider = new ServiceProvider(mechanographicalNumber, completeName, abbreviatedName, email, postCode);
		serviceProvider.getDailyAvailabilityList().getElements().add(dailyAvailability);

		ServiceProvider serviceProvider1 = new ServiceProvider(mechanographicalNumber + "2", completeName1, abbreviatedName, email, postCode);
		serviceProvider1.getDailyAvailabilityList().getElements().add(dailyAvailability1);

		manager.add(serviceProvider);
		manager.add(serviceProvider1);

		assertEquals(serviceProvider, manager.getServiceProviderMN(mechanographicalNumber));
		assertNull(manager.getServiceProviderMN("aabb"));
	}
}