package com.dream.dao.impl;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.FinanceDAO;
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
		String sql = "INSERT INTO finance (username, #finance_type, date_time, amount, description, #budget, #save)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
		numRow = jdbcTemplate.update(sql, finance.getUsername(),
				finance.getSaveId(), finance.getDateTime(),
				finance.getAmount(), finance.getDescription(),
				finance.getBudgetId(), finance.getSaveId());
		return numRow;
	}

	@Override
	public int update(Finance finance) {
		int numRow;
		String sql = "UPDATE finance SET amount=?,description=?,#budget=?, #save=?, #finance_type=?"
				+ " WHERE username=? and date_time=?";
		numRow = jdbcTemplate.update(sql, finance.getUsername(),
				finance.getFinanceId(), finance.getDateTime(),
				finance.getAmount(), finance.getDescription(),
				finance.getBudgetId(), finance.getSaveId());
		return numRow;
	}

	@Override
	public Finance findFromUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String username) {
		// TODO Auto-generated method stub
		return false;
	}
}
