<<<<<<< Updated upstream
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

=======
>>>>>>> Stashed changes
package com.dream.controller;

import java.sql.Timestamp;
import java.util.Calendar;

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

@RestController
@RequestMapping("/finance")
public class FinanceController {
	@Autowired
	JdbcFinanceDAO	jdbcFinanceDAO;

	@RequestMapping(value = "/insert", method = RequestMethod.GET)
	@ResponseBody
	public int financeInsert(
			@RequestParam(value = "financeId", required = true) int financeId,
			@RequestParam(value = "amount", required = true) double amount,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "saveId", required = true) int saveId,
			@RequestParam(value = "budgetId", required = true) int budgetId) {
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
		Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTime()
				.getTime());
		Finance finance = new Finance(financeId, amount, description,
				timeStamp, userDetails.getUsername(), budgetId, saveId);
		return jdbcFinanceDAO.insert(finance);
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	@ResponseBody
	public int financeUpdate(
			@RequestParam(value = "financeId", required = true) int financeId,
			@RequestParam(value = "amount", required = true) double amount,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "saveId", required = true) int saveId,
			@RequestParam(value = "budgetId", required = true) int budgetId,
			@RequestParam(value = "dateTime", required = true) String tempTimeStamp) {

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
		Timestamp timeStamp = null; // = tempTimeStamp wait format from
									// front-end

		Finance finance = new Finance(financeId, amount, description,
				timeStamp, userDetails.getUsername(), budgetId, saveId);
		return jdbcFinanceDAO.insert(finance);
	}
}
