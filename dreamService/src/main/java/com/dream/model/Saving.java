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

public class Saving {
	private int saveID;
	private String username;
	private int goal;
	private int startAmount;
	private Date end;
	private String description;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

	public Saving(String username, int goal, int strartAmount, String end,
			String description) {
		this.username = username;
		this.goal = goal;
		this.startAmount = strartAmount;
		this.end = getCurrentDate(end);
		this.description = description;
	}
	public Saving(int saveID,String username, int goal, int strartAmount, String end,
			String description) {
		this.saveID = saveID;
		this.username = username;
		this.goal = goal;
		this.startAmount = strartAmount;
		this.end = getCurrentDate(end);
		this.description = description;
	}

	public int getSaveID() {
		return saveID;
	}

	public void setSaveID(int saveID) {
		this.saveID = saveID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getGoal() {
		return goal;
	}

	public void setGoal(int goal) {
		this.goal = goal;
	}

	public int getStartAmount() {
		return startAmount;
	}

	public void setStartAmount(int startAmount) {
		this.startAmount = startAmount;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private Date getCurrentDate(String time) {
		if(time!=null){
			Date date = null;
			try {
				date = new Date(format.parse(time).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		}else{
			return null;
		}
		
	}
}
