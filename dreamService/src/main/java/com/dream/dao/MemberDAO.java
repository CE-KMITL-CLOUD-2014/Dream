package com.dream.dao;

import java.util.List;

import com.dream.model.Member;

public interface MemberDAO {
	public int insert(Member member);

	public int update(Member member);

	public Member findFromUsername(String username);

	public List<Member> list();

	public int delete(String username);
}
