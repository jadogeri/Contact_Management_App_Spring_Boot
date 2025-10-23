package com.josephadogeri.contact_management_app.utils;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CredentialsValidatorUtilTest {
	@Test
	public void isValidEmail() {
		CredentialsValidatorUtil c = new CredentialsValidatorUtil();
		String email = "johndoe1@gmail.com";
		boolean expected = true;
		boolean actual = c.isValidEmail(email);

		assertEquals(expected, actual);
	}

	@Test
	public void isValidPassword() {
		CredentialsValidatorUtil c = new CredentialsValidatorUtil();
		String password = "joH@nd03241";
		boolean expected = true;
		boolean actual = c.isValidPassword(password);

		assertEquals(expected, actual);
	}

	@Test
	public void isValidUsername() {
		CredentialsValidatorUtil c = new CredentialsValidatorUtil();
		String username = "Johndoe184";
		boolean expected = true;
		boolean actual = c.isValidUsername(username);

		assertEquals(expected, actual);
	}
}
