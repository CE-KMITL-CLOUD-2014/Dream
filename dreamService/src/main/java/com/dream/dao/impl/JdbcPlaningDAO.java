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


package com.dream.dao.impl;

import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.PlanningDAO;
import com.dream.jdbc.SavingRowMapper;
import com.dream.model.Budget;
import com.dream.model.Event;
import com.dream.model.Saving;

public class JdbcPlaningDAO implements PlanningDAO {
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int insertSaving(Saving saving) {
		String sql = "INSERT INTO savings (username, goal, start_amount, end_time, description)"
				+ " VALUES (?, ?, ?, ?, ?)";
		return jdbcTemplate.update(sql, saving.getUsername(), saving.getGoal(),
				saving.getStartAmount(), saving.getEnd(),
				saving.getDescription());
	}

	@Override
	public int deleteSaving(int saveID, String username) {
		String sql1 = "update finance set #save = NULL where #save = ? and username = ?";
		String sql2 = "DELETE from savings where username = ? and #save = ?";
		jdbcTemplate.update(sql1, saveID, username);
		return jdbcTemplate.update(sql2, username, saveID);
	}

	@Override
	public int updateSaving(Saving saving) {
		String sql = "update savings set goal = ?,start_amount = ?,end_time = ?,description = ? where username = ? and #save = ?";
		Object[] params = new Object[] { saving.getGoal(),
				saving.getStartAmount(), saving.getEnd(),
				saving.getDescription(), saving.getUsername(),
				saving.getSaveID() };
		int[] types = new int[] { Types.REAL, Types.REAL, Types.DATE,
				Types.VARCHAR, Types.VARCHAR, Types.INTEGER };
		return jdbcTemplate.update(sql, params, types);
	}

	@Override
	public List<Saving> listSavingFromUsername(String username) {
		String sql = "select * from savings where username = ?";
		return jdbcTemplate.query(sql, new Object[] { username },
				new SavingRowMapper());
	}

	@Override
	public int insertEvent(Event event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteEvent(int eventID, String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateEvent(Event event) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Event> listEventFromUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insertBudget(Budget budget) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteBudget(int eventID, String username) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateBudget(Budget budget) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Budget> listBudgetFromUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

}
