package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dream.model.Budget;

public class BudgetRowMapper implements RowMapper<Budget> {

	@Override
	public Budget mapRow(ResultSet resultSet, int line) throws SQLException {
		BudgetExtractor budget = new BudgetExtractor();
		return budget.extractData(resultSet);
	}

}
