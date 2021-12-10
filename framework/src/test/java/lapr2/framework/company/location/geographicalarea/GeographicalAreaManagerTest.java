package lapr2.framework.company.location.geographicalarea;

import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicalAreaManagerTest {

    @Test
    void createGeographicalArea() {
        String designation = "designationTeste";
        PostCode postCode = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
        double radius = 27.34;
        double travelCost = 49.99;

        GeographicalAreaManager manager = new GeographicalAreaManager("");
        GeographicalArea expected = new GeographicalArea(designation, postCode, radius, travelCost, null);

        assertEquals(expected, manager.createGeographicalArea(designation, postCode, radius, travelCost, null));
    }

    @Test
    void add() {
        String designation = "designationTeste";
        PostCode postCode = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
        double radius = 27.34;
        double travelCost = 49.99;

        GeographicalAreaManager manager = new GeographicalAreaManager("");
        GeographicalArea geographicalArea = new GeographicalArea(designation, postCode, radius, travelCost, null);

        manager.add(geographicalArea);

        assertTrue(manager.getElements().contains(geographicalArea));
    }

    @Test
    void isSecure() {
        String designation1 = "designationTest1";
        String designation2 = "designationTest2";
        PostCode postCode = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
        double radius = 27.34;
        double travelCost = 49.99;

        GeographicalArea geographicalAreaValid1 = new GeographicalArea(designation1, postCode, radius, travelCost, null);

        GeographicalArea geographicalAreaRepeated = new GeographicalArea(designation1, postCode, radius, travelCost, null);
        GeographicalArea geographicalAreaInvalid1 = new GeographicalArea("", postCode, radius, travelCost, null);
        GeographicalArea geographicalAreaInvalid2 = new GeographicalArea(designation2, postCode, radius, travelCost, null);

        GeographicalAreaManager manager = new GeographicalAreaManager("");

        assertTrue(manager.isSecure(geographicalAreaValid1));
        manager.add(geographicalAreaValid1);

        assertFalse(manager.isValid(geographicalAreaInvalid2));
        assertFalse(manager.isSecure(geographicalAreaRepeated));
        assertFalse(manager.isSecure(geographicalAreaInvalid1));


    }

    @Test
    void getClosestGeographicalArea() {
		PostCode furthestPostCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -50d, -50, "TestPostCode");
        PostCode closestPostCode = new PostCode("TestDistrict1", "TestMunicipality1", "TestLocality1", 8d, 8, "TestPostCode1");
        PostCode comparisonPostCode = new PostCode("TestDistrict2", "TestMunicipality2", "TestLocality2", 7d, 7, "TestPostCode2");
		PostCode evenFurthestPostCode = new PostCode("TestDistrict", "TestMunicipality", "TestLocality", -50d, -50, "TestPostCode");


        GeographicalArea furthestGeographicalArea = new GeographicalArea("designation", furthestPostCode, 10, 10, null);
        GeographicalArea closestGeographicalArea = new GeographicalArea("designation1", closestPostCode, 11, 11, null);
		GeographicalArea evenFurthestGeographicalArea = new GeographicalArea("designation", evenFurthestPostCode, 10, 10, null);

        GeographicalAreaManager geographicalAreaManager = new GeographicalAreaManager("");

        geographicalAreaManager.add(furthestGeographicalArea);
        geographicalAreaManager.add(closestGeographicalArea);
		geographicalAreaManager.add(evenFurthestGeographicalArea);

        assertEquals(closestGeographicalArea, geographicalAreaManager.getClosestGeographicalArea(comparisonPostCode));
        assertNotEquals(furthestGeographicalArea, geographicalAreaManager.getClosestGeographicalArea(comparisonPostCode));
    }

	@Test
	void getGeographicalArea() {
		GeographicalAreaManager manager = new GeographicalAreaManager("");

		String designation = "designation";

		assertNull(manager.getGeographicalArea(designation));

		String designation1 = "designationTest1";
		String designation2 = "designationTest2";
		PostCode postCode = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
		double radius = 27.34;
		double travelCost = 49.99;

		GeographicalArea geographicalArea = new GeographicalArea(designation1, postCode, radius, travelCost, null);
		GeographicalArea geographicalArea1 = new GeographicalArea(designation2, postCode, radius, travelCost, null);

		manager.add(geographicalArea);
		manager.add(geographicalArea1);

		assertEquals(geographicalArea1, manager.getGeographicalArea(designation2));
	}
}