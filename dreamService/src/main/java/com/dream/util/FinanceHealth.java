package com.dream.util;

public class FinanceHealth {
	/**
	 * Calculate liquidity
	 * 
	 * @param assets
	 *            total assets
	 * @param expenses
	 *            per month
	 * @return liquidity result (month)
	 */
	public double liquidity(double assets, double expenses) {
		double liquidityValue;
		liquidityValue = assets / expenses;
		return liquidityValue;
	}

	/**
	 * Calculate debt ratio
	 * 
	 * @param debt
	 * @param totalAsset
	 * @return debt ratio
	 */
	public double debtRatio(double debt, double totalAsset) {
		double debtRatio;
		debtRatio = debt / totalAsset;
		return debtRatio;
	}

	/**
	 * Calculate saving ratio
	 * 
	 * @param savingPerYear
	 * @param incomePerYear
	 * @return saving ratio
	 */

	public double savingRation(double savingPerYear, double incomePerYear) {
		double savingRation;
		savingRation = savingPerYear / incomePerYear;
		return savingRation;
	}
}
