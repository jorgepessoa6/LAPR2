package lapr2.framework.company.location.geographicalarea;

import lapr2.framework.company.location.postcode.PostCode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GeographicalAreaTest {

	@Test
	void isValid() {
		String designation = "designationTeste";
		PostCode postCode = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
		double radius = 27.34;
		double travelCost = 49.99;

		GeographicalArea geographicalAreaValid = new GeographicalArea(designation, postCode, radius, travelCost, null);
		GeographicalArea geographicalAreaInvalidDesignation = new GeographicalArea("", postCode, radius, travelCost, null);

		PostCode postCodeInvalid = new PostCode("", "municipality", "locality", 67.93, -110.45, "4400-123");
		GeographicalArea geographicalAreaInvalidPostCode = new GeographicalArea(designation, postCodeInvalid, radius, travelCost, null);

		GeographicalArea geographicalAreaInvalidDesignationAndPostCode = new GeographicalArea("", postCodeInvalid, radius, travelCost, null);

		assertTrue(geographicalAreaValid.isValid());
		assertFalse(geographicalAreaInvalidDesignation.isValid());
		assertFalse(geographicalAreaInvalidPostCode.isValid());
		assertFalse(geographicalAreaInvalidDesignationAndPostCode.isValid());

	}

	@Test
	void equalsAndHashCode() {
		String designation = "designationTeste";
		PostCode postCode = new PostCode("district", "municipality", "locality", 67.93, -110.45, "4400-123");
		double radius = 27.34;
		double travelCost = 49.99;

		GeographicalArea geographicalArea = new GeographicalArea(designation, postCode, radius, travelCost, null);
		GeographicalArea geographicalArea1 = new GeographicalArea(designation, postCode, radius, travelCost, null);

		Object object = new Object();

		GeographicalArea geographicalArea2 = new GeographicalArea("", postCode, radius, travelCost, null);
		GeographicalArea geographicalArea3 = new GeographicalArea(designation, null, radius, travelCost, null);
		GeographicalArea geographicalArea4 = new GeographicalArea(designation, postCode, radius + 1, travelCost, null);
		GeographicalArea geographicalArea5 = new GeographicalArea(designation, postCode, radius, travelCost + 1, null);

		assertNotEquals(geographicalArea, object);
		assertEquals(geographicalArea, geographicalArea);
		assertEquals(geographicalArea, geographicalArea1);
		assertNotEquals(geographicalArea, geographicalArea2);
		assertNotEquals(geographicalArea, geographicalArea3);
		assertNotEquals(geographicalArea, geographicalArea4);
		assertNotEquals(geographicalArea, geographicalArea5);

		assertEquals(geographicalArea.hashCode(), geographicalArea.hashCode());
		assertEquals(geographicalArea.hashCode(), geographicalArea1.hashCode());
		assertNotEquals(geographicalArea.hashCode(), geographicalArea2.hashCode());
		assertNotEquals(geographicalArea.hashCode(), geographicalArea3.hashCode());
		assertNotEquals(geographicalArea.hashCode(), geographicalArea4.hashCode());
		assertNotEquals(geographicalArea.hashCode(), geographicalArea5.hashCode());
	}
}