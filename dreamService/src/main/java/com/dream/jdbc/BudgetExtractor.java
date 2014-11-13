package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.Budget;

public class BudgetExtractor implements ResultSetExtractor<Budget> {

	@Override
	public Budget extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Budget budget = new Budget(resultSet.getInt(1), resultSet.getInt(7),
				resultSet.getDate(3), resultSet.getDate(4),
				resultSet.getDouble(2), resultSet.getString(6),
				resultSet.getDouble(5));
		return budget;
	}

}
