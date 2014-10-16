package com.dream.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.calc.DebtCalc;
import com.dream.model.Debt;

@RestController
@RequestMapping("/debt")
public class DebtController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public DebtCalc debtCal(
			@RequestParam(value = "moneyLoan", required = true) double moneyLoan,
			@RequestParam(value = "debtRate", required = true) double debtRate,
			@RequestParam(value = "time", required = true) double time) {
		Debt debt = new Debt(moneyLoan, debtRate, time);
		int ret = (int) debt.calculate();
		ret = ((ret + 99) / 100) * 100;
		return new DebtCalc(ret);
	}
}
