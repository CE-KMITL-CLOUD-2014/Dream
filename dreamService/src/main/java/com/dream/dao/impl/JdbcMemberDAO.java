package com.dream.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.MemberDAO;
import com.dream.jdbc.MemberRowMapper;
import com.dream.model.Member;

public class JdbcMemberDAO implements MemberDAO {
	DataSource		dataSource;
	JdbcTemplate	jdbcTemplate;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int insert(Member member) {
		int numRow;
		String sql = "INSERT INTO users (username, password, email, fname,lname,age,phone,nickname,enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		numRow = jdbcTemplate.update(sql, member.getUsername(),
				member.getPassword(), member.getEmail(), member.getFname(),
				member.getLname(), member.getAge(), member.getPhone(),
				member.getNickname(), (int) (member.isEnable() ? 1 : 0));
		return numRow;
	}

	@Override
	public Member find(String Username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Member> list() {
		List userList = new ArrayList();
		String sql = "select * from users";
		userList = jdbcTemplate.query(sql, new MemberRowMapper());
		return userList;
	}

	@Override
	public boolean delete(String Username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		int nunRow;
		String sql = "UPDATE users SET email, fname,lname,age,phone,nickname,sex"
				+ "WHERE username=?";
		nunRow = jdbcTemplate.update(sql, member.getUsername(),
				member.getPassword(), member.getEmail(), member.getFname(),
				member.getLname(), member.getAge(), member.getPhone(),
				member.getNickname(), member.isEnable());
		return nunRow;
	}

}
