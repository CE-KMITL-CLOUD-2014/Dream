package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.Member;

public class MemberExtractor implements ResultSetExtractor<Member> {

	@Override
	public Member extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Member member = new Member(resultSet.getString(1),
				resultSet.getString(2), resultSet.getString(3),
				resultSet.getString(4), resultSet.getString(5),
				resultSet.getString(6), resultSet.getInt(7),
				resultSet.getString(8));
		return member;
	}

}
