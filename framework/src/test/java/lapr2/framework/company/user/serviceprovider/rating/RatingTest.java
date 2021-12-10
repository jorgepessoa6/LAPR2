package lapr2.framework.company.user.serviceprovider.rating;

import lapr2.framework.company.Company;
import lapr2.framework.company.location.postcode.PostCode;
import lapr2.framework.company.user.serviceprovider.ServiceProvider;
import lapr2.framework.company.user.serviceprovider.ServiceProviderManager;
import lapr2.framework.homeservices.HomeServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingTest {

	@Test
	void add() {
		Rating rating = new Rating();

		assertFalse(rating.add(-1));
		assertFalse(rating.add(6));
		assertTrue(rating.add(2));
	}

	@Test
	void getReviewNumber() {
		Rating rating = new Rating();

		rating.add(2);

		assertEquals(0, rating.getReviewNumber(-1));
		assertEquals(0, rating.getReviewNumber(6));
		assertEquals(1, rating.getReviewNumber(2));
	}

	@Test
	void getMean() {
		Rating rating = new Rating();

		assertEquals(3.0f, rating.getMean());

		rating.add(3);
		rating.add(3);
		rating.add(4);

		float expected = (2f / 3f) * 3f + (1f / 3f) * 4f;

		assertEquals(expected, rating.getMean());
	}

	@Test
	void getStandardDeviation() {
		Rating rating = new Rating();

		assertEquals(0f, rating.getStandardDeviation());

		rating.add(3);
		rating.add(3);
		rating.add(4);

		double expected = (2f / 3f) * 3f * 3f + (1f / 3f) * 4f * 4f - Math.pow(rating.getMean(), 2);

		assertEquals(expected, rating.getStandardDeviation());
	}

	@Test
	void getSuggestedLabel() {
		Company company = new Company("", "");

		HomeServices.getInstance().setCompany(company);

		ServiceProviderManager serviceProviderManager = new ServiceProviderManager("");

		company.getDataManager().set(serviceProviderManager);

        PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

        ServiceProvider serviceProvider1 = new ServiceProvider("0", "", "", "", postCode);
        ServiceProvider serviceProvider2 = new ServiceProvider("1", "", "", "", postCode);
        ServiceProvider serviceProvider3 = new ServiceProvider("2", "", "", "", postCode);

		serviceProvider1.getRating().add(4);
		serviceProvider1.getRating().add(4);
		serviceProvider1.getRating().add(4);
		serviceProvider1.getRating().add(4);
		serviceProvider1.getRating().add(4);
		serviceProvider1.getRating().add(4);
		serviceProvider1.getRating().add(4);

		serviceProvider2.getRating().add(5);
		serviceProvider2.getRating().add(5);
		serviceProvider2.getRating().add(5);
		serviceProvider2.getRating().add(5);

		serviceProvider3.getRating().add(2);
		serviceProvider3.getRating().add(2);

		serviceProviderManager.add(serviceProvider1);
		serviceProviderManager.add(serviceProvider2);
		serviceProviderManager.add(serviceProvider3);

		Rating.Label expected1 = Rating.Label.REGULAR_PROVIDER;
		Rating.Label expected2 = Rating.Label.OUTSTANDING_PROVIDERS;
		Rating.Label expected3 = Rating.Label.WORST_PROVIDERS;

		assertEquals(expected1, serviceProvider1.getRating().getSuggestedLabel());
		assertEquals(expected2, serviceProvider2.getRating().getSuggestedLabel());
		assertEquals(expected3, serviceProvider3.getRating().getSuggestedLabel());
	}

	@Test
	void compareTo() {
		Rating rating = new Rating();
		Rating rating1 = new Rating();

		assertEquals(0, rating.compareTo(rating1));
		rating1.add(5);

		assertTrue(rating.compareTo(rating1) < 0);
		assertTrue(rating1.compareTo(rating) > 0);
	}

	@Test
	void equalsAndHashCode() {
		Rating rating = new Rating();
		rating.add(4);
		Rating rating1 = new Rating();
		rating1.add(4);
		Rating rating2 = new Rating();

		assertNotEquals(rating, new Object());
		assertEquals(rating, rating);
		assertEquals(rating, rating1);
		assertNotEquals(rating, rating2);
	}
}