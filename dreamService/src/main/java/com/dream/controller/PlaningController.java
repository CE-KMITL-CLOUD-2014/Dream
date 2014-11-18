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

import com.dream.dao.impl.JdbcFinanceDAO;
import com.dream.dao.impl.JdbcPlaningDAO;
import com.dream.model.Budget;
import com.dream.model.Event;
import com.dream.model.FinanceType;
import com.dream.model.Row;
import com.dream.model.Saving;

@RestController
@RequestMapping("/planing")
public class PlaningController {
	@Autowired
	JdbcPlaningDAO jdbcPlaningDao;
	@Autowired
	JdbcFinanceDAO	jdbcFinanceDAO;

	@RequestMapping(value = "/saving/insert", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row insertSaving(
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
			return new Row(-1);
		}
		Saving saving = new Saving(userDetails.getUsername(),
				Integer.parseInt(goal), Integer.parseInt(start_amount),
				end_time, description);
		return new Row(jdbcPlaningDao.insertSaving(saving));
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
	public Row deleteSaving(@PathVariable("saveid") String saveid) {
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
		return new Row(jdbcPlaningDao.deleteSaving(Integer.parseInt(saveid),
				userDetails.getUsername()));

	}

	@RequestMapping(value = "/saving/edit/{saveid}", method = RequestMethod.PUT)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row editSaving(
			@PathVariable("saveid") String saveid,
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
			return new Row(-1);
		}
		Saving saving = new Saving(Integer.parseInt(saveid),
				userDetails.getUsername(), Integer.parseInt(goal),
				Integer.parseInt(start_amount), end_time, description);
		return new Row(jdbcPlaningDao.updateSaving(saving));
	}

	@RequestMapping(value = "/event/insert", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row insertEvent(
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
			return new Row(-1);
		}
		Event event = new Event(userDetails.getUsername(), description,
				end_time);
		return new Row(jdbcPlaningDao.insertEvent(event));
	}

	@RequestMapping(value = "/event/list", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_USER")
	public List<Event> listEventFromUsername() {
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
		return jdbcPlaningDao.listEventFromUsername(userDetails.getUsername());
	}

	@RequestMapping(value = "/event/delete/{eventid}", method = RequestMethod.DELETE)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row deleteEvent(@PathVariable("eventid") String saveid) {
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
		return new Row(jdbcPlaningDao.deleteEvent(Integer.parseInt(saveid),
				userDetails.getUsername()));

	}

	@RequestMapping(value = "/event/edit/{eventid}", method = RequestMethod.PUT)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row editEvent(
			@PathVariable("eventid") String eventid,
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
			return new Row(-1);
		}
		Event event = new Event(Integer.parseInt(eventid),
				userDetails.getUsername(), description, end_time);
		return new Row(jdbcPlaningDao.updateEvent(event));
	}

	@RequestMapping(value = "/budget/insert", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row insertBudget(
			@RequestParam(value = "type_id", required = true) String type_id,
			@RequestParam(value = "startTime", required = true) String startTime,
			@RequestParam(value = "endTime", required = true) String endTime,
			@RequestParam(value = "goal", required = true) String goal) {
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

		FinanceType financeType = jdbcFinanceDAO.findFinanceType(type_id);

		Budget budget = new Budget(financeType.getId(), startTime,
				endTime, Double.parseDouble(goal), userDetails.getUsername());
		return new Row(jdbcPlaningDao.insertBudget(budget));
	}

	@RequestMapping(value = "/budget/list", method = RequestMethod.GET)
	@ResponseBody
	@Secured("ROLE_USER")
	public List<Budget> listBudgetFromUsername() {
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
		return jdbcPlaningDao.listBudgetFromUsername(userDetails.getUsername());
	}

	@RequestMapping(value = "/budget/delete/{budgetid}", method = RequestMethod.DELETE)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row deleteBudget(@PathVariable("budgetid") String budgetid) {
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
		return new Row(jdbcPlaningDao.deleteBudget(Integer.parseInt(budgetid),
				userDetails.getUsername()));

	}

	@RequestMapping(value = "/budget/edit/{budgetid}", method = RequestMethod.PUT)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row editBudget(
			@PathVariable("budgetid") String budgetid,
			@RequestParam(value = "type_id", required = true) String type_id,
			@RequestParam(value = "startTime", required = true) String startTime,
			@RequestParam(value = "endTime", required = true) String endTime,
			@RequestParam(value = "goal", required = true) String goal) {
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
		Budget budget = new Budget(Integer.parseInt(budgetid),
				Integer.parseInt(type_id), startTime, endTime,
				Double.parseDouble(goal), userDetails.getUsername());
		return new Row(jdbcPlaningDao.updateBudget(budget));
	}
}
