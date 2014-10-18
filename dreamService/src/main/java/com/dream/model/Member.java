package com.dream.model;

public class Member {
	private String	username;
	private String	password;
	private String	email;
	private String	phone;
	private String	fname;
	private String	lname;
	private int		age;
	private String	nickname;
	private boolean	enable;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public Member(String username, String password, String email, String phone,
			String fname, String lname, int age, String nickname) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.fname = fname;
		this.lname = lname;
		this.age = age;
		this.nickname = nickname;
		enable = true;
	}
}
