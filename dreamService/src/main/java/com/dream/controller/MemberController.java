package com.dream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.dao.impl.JdbcMemberDAO;
import com.dream.model.Member;

@RestController
@RequestMapping("/member")
public class MemberController {
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public int insertUser(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "fname", required = true) String fname,
			@RequestParam(value = "lname", required = true) String lname,
			@RequestParam(value = "age", required = true) int age,
			@RequestParam(value = "nickname", required = false) String nickname) {
		Member member = new Member(username, password, email, phone, fname,
				lname, age, nickname);
		JdbcMemberDAO jdbcMemberDao = new JdbcMemberDAO();
		return jdbcMemberDao.insert(member);
	}
}
