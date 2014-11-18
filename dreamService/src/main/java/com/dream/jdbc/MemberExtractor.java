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

package com.dream.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.dream.model.Member;

/**
 * For map Member data from database to Member Object
 * 
 * @author shadowslight
 *
 */
public class MemberExtractor implements ResultSetExtractor<Member> {

	@Override
	public Member extractData(ResultSet resultSet) throws SQLException,
			DataAccessException {
		Member member = new Member(resultSet.getString(1),
				resultSet.getString(2), resultSet.getString(3),
				resultSet.getString(4), resultSet.getString(5),
				resultSet.getString(6), resultSet.getString(7),
				resultSet.getString(8), resultSet.getBoolean(9),
				resultSet.getString(12), resultSet.getTimestamp(10));
		return member;
	}

}
