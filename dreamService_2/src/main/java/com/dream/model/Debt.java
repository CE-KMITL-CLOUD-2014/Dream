package com.dream.model;

public class Debt {
	private double	moneyLoan;
	private double	debtRate;
	private double	time;

	public Debt(double moneyLoan, double debtRate, double time) {
		this.moneyLoan = moneyLoan;
		this.debtRate = debtRate;
		this.time = time;
	}

	public double calculate() {
		double tempV, tempI, resultV;
		tempV = (1 / (1 + (this.debtRate / 1200)));
		resultV = tempV;
		for (int i = 1; i < this.time; i++) {
			resultV *= tempV;
		}
		tempI = this.debtRate / 1200;
		return (tempI / (1 - resultV)) * this.moneyLoan;
	}

	public double getMoneyLoan() {
		return moneyLoan;
	}

	public void setMoneyLoan(double moneyLoan) {
		this.moneyLoan = moneyLoan;
	}

	public double getDebtRate() {
		return debtRate;
	}

	public void setDebtRate(double debtRate) {
		this.debtRate = debtRate;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

}
