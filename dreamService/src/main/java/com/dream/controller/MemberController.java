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

@RestController
@RequestMapping("/member")
public class MemberController {
	@Autowired
	JdbcMemberDAO jdbcMemberDao;

	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@ResponseBody
	public int insertMember(
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
				fname, lname, nickname, birth,"ROLE_USER", true);
		return jdbcMemberDao.insert(member);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public int update(
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
			return -1;
		}
		Member member = new Member(userDetails.getUsername(), email, phone,
				fname, lname, birth, nickname);
		return jdbcMemberDao.update(member);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public int delete() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return -1;
		}
		return jdbcMemberDao.delete(userDetails.getUsername());
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	// @Secured("ROLE_ADMIN")
	public List<Member> list() {
		List<Member> members = jdbcMemberDao.list();
		for (Member m : members) {
			m.setPassword("");
		}
		return members;
	}

	@RequestMapping(value = "/findformuser", method = RequestMethod.POST)
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
