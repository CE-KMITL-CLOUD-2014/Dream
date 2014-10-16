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

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletResponse response = (HttpServletResponse) resp;

		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods",
				"POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "3600");
		response.setHeader("Access-Control-Allow-Headers", "x-requested-with");

		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}

}
