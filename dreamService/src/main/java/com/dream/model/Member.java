/****************************************************************************
 * Copyright Peerawit Praphanwittaya & Apirat Puttaraksa                    * 
 *                                                                          * 
 * Licensed under the Apache License, Version 2.0 (the "License");          * 
 * you may not use this file except in compliance with the License.         * 
 * You may obtain a copy of the License at                                  * 
 * 																		    * 
 *     http://www.apache.org/licenses/LICENSE-2.0                           * 
 * 																		    * 
 * Unless required by applicable law or agreed to in writing, software	    * 
 * distributed under the License is distributed on an "AS IS" BASIS,        * 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. * 
 * See the License for the specific language governing permissions and      * 
 * limitations under the License.                                           * 
 */

package com.dream.model;

import java.sql.Date;
import java.sql.Timestamp;

import com.dream.util.ParseDate;

public class Member {
	private String username;
	private String password;
	private String email;
	private String phone;
	private String fname;
	private String lname;
	private Date birth;
	private String nickname;
	private boolean enable;
	private Timestamp regis_date;
	private String type;

	public Member(String username, String password, String email, String phone,
			String fname, String lname, String nickname, String birth,
			String type, boolean enable) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.fname = fname;
		this.lname = lname;
		this.birth = ParseDate.getCurrentDate(birth);
		this.nickname = nickname;
		this.enable = enable;
		this.type = "ROLE_USER";
		this.regis_date = new Timestamp(System.currentTimeMillis());
	}

	public Member(String username, String password, String email, String phone,
			String fname, String lname, String nickname, String birth,
			boolean enable, String type, Timestamp regis_date) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.fname = fname;
		this.lname = lname;
		this.birth = ParseDate.getCurrentDate(birth);
		this.nickname = nickname;
		this.enable = enable;
		this.type = type;
		this.regis_date = regis_date;
	}

	public Member(String username, String email, String phone, String fname,
			String lname, String birth, String nickname) {
		this.username = username;
		this.email = email;
		this.phone = phone;
		this.fname = fname;
		this.lname = lname;
		this.birth = ParseDate.getCurrentDate(birth);
		this.nickname = nickname;
	}

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

	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	public Timestamp getRegis_date() {
		return regis_date;
	}

	public void setRegis_date(Timestamp regis_date) {
		this.regis_date = regis_date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
