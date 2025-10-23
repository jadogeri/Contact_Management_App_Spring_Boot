package com.josephadogeri.contact_management_app.utils;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordGeneratorTest {
	@Test
	public void generateStrongPassword() {
		int length = 123;
		String expected = "abc";
		String actual = PasswordGenerator.generateStrongPassword(length);

		assertNotEquals(expected, actual);
        assertTrue(actual.length()  > 100);
	}
}
