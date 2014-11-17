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
import com.dream.model.Finance;
import com.dream.util.FinanceHealth;

@RestController
@RequestMapping("/financehealth")
public class FinanceHealthController {
	FinanceHealth financeHealth = new FinanceHealth();
	@Autowired
	JdbcFinanceDAO jdbcFinanceDAO;

	@RequestMapping(value = "/getliquidity", method = RequestMethod.GET)
	@ResponseBody
	public double getLiquidity(
			@RequestParam(value = "assets", required = false) String assets,
			@RequestParam(value = "expenses", required = false) String expenses) {
		if (assets != null && expenses != null) {
			return financeHealth.liquidity(Double.parseDouble(assets),
					Double.parseDouble(expenses));
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
					List<Finance> finances = jdbcFinanceDAO.list(userDetails.getUsername());
					double assetsValue = 0;
					double expensesValue = 0;
					Timestamp start, end;
					int month;
					if (finances.size() > 0) {
						start = finances.get(0).getDateTime();
						end = finances.get(finances.size() - 1).getDateTime();
						month = (end.getMonth() - start.getMonth()) + 1;
						for (Finance finance : finances) {
							if (finance.getType() == 0) {
								assetsValue += finance.getAmount();
							} else {
								expensesValue += finance.getAmount();
							}
						}
						assetsValue = assetsValue - expensesValue;
						expensesValue = expensesValue / month;
						return financeHealth.liquidity(assetsValue,
								expensesValue);
					}else{
						return 0;
					}
				} else {
					return 0;
				}

			} else {
				return 0;
			}
		}

	}

	@RequestMapping(value = "/debtratio", method = RequestMethod.GET)
	@ResponseBody
	public double debtRatio(
			@RequestParam(value = "debt", required = true) double debt,
			@RequestParam(value = "totalAsset", required = true) double totalAsset) {
		return financeHealth.debtRatio(debt, totalAsset);
	}

	@RequestMapping(value = "/savingratio", method = RequestMethod.GET)
	@ResponseBody
	public double savingRation(
			@RequestParam(value = "savingPerYear", required = true) double savingPerYear,
			@RequestParam(value = "incomePerYear", required = true) double incomePerYear) {
		return financeHealth.savingRation(savingPerYear, incomePerYear);
	}
}
