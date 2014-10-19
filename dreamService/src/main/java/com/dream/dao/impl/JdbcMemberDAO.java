package com.dream.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dream.dao.MemberDAO;
import com.dream.jdbc.MemberExtractor;
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
		String sql = "INSERT INTO users (username, password, email, fname,lname,birth,phone,nickname,enabled,regis_date)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		String sql2 = "INSERT INTO user_roles (username,role)" + "VALUEs (?,?)";
		member.setEnable(true);
		numRow = jdbcTemplate.update(sql, member.getUsername(),
				member.getPassword(), member.getEmail(), member.getFname(),
				member.getLname(), member.getBirth(), member.getPhone(),
				member.getNickname(), (int) (member.isEnable() ? 1 : 0),
				member.getRegis_date());
		jdbcTemplate.update(sql2, member.getUsername(), "ROLE_USER");
		return numRow;
	}

	@Override
	public Member findFromUsername(String username) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM users WHERE user=" + username;
		return jdbcTemplate.query(sql, new MemberExtractor());
	}

	@Override
	public List<Member> list() {
		List userList = new ArrayList();
		String sql = "select * from users";
		userList = jdbcTemplate.query(sql, new MemberRowMapper());
		return userList;
	}

	@Override
	public int delete(String username) {
		String sql = "DELETE FROM users WHERE username=?";
		String sql2 = "DELETE FROM user_roles WHERE username=?";
		jdbcTemplate.update(sql2, username);
		return jdbcTemplate.update(sql, username);
	}

	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		int numRow;
		String sql = "UPDATE users SET email=?, fname=?,lname=?,birth=?,phone=?,nickname=?"
				+ " WHERE username=?";
		numRow = jdbcTemplate.update(sql, member.getEmail(), member.getFname(),
				member.getLname(), member.getBirth(), member.getPhone(),
				member.getNickname(), member.getUsername());
		return numRow;
	}
}
