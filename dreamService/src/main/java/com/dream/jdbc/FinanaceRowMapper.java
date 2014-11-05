package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dream.model.Finance;

public class FinanaceRowMapper implements RowMapper<Finance> {

	@Override
	public Finance mapRow(ResultSet resultSet, int line) throws SQLException {
		FinanceExtractor finance = new FinanceExtractor();
		return finance.extractData(resultSet);
	}

}
