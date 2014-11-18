package com.dream.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "**/DAOConfig.xml",
		"**/webSecurityConfig.xml",
		"file:src/main/webapp/WEB-INF/rest-servlet.xml" })
@WebAppConfiguration
public class FinanceControllerTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	static JdbcDaoImpl userDetailsService = null;
	private MockMvc mockMvc;
	private Authentication authToken;
	UserDetails userDetails;

	@Before
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.build();
		userDetailsService = webApplicationContext.getBean(JdbcDaoImpl.class);
		userDetails = userDetailsService.loadUserByUsername("fortest");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				"fortest");
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Test
	public void can_insert_update_list_delete_finance_correctly() throws Exception {
		// InsertFinance
		mockMvc.perform(
				post("/finance/insert").param("finance", "Stock")
						.param("amount", "1000").param("description", "test"))
				.andExpect(status().isOk());
		// ListFinance
		MvcResult result = mockMvc.perform(get("/finance/list"))
				.andExpect(status().isOk()).andReturn();
		String jsonResult = result.getResponse().getContentAsString();
		JSONParser jsonParser = new JSONParser();
		JSONArray jsonArray = null;
		JSONObject jsonObject = null;
		try {
			jsonArray = (JSONArray) jsonParser.parse(jsonResult);
			jsonObject = (JSONObject) jsonArray.get(0);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long time = (long) jsonObject.get("dateTime");
		// update
		mockMvc.perform(
				put("/finance/update").param("finance", "Stock")
						.param("amount", "1000").param("description", "test")
						.param("dateTime", String.valueOf(time))).andExpect(
				status().isOk());
		// Delete
		mockMvc.perform(
				delete("/finance/delete").param("date_time",
						String.valueOf(time))).andExpect(status().isOk());
	}

	@Test
	public void can_list_from_date_to_date() throws Exception {
		userDetailsService = webApplicationContext.getBean(JdbcDaoImpl.class);
		userDetails = userDetailsService.loadUserByUsername("fortest2");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				"fortest2");
		SecurityContextHolder.getContext().setAuthentication(authToken);
		mockMvc.perform(
				get("/finance/listdatetodate").param("start", "2014-11-1")
						.param("end", "2014-11-30")).andExpect(jsonPath("$", hasSize(4))).andExpect(status().isOk());
	}

	@After
	public void clear() {
		SecurityContextHolder.clearContext();
	}
}
