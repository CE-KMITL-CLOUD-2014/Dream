package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.Finance;

public class FinanceExtractor implements ResultSetExtractor<Finance> {

	@Override
	public Finance extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Finance finance = new Finance(resultSet.getInt(2),
				resultSet.getDouble(4),
				resultSet.getString("description"),
				resultSet.getTimestamp("date_time"),
				resultSet.getString("username"), resultSet.getInt("#budget"),
				resultSet.getInt("#save"), resultSet.getInt("#event"));
		return finance;
	}

}
