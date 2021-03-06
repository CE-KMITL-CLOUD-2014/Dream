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

package com.dream.dao;

import java.util.List;

import com.dream.model.Member;

/**
 * DAO interface for Member JDBC
 * 
 * @author Peerawit Praphanwittaya
 *
 */
public interface MemberDAO {
	public int insert(Member member);

	public int update(Member member);

	public Member findFromUsername(String username);

	public List<Member> list();

	public int delete(String username);
}
