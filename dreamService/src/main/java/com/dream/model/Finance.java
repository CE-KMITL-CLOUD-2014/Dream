package com.dream.model;

import java.sql.Timestamp;

public class Finance {
	private int			financeId;
	private double		amount;
	private String		description;
	private Timestamp	dateTime;
	private String		username;
	private int			budgetId;
	private int			saveId;

	public Finance(int financeId, double amount, String description,
			Timestamp dateTime, String username, int budgetId, int saveId) {
		this.financeId = financeId;
		this.amount = amount;
		this.description = description;
		this.dateTime = dateTime;
		this.username = username;
		this.budgetId = budgetId;
		this.saveId = saveId;
	}

	public int getFinanceId() {
		return financeId;
	}

	public void setFinanceId(int financeId) {
		this.financeId = financeId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDateTime() {
		return dateTime;
	}

	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}

	public int getSaveId() {
		return saveId;
	}

	public void setSaveId(int saveId) {
		this.saveId = saveId;
	}

}
