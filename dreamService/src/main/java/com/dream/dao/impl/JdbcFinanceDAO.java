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

import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.FinanceDAO;
import com.dream.jdbc.FinanaceRowMapper;
import com.dream.jdbc.FinanceTypeRowMapper;
import com.dream.model.Finance;
import com.dream.model.FinanceType;

/**
 * JDBC for Finance Service
 * 
 * @author Peerawit Praphanwittaya
 *
 */
public class JdbcFinanceDAO implements FinanceDAO {
	DataSource dataSource;
	JdbcTemplate jdbcTemplate;

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
				finance.getFinanceId(), finance.getDateTime(),
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
		numRow = jdbcTemplate.update(sql, finance.getAmount(),
				finance.getDescription(), finance.getBudgetId(),
				finance.getSaveId(), finance.getFinanceId(),
				finance.getEventId(), finance.getUsername(),
				finance.getDateTime());
		return numRow;
	}

	@Override
	public List<Finance> list(String username) {
		String sql = "SELECT finance.*,finance_type.type,finance_type.description as type_description ,savings.description as saving_description,events.description as event_description FROM finance,finance_type,savings,events WHERE finance.username=? and finance.#finance_type = finance_type.#finance_type and finance.#save=savings.#save and finance.#event=events.#event";
		return (List<Finance>) jdbcTemplate.query(sql,
				new Object[] { username }, new FinanaceRowMapper());
	}

	@Override
	public int delete(String username, Timestamp dateTime) {
		String sql = "delete from finance where username = ? and date_time = ?";
		Object[] params = new Object[] { username, dateTime };
		int[] types = new int[] { Types.VARCHAR, Types.TIMESTAMP };
		return jdbcTemplate.update(sql, params, types);
	}

	@Override
	public List<Finance> listFromDateToDate(String username, Timestamp start,
			Timestamp end) {
		String sql = "SELECT finance.*,finance_type.type,finance_type.description as type_description ,savings.description as saving_description,events.description as event_description FROM finance,finance_type,savings,events WHERE date_time between ? and ? and finance.username=? and finance.#finance_type = finance_type.#finance_type and finance.#save=savings.#save and finance.#event=events.#event";
		Object[] params = new Object[] { start, end, username };
		int[] types = new int[] { Types.TIMESTAMP, Types.TIMESTAMP,
				Types.VARCHAR };
		return jdbcTemplate.query(sql, params, types, new FinanaceRowMapper());
	}

	public FinanceType findFinanceType(String description) {
		String sql = "SELECT * FROM finance_type WHERE description= ?";
		return jdbcTemplate.queryForObject(sql, new Object[] { description },
				new FinanceTypeRowMapper());
	}
}
