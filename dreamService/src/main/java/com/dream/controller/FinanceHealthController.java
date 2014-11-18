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

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.dream.model.DebtRatio;
import com.dream.model.Finance;
import com.dream.model.Liquidty;
import com.dream.model.SavingRatio;
import com.dream.util.FinanceHealth;

/**
 * Controller for FinanceHealth Service
 * 
 * @author Peerawit Praphanwittaya
 *
 */
@RestController
@RequestMapping("/financehealth")
public class FinanceHealthController {
	FinanceHealth financeHealth = new FinanceHealth();
	@Autowired
	JdbcFinanceDAO jdbcFinanceDAO;

	/**
	 * Calculate liquidity if login then get data from database to calculate
	 * else insert assets and expenses/month. if assets or expenses = 0 then
	 * return 0.
	 * 
	 * @param assets
	 *            assets
	 * @param expenses
	 *            expenses/month
	 * @return result from calculate
	 */
	@RequestMapping(value = "/getliquidity", method = RequestMethod.GET)
	@ResponseBody
	public Liquidty getLiquidity(
			@RequestParam(value = "assets", required = false) String assets,
			@RequestParam(value = "expenses", required = false) String expenses) {
		if (assets != null && expenses != null) {
			return new Liquidty(financeHealth.liquidity(
					Double.parseDouble(assets), Double.parseDouble(expenses)));
		} else {
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			Authentication authentication = securityContext.getAuthentication();
			UserDetails userDetails;
			if (authentication != null) {
				Object principal = authentication.getPrincipal();
				userDetails = (UserDetails) (authentication.getPrincipal() instanceof UserDetails ? principal
						: null);
				if (userDetails != null) {
					List<Finance> finances = jdbcFinanceDAO.list(userDetails
							.getUsername());
					double assetsValue = 0;
					double expensesValue = 0;
					Timestamp start, end;
					int month;
					if (finances.size() > 0) {
						start = finances.get(0).getDateTime();
						end = finances.get(finances.size() - 1).getDateTime();
						month = (end.getMonth() - start.getMonth()) + 1;
						for (Finance finance : finances) {
							if (finance.getType() == 1) {
								assetsValue += finance.getAmount();
							} else {
								expensesValue += finance.getAmount();
							}
						}
						if (assetsValue == 0 || expensesValue == 0) {
							return new Liquidty(0);
						}
						assetsValue = assetsValue - expensesValue;
						expensesValue = expensesValue / month;
						return new Liquidty(financeHealth.liquidity(
								assetsValue, expensesValue));
					} else {
						return new Liquidty(0);
					}
				} else {
					return new Liquidty(0);
				}

			} else {
				return new Liquidty(0);
			}
		}

	}

	/**
	 * Calculate debt ratio
	 * 
	 * @param debt
	 *            total debt
	 * @param totalAsset
	 *            total asset
	 * @return debt ratio
	 */
	@RequestMapping(value = "/debtratio", method = RequestMethod.GET)
	@ResponseBody
	public DebtRatio debtRatio(
			@RequestParam(value = "debt", required = true) double debt,
			@RequestParam(value = "totalAsset", required = true) double totalAsset) {
		if (debt == 0 || totalAsset == 0) {
			return new DebtRatio(0);
		} else {
			return new DebtRatio(financeHealth.debtRatio(debt, totalAsset));
		}
	}

	/**
	 * Calculate saving ratio
	 * 
	 * @param savingPerYear
	 * @param incomePerYear
	 * @return saving ratio
	 */
	@RequestMapping(value = "/savingratio", method = RequestMethod.GET)
	@ResponseBody
	public SavingRatio savingRation(
			@RequestParam(value = "savingPerYear", required = true) double savingPerYear,
			@RequestParam(value = "incomePerYear", required = true) double incomePerYear) {
		if (savingPerYear == 0 || incomePerYear == 0) {
			return new SavingRatio(0);
		} else {
			return new SavingRatio(financeHealth.savingRation(savingPerYear,
					incomePerYear));
		}
	}
}
