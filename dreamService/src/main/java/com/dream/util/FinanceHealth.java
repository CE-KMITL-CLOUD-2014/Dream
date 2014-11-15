package com.dream.util;

public class FinanceHealth {

	public double liquidity(double assets,double expenses){
		double liquidityValue;
		liquidityValue = assets/expenses;
		return liquidityValue;
	}
	public double debtRatio(double debt,double totalAsset){
		double debtRatio;
		debtRatio = debt/totalAsset;
		return debtRatio;
	}
	public double savingRation(double savingPerYear,double incomePerYear){
		double savingRation;
		savingRation = savingPerYear/incomePerYear;
		return savingRation;
	}
}
