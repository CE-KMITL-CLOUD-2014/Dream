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
package com.dream.test;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dream.dao.impl.JdbcMemberDAO;

@RestController
@RequestMapping("/greeting")
public class GreetingController {
	@Autowired
	JdbcMemberDAO				jdbcMemberDao;
	private static final String	template	= "Hello, %s!";
	private final AtomicLong	counter		= new AtomicLong();

	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public Greeting greeting(
			@RequestParam(value = "name", required = false, defaultValue = "World") String name)
			throws SQLException {
		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}
}
