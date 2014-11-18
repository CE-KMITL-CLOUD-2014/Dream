/* Copyright Peerawit Praphanwittaya & Apirat Puttaraksa                    * 
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

package com.dream.dao;

import java.sql.Timestamp;
import java.util.List;

import com.dream.model.Finance;
/**
 * DAO interface for JDBC
 * @author Peerawit Praphanwittaya
 *
 */
public interface FinanceDAO {
	public int insert(Finance finance);

	public int update(Finance finance);

	public List<Finance> list(String username);

	public List<Finance> listFromDateToDate(String username, Timestamp start,
			Timestamp end);

	public int delete(String username, Timestamp dateTime);
}
