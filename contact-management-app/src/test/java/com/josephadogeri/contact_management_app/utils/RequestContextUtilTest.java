package com.josephadogeri.contact_management_app.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class RequestContextUtilTest {
	@Test
	public void getCurrentHttpRequest() {
		HttpServletRequest expected = null;
		HttpServletRequest actual = RequestContextUtil.getCurrentHttpRequest();

		assertEquals(expected, actual);
	}
}
