package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.dream.model.Member;

public class MemberRowMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet resultSet, int line) throws SQLException {
		MemberExtractor userExtractor = new MemberExtractor();
		return userExtractor.extractData(resultSet);
	}

}
