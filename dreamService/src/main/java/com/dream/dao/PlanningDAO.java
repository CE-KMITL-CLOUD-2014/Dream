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

package com.dream.dao;

import java.util.List;

import com.dream.model.Budget;
import com.dream.model.Event;
import com.dream.model.Saving;

/**
 * DAO interface for Planing JDBC
 * 
 * @author Peerawit Praphanwittaya
 *
 */
public interface PlanningDAO {
	public int insertSaving(Saving saving);

	public List<Saving> listSavingFromUsername(String username);

	public int deleteSaving(int SaveID, String username);

	public int updateSaving(Saving saving);

	public int insertEvent(Event event);

	public int deleteEvent(int eventID, String username);

	public int updateEvent(Event event);

	public List<Event> listEventFromUsername(String username);

	public int insertBudget(Budget budget);

	public int deleteBudget(int eventID, String username);

	public int updateBudget(Budget budget);

	public List<Budget> listBudgetFromUsername(String username);
}