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

import com.dream.util.DreamUtil;

public class Event {
	private int eventID;
	private String username;
	private String description;
	private Date end_time;

	public Event(String username, String description, String end_time) {
		this.username = username;
		this.description = description;
		this.end_time = DreamUtil.getCurrentDate(end_time);
	}

	public Event(int eventID, String username, String description,
			String end_time) {
		this.eventID = eventID;
		this.username = username;
		this.description = description;
		this.end_time = DreamUtil.getCurrentDate(end_time);
	}
	
	public Event(int eventID, String username, String description,
			Date end_time) {
		this.eventID = eventID;
		this.username = username;
		this.description = description;
		this.end_time = end_time;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}

}
