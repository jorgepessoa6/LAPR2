package lapr2.framework.company.location.postcode;

import lapr2.framework.company.Company;
import lapr2.framework.company.location.geographicalarea.GeographicalArea;
import lapr2.framework.company.location.geographicalarea.GeographicalAreaManager;
import lapr2.framework.homeservices.HomeServices;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostCodeTest {

	@Test
	void isValid() {
		PostCode postCode1 = new PostCode("", "", "", -100d, -190d, "");
		PostCode postCode2 = new PostCode("TestDistrict", "", "", -100d, -190d, "");
		PostCode postCode3 = new PostCode("TestDistrict", "TestMunicipality", "", -100d, -190d, "");
		PostCode postCode4 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -100d, -190d, "");
		PostCode postCode5 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -190d, "");
		PostCode postCode6 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "");
		PostCode postCode7 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, 190, "TestPostCode");
		PostCode postCode8 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", 100, -170d, "TestPostCode");
		PostCode postCode9 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, 200, "TestPostCode");
		PostCode postCode10 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -200, "TestPostCode");
		PostCode postCode11 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -100d, -170, "TestPostCode");
		PostCode postCode12 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -100d, -170, "TestPostCode");
		PostCode postCode13 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170, "TestPostCode");

		assertFalse(postCode1.isValid());
		assertFalse(postCode2.isValid());
		assertFalse(postCode3.isValid());
		assertFalse(postCode4.isValid());
		assertFalse(postCode5.isValid());
		assertFalse(postCode6.isValid());
		assertFalse(postCode7.isValid());
		assertFalse(postCode8.isValid());
		assertFalse(postCode9.isValid());
		assertFalse(postCode10.isValid());
		assertFalse(postCode11.isValid());
		assertFalse(postCode12.isValid());
		assertTrue(postCode13.isValid());
	}

	@Test
	void equalsAndHashCode() {
		PostCode postCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostCode postCode1 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostCode postCode2 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -70d, -170d, "TestPostCode");
		PostCode postCode3 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -150d, "TestPostCode");
		PostCode postCode4 = new PostCode("", "TestMunicipality", "TestLocality", -80d, -170d, "TestPostCode");
		PostCode postCode5 = new PostCode("TestDistrict", "", "TestLocality", -80d, -170d, "TestPostCode");
		PostCode postCode6 = new PostCode("TestDistrict", "TestMunicipality", "", -80d, -170d, "TestPostCode");
		PostCode postCode7 = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -80d, -170d, "");

		assertEquals(postCode, postCode);
		assertNotEquals(postCode, new Object());
		assertEquals(postCode, postCode1);
		assertNotEquals(postCode, postCode2);
		assertNotEquals(postCode, postCode3);
		assertNotEquals(postCode, postCode4);
		assertNotEquals(postCode, postCode5);
		assertNotEquals(postCode, postCode6);
		assertNotEquals(postCode, postCode7);

		assertEquals(postCode.hashCode(), postCode.hashCode());
		assertEquals(postCode.hashCode(), postCode1.hashCode());
		assertNotEquals(postCode.hashCode(), postCode2.hashCode());
		assertNotEquals(postCode.hashCode(), postCode3.hashCode());
		assertNotEquals(postCode.hashCode(), postCode4.hashCode());
		assertNotEquals(postCode.hashCode(), postCode5.hashCode());
		assertNotEquals(postCode.hashCode(), postCode6.hashCode());
		assertNotEquals(postCode.hashCode(), postCode7.hashCode());
	}

	@Test
	void getNearestGeographicalArea() {
		PostCode postCode1 = new PostCode("Porto", "Porto", "Avenida da Boavista", 41.160925d, -8.647292d, "test");
		PostCode postCode2 = new PostCode("Porto", "Porto", "Alameda das Antas", 41.162967d, -8.586571d, "test");
		PostCode postCode3 = new PostCode("Porto", "Porto", "Rua de Egas Moniz", 41.162721d, -8.620486d, "test");
		PostCode postCode4 = new PostCode("Porto", "Porto", "Avenida Fernão Magalhães", 41.161482d, -8.591626d, "test");
		PostCode postCode5 = new PostCode("Porto", "Matosinhos", "Avenida Engenheiro Duarte Pacheco", 41.186409d, -8.695424d, "test");

		Company company = new Company("", "");
		HomeServices.getInstance().setCompany(company);

		GeographicalAreaManager geographicalAreaManager = new GeographicalAreaManager("");

		assertNull(geographicalAreaManager.getClosestGeographicalArea(postCode1));

		GeographicalArea geographicalArea1 = new GeographicalArea("Grande Porto 1", postCode1, 3000, 10, null);
		GeographicalArea geographicalArea2 = new GeographicalArea("Grande Porto 2", postCode2, 3000, 10, null);

		geographicalAreaManager.add(geographicalArea1);
		geographicalAreaManager.add(geographicalArea2);

		company.getDataManager().set(geographicalAreaManager);

		assertEquals(geographicalArea1, postCode3.getNearestGeographicalArea());
		assertEquals(geographicalArea2, postCode4.getNearestGeographicalArea());
		assertNull(postCode5.getNearestGeographicalArea());
		assertEquals(geographicalArea1, postCode1.getNearestGeographicalArea());
	}

	@Test
	void getDistance() {
		PostCode postCode1 = new PostCode("Porto", "Porto", "Avenida da Boavista", 41.160925d, -8.647292d, "4100-100");
		PostCode postCode2 = new PostCode("Porto", "Porto", "Alameda das Antas", 41.162967d, -8.586571d, "4100-100");

		assertEquals(5090, PostCode.getDistance(postCode1, postCode2), 10);
	}
}