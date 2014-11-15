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

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.calc.DebtCalc;
import com.dream.debt.Debt;

@RestController
@RequestMapping("/debt/cal")
public class DebtController {

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public DebtCalc debtCal(
			@RequestParam(value = "moneyLoan", required = true) double moneyLoan,
			@RequestParam(value = "debtRate", required = true) double debtRate,
			@RequestParam(value = "time", required = true) double time) {
		if (moneyLoan != 0 && debtRate != 0 && time != 0) {
			Debt debt = new Debt(moneyLoan, debtRate, time);
			double ret = debt.calculate();
			ret = Math.round(ret);
			return new DebtCalc(ret);
		}else{
			return new DebtCalc(0);
		}
	}
}
