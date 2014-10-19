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
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.money.Money;

public class Budgets {
	private int					budgetId;
	private int					type_id;
	private Date				startTime;
	private Date				endTime;
	private Money				goal;
	private String				username;
	private Money				amount;
	private SimpleDateFormat	format	= new SimpleDateFormat("yyyy-MM-dd");

	public Budgets(int budgetId, int type_id, String startTime, String endTime,
			Money goal, String username, Money amount) {
		this.budgetId = budgetId;
		this.type_id = type_id;
		this.startTime = getCurrentDate(startTime);
		this.endTime = getCurrentDate(endTime);
		this.goal = goal;
		this.username = username;
		this.amount = amount;
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

	public Money getGoal() {
		return goal;
	}

	public void setGoal(Money goal) {
		this.goal = goal;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Money getAmount() {
		return amount;
	}

	public void setAmount(Money amount) {
		this.amount = amount;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	private Date getCurrentDate(String time) {
		Date date = null;
		try {
			date = new Date(format.parse(time).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
}
