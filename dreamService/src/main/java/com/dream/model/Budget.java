/****************************************************************************
 * Copyright Peerawit Praphanwittaya & Apirat Puttaraksa                    * 
 *                                                                          * 
 * Licensed under the Apache License, Version 2.0 (the "License");          * 
 * you may not use this file except in compliance with the License.         * 
 * You may obtain a copy of the License at                                  * 
 * 																		    * 
 *     http://www.apache.org/licenses/LICENSE-2.0                           * 
 * 																		    * 
 * Unless required by applicable law or agreed to in writing, software	    * 
 * distributed under the License is distributed on an "AS IS" BASIS,        * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * 
 * See the License for the specific language governing permissions and      * 
 * limitations under the License.                                           * 
 */

package com.dream.model;

import java.sql.Date;

import com.dream.util.ParseDate;

public class Budget {
	private int		budgetId;
	private int		type_id;
	private Date	startTime;
	private Date	endTime;
	private double	goal;
	private String	username;
	private double	amount;
	private String	type_description;

	public Budget() {
	}

	public Budget(int budgetId, int type_id, Date startTime, Date endTime,
			double goal, String username, double amount) {
		this.budgetId = budgetId;
		this.type_id = type_id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.goal = goal;
		this.username = username;
		this.amount = amount;
	}
	
	public Budget(int budgetId, int type_id, Date startTime, Date endTime,
			double goal, String username, double amount, String type_description) {
		this.budgetId = budgetId;
		this.type_id = type_id;
		this.startTime = startTime;
		this.endTime = endTime;
		this.goal = goal;
		this.username = username;
		this.amount = amount;
		this.type_description = type_description;
	}

	public Budget(int budgetId, int type_id, String startTime, String endTime,
			double goal, String username) {
		this.budgetId = budgetId;
		this.type_id = type_id;
		this.startTime = ParseDate.getCurrentDate(startTime);
		this.endTime = ParseDate.getCurrentDate(endTime);
		this.goal = goal;
		this.username = username;
	}
	
	public Budget(int type_id, String startTime, String endTime,
			double goal, String username) {
		this.type_id = type_id;
		this.startTime = ParseDate.getCurrentDate(startTime);
		this.endTime = ParseDate.getCurrentDate(endTime);
		this.goal = goal;
		this.username = username;
	}

	public int getBudgetId() {
		return budgetId;
	}

	public void setBudgetId(int budgetId) {
		this.budgetId = budgetId;
	}

	public int getType_id() {
		return type_id;
	}

	public void setType_id(int type_id) {
		this.type_id = type_id;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getGoal() {
		return goal;
	}

	public void setGoal(double goal) {
		this.goal = goal;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getType_description() {
		return type_description;
	}

	public void setType_description(String type_description) {
		this.type_description = type_description;
	}

}
