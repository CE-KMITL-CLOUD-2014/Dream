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

package com.dream.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.dao.impl.JdbcMemberDAO;
import com.dream.model.Member;
import com.dream.model.Row;

/**
 * Controller for Member Service
 * 
 * @author Peerawit Praphanwittaya
 *
 */
@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	JdbcMemberDAO jdbcMemberDao;

	/**
	 * Insert Member to database
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param phone
	 * @param birth
	 * @param fname
	 * @param lname
	 * @param nickname
	 * @return Number of row insert
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public Row insertMember(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "birth", required = true) String birth,
			@RequestParam(value = "fname", required = true) String fname,
			@RequestParam(value = "lname", required = true) String lname,
			@RequestParam(value = "nickname", required = false) String nickname) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		Member member = new Member(username, hashedPassword, email, phone,
				fname, lname, nickname, birth, "ROLE_USER", true);
		return new Row(jdbcMemberDao.insert(member));
	}

	/**
	 * Update Member in database
	 * 
	 * @param email
	 * @param phone
	 * @param fname
	 * @param lname
	 * @param birth
	 * @param nickname
	 * @return Number of row update
	 */

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row update(
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "phone", required = true) String phone,
			@RequestParam(value = "fname", required = true) String fname,
			@RequestParam(value = "lname", required = true) String lname,
			@RequestParam(value = "birth", required = true) String birth,
			@RequestParam(value = "nickname", required = false) String nickname) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return new Row(-1);
		}
		Member member = new Member(userDetails.getUsername(), email, phone,
				fname, lname, birth, nickname);
		return new Row(jdbcMemberDao.update(member));
	}

	/**
	 * Delete member form database (login first)
	 * @return Number of row delete
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row delete() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return new Row(-1);
		}
		securityContext.setAuthentication(null);
		return new Row(jdbcMemberDao.delete(userDetails.getUsername()));
	}

	/**
	 * List member form database (ROLE_ADMIN)
	 * @return List of members
	 */

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_ADMIN")
	public List<Member> list() {
		List<Member> members = jdbcMemberDao.list();
		for (Member m : members) {
			m.setPassword("");
		}
		return members;
	}

	/**
	 * Find user detail by username (login first)
	 * @return member detail
	 */

	@RequestMapping(value = "/findfromuser", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_USER")
	public Member findFormUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return null;
		}
		Member members = jdbcMemberDao.findFromUsername(userDetails
				.getUsername());
		members.setPassword("");
		return members;
	}
}
