package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dream.model.FinanceType;

/**
 * For map Financetype data from database to row
 * 
 * @author shadowslight
 *
 */
public class FinanceTypeRowMapper implements RowMapper<FinanceType> {

	@Override
	public FinanceType mapRow(ResultSet resultSet, int line)
			throws SQLException {
		FinanceTypeExtractor financeType = new FinanceTypeExtractor();
		return financeType.extractData(resultSet);
	}

}
