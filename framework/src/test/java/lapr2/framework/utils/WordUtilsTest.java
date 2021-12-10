package lapr2.framework.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordUtilsTest {

	@Test
	void capitalize() {
		String text = "lorem_ipSum doLor sit amet, consectetur adipiscing_elit.";
		String expected = "Lorem Ipsum Dolor Sit Amet, Consectetur Adipiscing Elit.";

		assertEquals(expected, WordUtils.capitalize(text));
	}

	@Test
	void capitalizeFirstLetter() {
		String text = "test";
		String expected = "Test";

		assertEquals(expected, WordUtils.capitalizeFirstLetter(text));
	}

	@Test
	void isInteger() {
		String textInteger = "123";
		String textFloat = "123.4";
		String textLetters = "a23";

		assertTrue(WordUtils.isInteger(textInteger));
		assertFalse(WordUtils.isInteger(textFloat));
		assertFalse(WordUtils.isInteger(textLetters));
	}

	@Test
	void isDouble() {
		String textDouble1 = "123";
		String textDouble2 = "123.23";
		String textDouble3 = "123,23";

		assertTrue(WordUtils.isDouble(textDouble1));
		assertTrue(WordUtils.isDouble(textDouble2));
		assertFalse(WordUtils.isDouble(textDouble3));
	}

	@Test
	void isEmailValid() {
		String email = "abcd@google.com";
		String email1 = "abcd@google.com@microsoft.com";
		String email2 = "abcd@abcd";

		assertTrue(WordUtils.isEmailValid(email));
		assertFalse(WordUtils.isEmailValid(email1));
		assertFalse(WordUtils.isEmailValid(email2));
	}

	@Test
	void isTaxIdentificationNumberValid() {
		String taxIdentificationNumber = "501442600";
		String taxIdentificationNumber1 = "abcdefghi";
		String taxIdentificationNumber2 = "123";

		assertTrue(WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber));
		assertFalse(WordUtils.isTaxIdentificationNumberValid(null));
		assertFalse(WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber1));
		assertFalse(WordUtils.isTaxIdentificationNumberValid(taxIdentificationNumber2));
	}

	@Test
	void isPhoneNumberValid() {
		String phoneNumber = "912345678"; //correct
		String phoneNumber1 = "255123456"; //correct
		String phoneNumber2 = "+351255123456"; //correct
		String phoneNumber3 = "+351912345678"; //correct
		String phoneNumber4 = ""; //few digits
		String phoneNumber5 = "9123456789"; //to many digits
		String phoneNumber6 = "312345678"; //starts with 3
		String phoneNumber7 = "9351912345678"; //does not start with +
		String phoneNumber8 = "+352912345678"; //does not start with +351
		String phoneNumber9 = "+351312345678"; //the "real" number starts with 3
		String phoneNumber10 = "91";
		String phoneNumber11 = "9";

		assertTrue(WordUtils.isPhoneNumberValid(phoneNumber));
		assertTrue(WordUtils.isPhoneNumberValid(phoneNumber1));
		assertTrue(WordUtils.isPhoneNumberValid(phoneNumber2));
		assertTrue(WordUtils.isPhoneNumberValid(phoneNumber3));
		assertFalse(WordUtils.isPhoneNumberValid(null));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber4));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber4));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber5));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber6));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber7));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber8));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber9));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber10));
		assertFalse(WordUtils.isPhoneNumberValid(phoneNumber11));
	}

	@Test
	void isPostCodeValid() {
		String postCode = "1234-123"; //correct
		String postCode1 = "1234-123-12"; //does not have 2 fields
		String postCode2 = "a234-123"; //first field is not an integer
		String postCode3 = "1234-a23"; //second field is not an integer
		String postCode4 = "12345-123"; //first field has not 4 digits
		String postCode5 = "1234-"; //second field has less than 1 digit
		String postCode6 = "1234-1234"; //second field has more than 3 digits
		String postCode7 = "1234-1"; //correct
		String postCode8 = "1234-12"; //correct

		assertTrue(WordUtils.isPostCodeValid(postCode));
		assertFalse(WordUtils.isPostCodeValid(null));
		assertFalse(WordUtils.isPostCodeValid(postCode1));
		assertFalse(WordUtils.isPostCodeValid(postCode2));
		assertFalse(WordUtils.isPostCodeValid(postCode3));
		assertFalse(WordUtils.isPostCodeValid(postCode4));
		assertFalse(WordUtils.isPostCodeValid(postCode5));
		assertFalse(WordUtils.isPostCodeValid(postCode6));
		assertTrue(WordUtils.isPostCodeValid(postCode7));
		assertTrue(WordUtils.isPostCodeValid(postCode8));
	}

	@Test
	void generatePassword() {
		for (int i = 1; i < 50; i++)
			assertEquals(i, WordUtils.generatePassword(i).length());
	}
}