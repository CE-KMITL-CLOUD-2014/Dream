package com.dream.util;

import static org.junit.Assert.*;
import org.junit.Test;

public class FinanceHealthTest {
	FinanceHealth financeHealth = new FinanceHealth();
	@Test
	public void can_calculate_liquidity_correctly() {
		assertEquals(100, Math.round(financeHealth.liquidity(1000, 10)));
	}
	@Test
	public void can_calculate_saving_ratio_correctly(){
		assertEquals(0.1, financeHealth.savingRation(100, 1000), 0.1);
	}
	@Test
	public void can_calculate_debtRatio_ratio_correctly(){
		assertEquals(0.1, financeHealth.debtRatio(100, 1000), 0.1);
	}
}
