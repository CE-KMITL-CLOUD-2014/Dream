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

/**
 * Controller for Planing Service
 * 
 * @author Peerawit Praphanwittaya
 *
 */
@RestController
@RequestMapping("/planing")
public class PlaningController {
	@Autowired
	JdbcPlaningDAO jdbcPlaningDao;
	@Autowired
	JdbcFinanceDAO jdbcFinanceDAO;

	/**
	 * Insert saving in to database (login first)
	 * 
	 * @param goal
	 *            saving goal
	 * @param start_amount
	 * @param end_time
	 * @param description
	 * @return Number of insert row
	 */
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

	/**
	 * List saving from user (login first)
	 * 
	 * @return List of saving
	 */

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

	/**
	 * Delete saving by saveID (login first)
	 * 
	 * @param saveid
	 * @return Number of delete row
	 */

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

	/**
	 * Update saving by saveID
	 * 
	 * @param saveid
	 * @param goal
	 * @param start_amount
	 * @param end_time
	 * @param description
	 * @return Number of update row
	 */

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

	/**
	 * Insert event in to database
	 * 
	 * @param end_time
	 * @param description
	 * @return Number of insert row
	 */

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

	/**
	 * List event from user (login first)
	 * 
	 * @return List of events
	 */

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

	/**
	 * Delete event by saveID (login first)
	 * 
	 * @param eventid
	 * @return Number of delete row
	 */

	@RequestMapping(value = "/event/delete/{eventid}", method = RequestMethod.DELETE)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row deleteEvent(@PathVariable("eventid") String eventid) {
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
		return new Row(jdbcPlaningDao.deleteEvent(Integer.parseInt(eventid),
				userDetails.getUsername()));

	}

	/**
	 * Update event by eventID
	 * 
	 * @param eventid
	 * @param end_time
	 * @param description
	 * @return Number of update row
	 */

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

	/**
	 * Insert budget in to database
	 * 
	 * @param type_description
	 *            finance type description
	 * @param startTime
	 * @param endTime
	 * @param goal
	 * @return Number of insert row
	 */

	@RequestMapping(value = "/budget/insert", method = RequestMethod.POST)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row insertBudget(
			@RequestParam(value = "type_description", required = true) String type_description,
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

		FinanceType financeType = jdbcFinanceDAO
				.findFinanceType(type_description);

		Budget budget = new Budget(financeType.getId(), startTime, endTime,
				Double.parseDouble(goal), userDetails.getUsername());
		return new Row(jdbcPlaningDao.insertBudget(budget));
	}

	/**
	 * List budget from user (login first)
	 * 
	 * @return List of budgets
	 */

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

	/**
	 * Delete budget by budgetID (login first)
	 * 
	 * @param budgetid
	 * @return Number of delete row
	 */

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

	/**
	 * Update budget by budgetID
	 * 
	 * @param budgetid
	 * @param type_description
	 *            finance type description
	 * @param startTime
	 * @param endTime
	 * @param goal
	 * @return Number of update row
	 */

	@RequestMapping(value = "/budget/edit/{budgetid}", method = RequestMethod.PUT)
	@ResponseBody
	@Secured("ROLE_USER")
	public Row editBudget(
			@PathVariable("budgetid") String budgetid,
			@RequestParam(value = "type_description", required = true) String type_description,
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

		FinanceType financeType = jdbcFinanceDAO
				.findFinanceType(type_description);
		Budget budget = new Budget(Integer.parseInt(budgetid),
				financeType.getId(), startTime, endTime,
				Double.parseDouble(goal), userDetails.getUsername());
		return new Row(jdbcPlaningDao.updateBudget(budget));
	}
}
