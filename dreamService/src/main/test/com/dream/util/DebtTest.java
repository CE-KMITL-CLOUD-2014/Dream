package com.dream.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class DebtTest {
	@Test
	public void can_calculate_debt_correctly() {
		Debt debt = new Debt(10000, 1, 12);
		assertEquals(838, Math.round(debt.calculate()));
	}
}
