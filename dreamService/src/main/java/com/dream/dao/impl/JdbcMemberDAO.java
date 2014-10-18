package com.dream.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.MemberDAO;
import com.dream.jdbc.MemberRowMapper;
import com.dream.model.Member;

public class JdbcMemberDAO implements MemberDAO {
	private DataSource	dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public int insert(Member member) {
		int nunRow;
		String sql = "INSERT INTO users (username, password, email, fname,lname,age,phone,nickname,sex,enabled)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		nunRow = jdbcTemplate.update(sql, member.getUsername(),
				member.getPassword(), member.getEmail(), member.getFname(),
				member.getLname(), member.getAge(), member.getPhone(),
				member.getNickname(), member.isEnable());
		return nunRow;
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		nunRow = jdbcTemplate.update(sql, member.getUsername(),
				member.getPassword(), member.getEmail(), member.getFname(),
				member.getLname(), member.getAge(), member.getPhone(),
				member.getNickname(), member.isEnable());
		return nunRow;
	}

}
