package com.dream.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
public class PlaningControllerTest {
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
		userDetails = userDetailsService.loadUserByUsername("fortest2");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				"fortest2");
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Test
	public void can_insert_list_update_delete_savingplan_correctly()
			throws Exception {
		mockMvc.perform(
				post("/planing/saving/insert").param("goal", "10000")
						.param("start_amount", "0")
						.param("description", "bike"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$rowUpdate", is(1)));
		MvcResult result = mockMvc
				.perform(get("/planing/saving/list"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.[0].description", is("bike")))
				.andExpect(jsonPath("$", hasSize(1))).andReturn();
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
		long id = (long) jsonObject.get("saveID");
		mockMvc.perform(
				put("/planing/saving/edit/" + id).param("goal", "10000")
						.param("start_amount", "0")
						.param("description", "eiei"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$rowUpdate", is(1)));
		mockMvc.perform(get("/planing/saving/list"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.[0].description", is("eiei")))
				.andExpect(jsonPath("$", hasSize(1))).andReturn();
		mockMvc.perform(delete("/planing/saving/delete/" + id)).andExpect(
				status().isOk());
	}

	@Test
	public void can_insert_list_update_delete_Event_correctly()
			throws Exception {
		mockMvc.perform(
				post("/planing/event/insert").param("description", "beach"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$rowUpdate", is(1)));
		MvcResult result = mockMvc
				.perform(get("/planing/event/list"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$.[0].description", is("beach")))
				.andReturn();
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
		long id = (long) jsonObject.get("eventID");
		mockMvc.perform(
				put("/planing/event/edit/" + id).param("description", "hike"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$rowUpdate", is(1)));
		mockMvc.perform(get("/planing/event/list"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$.[0].description", is("hike")))
				.andReturn();
		mockMvc.perform(delete("/planing/event/delete/" + id)).andExpect(
				status().isOk());
	}

	@Test
	public void can_insert_list_update_delete_budget_correctly()
			throws Exception {
		mockMvc.perform(
				post("/planing/budget/insert")
						.param("type_description", "Food")
						.param("startTime", "2014-10-01")
						.param("endTime", "2014-11-30").param("goal", "1000"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$rowUpdate", is(1)));
		MvcResult result = mockMvc
				.perform(get("/planing/budget/list"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$.[0].type_id", is(14)))
				.andReturn();
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
		long id = (long) jsonObject.get("budgetId");
		mockMvc.perform(
				put("/planing/budget/edit/" + id).param("type_description", "Travel")
				.param("startTime", "2014-10-01")
				.param("endTime", "2014-11-30").param("goal", "1000"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$rowUpdate", is(1)));
		mockMvc.perform(get("/planing/budget/list"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$.[0].type_id", is(15)))
				.andReturn();
		mockMvc.perform(delete("/planing/budget/delete/" + id)).andExpect(
				status().isOk());
	}

	@After
	public void clear() {
		SecurityContextHolder.clearContext();
	}

}
