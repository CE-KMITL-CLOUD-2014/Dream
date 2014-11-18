package com.dream.util;

import static org.junit.Assert.*;
import java.util.Date;

import org.junit.Test;

public class DreamUtilTest {
	@Test
	public void can_pasrse_date_correctly() {
		String dateStr = "1992-12-25";
		Date date = ParseDate.getCurrentDate(dateStr);
		assertEquals(92, date.getYear());
		assertEquals(11, date.getMonth());
		assertEquals(25, date.getDate());
	}
}
