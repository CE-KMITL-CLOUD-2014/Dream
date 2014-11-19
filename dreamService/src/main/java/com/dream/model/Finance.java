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

import java.sql.Timestamp;

public class Finance {
	private int			financeId;
	private double		amount;
	private String		description;
	private Timestamp	dateTime;
	private String		username;
	private int			budgetId;
	private int			saveId;
	private String		save_description;
	private int			eventId;
	private String		event_description;
	private int			type;
	private String		type_description;

	public Finance() {
	}

	public Finance(int financeId, double amount, String description,
			Timestamp dateTime, String username, int budgetId, int saveId,
			int eventId) {
		this.financeId = financeId;
		this.amount = amount;
		this.description = description;
		this.dateTime = dateTime;
		this.username = username;
		this.budgetId = budgetId;
		this.saveId = saveId;
		this.eventId = eventId;
	}
	
	public Finance(int financeId, double amount, String description,
			Timestamp dateTime, String username, int budgetId, int saveId,String save_description,
			int eventId,String event_description,int type,String type_description) {
		this.financeId = financeId;
		this.amount = amount;
		this.description = description;
		this.dateTime = dateTime;
		this.username = username;
		this.budgetId = budgetId;
		this.saveId = saveId;
		this.save_description = save_description;
		this.eventId = eventId;
		this.event_description = event_description;
		this.type = type;
		this.type_description = type_description;
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

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getType_description() {
		return type_description;
	}

	public void setType_description(String type_description) {
		this.type_description = type_description;
	}

	public String getSave_description() {
		return save_description;
	}

	public void setSave_description(String save_description) {
		this.save_description = save_description;
	}

	public String getEvent_description() {
		return event_description;
	}

	public void setEvent_description(String event_description) {
		this.event_description = event_description;
	}

}
