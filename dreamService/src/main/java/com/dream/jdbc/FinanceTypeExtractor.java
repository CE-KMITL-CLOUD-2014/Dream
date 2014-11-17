package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.FinanceType;

public class FinanceTypeExtractor implements ResultSetExtractor<FinanceType> {

	@Override
	public FinanceType extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		FinanceType financeType = new FinanceType(
				resultSet.getInt("#finance_type"), resultSet.getInt("type"),
				resultSet.getString("description"));
		return financeType;
	}

}
