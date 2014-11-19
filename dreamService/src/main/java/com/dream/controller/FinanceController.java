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

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.dao.impl.JdbcFinanceDAO;
import com.dream.dao.impl.JdbcPlaningDAO;
import com.dream.model.Budget;
import com.dream.model.Finance;
import com.dream.model.FinanceType;
import com.dream.model.Row;
import com.dream.model.Saving;
import com.dream.util.ParseDate;

/**
 * Controller for Finance Service
 * 
 * @author Peerawit Praphanwittaya
 *
 */
@RestController
@RequestMapping("/finance")
public class FinanceController {
	@Autowired
	JdbcFinanceDAO	jdbcFinanceDAO;
	@Autowired
	JdbcPlaningDAO	jdbcPlaningDAO;

	/**
	 * Insert finance in to Database
	 * 
	 * @param financeStr
	 *            Finance_type description
	 * @param amount
	 *            Money
	 * @param description
	 *            Description
	 * @param saveId
	 *            Saving ID
	 * @param budgetId
	 *            Budget ID
	 * @param eventId
	 *            Event ID
	 * @return Number of row insert
	 */
	@RequestMapping(value = "/insert", method = RequestMethod.POST)
	@Secured("ROLE_USER")
	@ResponseBody
	public Row financeInsert(
			@RequestParam(value = "finance", required = true) String financeStr,
			@RequestParam(value = "amount", required = true) double amount,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "saveId", required = false) Integer saveId,
			@RequestParam(value = "budgetId", required = false) Integer budgetId,
			@RequestParam(value = "eventId", required = false) Integer eventId) {
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
		Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTime()
				.getTime());
		if (saveId == null)
			saveId = 1;
		if (budgetId == null)
			budgetId = 1;
		if (eventId == null)
			eventId = 1;
		FinanceType financeType = jdbcFinanceDAO.findFinanceType(financeStr);
		Finance finance = new Finance(financeType.getId(), amount, description,
				timeStamp, userDetails.getUsername(), budgetId, saveId, eventId);
		Row numrow;
		double oldAmountSaving = 0;
		double oldAmountBudget = 0;
		Saving saving = new Saving();
		Budget budget = new Budget();
		try {

			for (Saving s : jdbcPlaningDAO.listSavingFromUsername(userDetails
					.getUsername())) {
				if (s.getEnd().getTime() > timeStamp.getTime()) {
					oldAmountSaving = s.getStartAmount();
					if (financeType.getType() == 1) {
						s.setStartAmount(amount + s.getStartAmount());
					} else if (financeType.getType() == 2) {
						s.setStartAmount(Math.abs(amount - s.getStartAmount()));
					}
					saveId = s.getSaveID();
					saving = s;
					break;
				}
			}
			for (Budget b : jdbcPlaningDAO.listBudgetFromUsername(userDetails
					.getUsername())) {
				if (b.getType_id() == financeType.getId()) {
					if (b.getEndTime().getTime() > timeStamp.getTime()) {
						oldAmountBudget = b.getAmount();
						b.setAmount(amount + b.getAmount());
						budgetId = b.getBudgetId();
						budget = b;
						break;
					}
				}
			}
			finance.setSaveId(saveId);
			finance.setBudgetId(budgetId);
			numrow = new Row(jdbcFinanceDAO.insert(finance));
			jdbcPlaningDAO.updateSaving(saving);
			jdbcPlaningDAO.updateBudget(budget);
			return numrow;
		} catch (Exception e) {
			numrow = new Row(jdbcFinanceDAO.delete(finance.getUsername(),
					finance.getDateTime()));
			saving.setStartAmount(oldAmountSaving);
			jdbcPlaningDAO.updateSaving(saving);
			budget.setAmount(oldAmountBudget);
			jdbcPlaningDAO.updateBudget(budget);
			return numrow;
		}
	}

	/**
	 * Update value in Finance
	 * 
	 * @param financeStr
	 *            Finance_type description
	 * @param amount
	 *            Money
	 * @param description
	 *            Description
	 * @param saveId
	 *            Saving ID
	 * @param budgetId
	 *            Budget ID
	 * @param eventId
	 *            Event ID
	 * @param tempTimeStamp
	 *            Timestamp when insert finance
	 * @return Number of row update
	 */

	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@Secured("ROLE_USER")
	@ResponseBody
	public Row financeUpdate(
			@RequestParam(value = "finance", required = true) String financeStr,
			@RequestParam(value = "amount", required = true) String amount,
			@RequestParam(value = "description", required = true) String description,
			@RequestParam(value = "saveId", required = false) Integer saveId,
			@RequestParam(value = "budgetId", required = false) Integer budgetId,
			@RequestParam(value = "eventId", required = false) Integer eventId,
			@RequestParam(value = "dateTime", required = true) String tempTimeStamp) {

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
		if (saveId == null)
			saveId = 1;
		if (budgetId == null)
			budgetId = 1;
		if (eventId == null)
			eventId = 1;
		double amountDouble = Double.parseDouble(amount);
		Timestamp timeStamp = new Timestamp(Long.parseLong(tempTimeStamp));
		List<Finance> financeList = jdbcFinanceDAO.list(userDetails
				.getUsername());
		Finance finance = new Finance();
		for (Finance f : financeList) {
			if (f.getDateTime().getTime() == timeStamp.getTime()) {
				finance = f;
				break;
			}
		}
		Row numrow = new Row(0);
		double oldAmountFinance = 0;
		double oldAmountSaving = 0;
		double oldAmountBudget = 0;
		Saving saving = new Saving();
		Budget budget = new Budget();
		try {
			oldAmountFinance = finance.getAmount();
			double diffValue = amountDouble - finance.getAmount();

			for (Saving s : jdbcPlaningDAO.listSavingFromUsername(userDetails
					.getUsername())) {
				if (s.getSaveID() == finance.getSaveId()) {
					if (s.getEnd().getTime() > finance.getDateTime().getTime()) {
						oldAmountSaving = s.getStartAmount();
						if (finance.getType() == 1) {
							s.setStartAmount(s.getStartAmount() + diffValue);
						} else if (finance.getType() == 2) {
							s.setStartAmount(s.getStartAmount() - diffValue);
						}
						saving = s;
						break;
					}
				}
			}
			for (Budget b : jdbcPlaningDAO.listBudgetFromUsername(userDetails
					.getUsername())) {
				if (b.getBudgetId() == finance.getBudgetId()) {
					if (b.getEndTime().getTime() > finance.getDateTime()
							.getTime()) {
						oldAmountBudget = b.getAmount();
						b.setAmount(diffValue + b.getAmount());
						budget = b;
						break;
					}
				}
			}
			finance.setAmount(amountDouble);
			numrow = new Row(jdbcFinanceDAO.update(finance));
			jdbcPlaningDAO.updateSaving(saving);
			jdbcPlaningDAO.updateBudget(budget);
			return numrow;
		} catch (Exception e) {
			finance.setAmount(oldAmountFinance);
			numrow = new Row(jdbcFinanceDAO.update(finance));
			saving.setStartAmount(oldAmountSaving);
			jdbcPlaningDAO.updateSaving(saving);
			budget.setAmount(oldAmountBudget);
			jdbcPlaningDAO.updateBudget(budget);
			return numrow;
		}
	}


	/**
	 * List finances by username
	 * 
	 * @return List of finances
	 */

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@Secured("ROLE_USER")
	@ResponseBody
	public List<Finance> financeByUser() {
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
		return jdbcFinanceDAO.list(userDetails.getUsername());
	}

	/**
	 * Delete finance by username and timestamp (login first)
	 * 
	 * @param date_time
	 *            Timestamp
	 * @return Number of row delete
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@Secured("ROLE_USER")
	@ResponseBody
	public Row deleteFinance(
			@RequestParam(value = "date_time", required = true) String date_time) {
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

		Timestamp timeStamp = new Timestamp(Long.parseLong(date_time));

		List<Finance> financeList = jdbcFinanceDAO.list(userDetails
				.getUsername());
		Finance finance = new Finance();
		for (Finance f : financeList) {
			if (f.getDateTime().getTime() == timeStamp.getTime()) {
				finance = f;
				break;
			}
		}
		Row numrow = new Row(0);
		double oldAmountFinance = 0;
		double oldAmountSaving = 0;
		double oldAmountBudget = 0;
		Saving saving = new Saving();
		Budget budget = new Budget();
		try {
			for (Saving s : jdbcPlaningDAO.listSavingFromUsername(userDetails
					.getUsername())) {
				if (s.getSaveID() == finance.getSaveId()) {
					if (s.getEnd().getTime() > finance.getDateTime().getTime()) {
						oldAmountSaving = s.getStartAmount();
						if (finance.getType() == 1) {
							s.setStartAmount(s.getStartAmount()
									- finance.getAmount());
						} else if (finance.getType() == 2) {
							s.setStartAmount(s.getStartAmount()
									+ finance.getAmount());
						}
						saving = s;
						break;
					}
				}
			}
			for (Budget b : jdbcPlaningDAO.listBudgetFromUsername(userDetails
					.getUsername())) {
				if (b.getBudgetId() == finance.getBudgetId()) {
					if (b.getEndTime().getTime() > finance.getDateTime()
							.getTime()) {
						oldAmountBudget = b.getAmount();
						b.setAmount(b.getAmount() - finance.getAmount());
						budget = b;
						break;
					}
				}
			}
			numrow = new Row(jdbcFinanceDAO.delete(finance.getUsername(),
					finance.getDateTime()));
			jdbcPlaningDAO.updateSaving(saving);
			jdbcPlaningDAO.updateBudget(budget);
			return numrow;
		} catch (Exception e) {
			finance.setAmount(oldAmountFinance);
			numrow = new Row(jdbcFinanceDAO.update(finance));
			saving.setStartAmount(oldAmountSaving);
			jdbcPlaningDAO.updateSaving(saving);
			budget.setAmount(oldAmountBudget);
			jdbcPlaningDAO.updateBudget(budget);
			return numrow;
		}
	}

	/**
	 * List finance from date to date
	 * 
	 * @param start
	 *            Start date
	 * @param end
	 *            End date
	 * @return List of finances
	 */
	@RequestMapping(value = "/listdatetodate", method = RequestMethod.GET)
	@Secured("ROLE_USER")
	@ResponseBody
	public List<Finance> listDatetoDate(
			@RequestParam(value = "start", required = true) String start,
			@RequestParam(value = "end", required = true) String end) {
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
		Date start_date = ParseDate.getCurrentDate(start);
		Date end_date = ParseDate.getCurrentDate(end);
		Timestamp startT = new Timestamp(start_date.getTime());
		Timestamp endT = new Timestamp(end_date.getTime());

		List<Finance> finance = jdbcFinanceDAO.listFromDateToDate(
				userDetails.getUsername(), startT, endT);

		return finance;
	}
}
