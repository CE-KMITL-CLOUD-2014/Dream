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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.dao.impl.JdbcPlaningDAO;
import com.dream.model.Saving;

@RestController
@RequestMapping("/planing")
public class PlaningController {
	@Autowired
	JdbcPlaningDAO jdbcPlaningDao;

	@RequestMapping(value = "/saving/insert", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public int insertSaving(
			@RequestParam(value = "goal", required = true) String goal,
			@RequestParam(value = "start_amount", required = true) String start_amount,
			@RequestParam(value = "end_time", required = false) String end_time,
			@RequestParam(value = "description", required = true) String description) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return 0;
		}
		Saving saving = new Saving(userDetails.getUsername(),
				Integer.parseInt(goal), Integer.parseInt(start_amount),
				end_time, description);
		return jdbcPlaningDao.insertSaving(saving);
	}

	@RequestMapping(value = "/saving/list", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_USER")
	public List<Saving> listSavingFromUsername() {
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
		return jdbcPlaningDao.listSavingFromUsername(userDetails.getUsername());
	}

	@RequestMapping(value = "/saving/delete/{saveid}", method = RequestMethod.DELETE)
	@ResponseBody
	@Secured("ROLE_USER")
	public int deleteSaving(@PathVariable("saveid") String saveid) {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return 0;
		}
		return jdbcPlaningDao.deleteSaving(Integer.parseInt(saveid),
				userDetails.getUsername());

	}

	@RequestMapping(value = "/saving/edit/{saveid}", method = RequestMethod.PUT)
	@ResponseBody
	@Secured("ROLE_USER")
	public int editSaving(
			@PathVariable("saveid") String saveid,
			@RequestParam(value = "goal", required = true) String goal,
			@RequestParam(value = "start_amount", required = true) String start_amount,
			@RequestParam(value = "end_time", required = false) String end_time,
			@RequestParam(value = "description", required = true) String description) {
		System.out.println(saveid);
		System.out.println(goal);
		System.out.println(start_amount);
		System.out.println(end_time);
		System.out.println(description);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();
		UserDetails userDetails;
		if (authentication != null) {
			Object principal = authentication.getPrincipal();
			userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
					: null);
		} else {
			return 0;
		}
		Saving saving = new Saving(Integer.parseInt(saveid),
				userDetails.getUsername(), Integer.parseInt(goal),
				Integer.parseInt(start_amount), end_time, description);
		return jdbcPlaningDao.updateSaving(saving);
	}

}
