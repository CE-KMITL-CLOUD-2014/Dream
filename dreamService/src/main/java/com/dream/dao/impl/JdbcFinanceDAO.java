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

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.FinanceDAO;
import com.dream.jdbc.FinanaceRowMapper;
import com.dream.model.Finance;

public class JdbcFinanceDAO implements FinanceDAO {
	DataSource		dataSource;
	JdbcTemplate	jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int insert(Finance finance) {
		int numRow;
		String sql = "INSERT INTO finance (username, #finance_type, date_time, amount, description, #budget, #save, #event)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		numRow = jdbcTemplate.update(sql, finance.getUsername(),
				finance.getSaveId(), finance.getDateTime(),
				finance.getAmount(), finance.getDescription(),
				finance.getBudgetId(), finance.getSaveId(),
				finance.getEventId());
		return numRow;
	}

	@Override
	public int update(Finance finance) {
		int numRow;
		String sql = "UPDATE finance SET amount=?,description=?,#budget=?, #save=?, #finance_type=?, #event=?"
				+ " WHERE username=? and date_time=?";
		numRow = jdbcTemplate.update(sql, finance.getUsername(),
				finance.getFinanceId(), finance.getDateTime(),
				finance.getAmount(), finance.getDescription(),
				finance.getBudgetId(), finance.getSaveId(),
				finance.getEventId());
		return numRow;
	}

	@Override
	public List<Finance> findFromUsername(String username) {
		String sql = "SELECT * FROM finance WHERE username=?";
		return (List<Finance>) jdbcTemplate.query(sql,
				new Object[] { username },
				new FinanaceRowMapper());
	}

	@Override
	public boolean delete(String username) {
		return false;
	}
}
