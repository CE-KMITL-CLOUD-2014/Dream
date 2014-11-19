package com.dream.controller;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "**/DAOConfig.xml",
		"**/webSecurityConfig.xml",
		"file:src/main/webapp/WEB-INF/rest-servlet.xml" })
@WebAppConfiguration
public class MemberControllerTest {
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
		userDetails = userDetailsService.loadUserByUsername("admin");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				"admin");
		SecurityContextHolder.getContext().setAuthentication(authToken);
	}

	@Test
	public void testList() throws Exception {
		userDetailsService = webApplicationContext.getBean(JdbcDaoImpl.class);
		userDetails = userDetailsService.loadUserByUsername("admin2");
		authToken = new UsernamePasswordAuthenticationToken(userDetails,
				"admin2");
		SecurityContextHolder.getContext().setAuthentication(authToken);
		mockMvc.perform(get("/member/list")).andExpect(status().isOk());
	}

	@Test
	public void can_find_user_after_login() throws Exception {
		mockMvc.perform(get("/member/findfromuser"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username", is("admin")))
				.andExpect(jsonPath("$.email", is("admin@hotmail.com")))
				.andExpect(jsonPath("$.phone", is("0881234567")))
				.andExpect(jsonPath("$.fname", is("admin")))
				.andExpect(jsonPath("$.lname", is("admin")))
				.andExpect(jsonPath("$.birth", is("1992-11-01")))
				.andExpect(jsonPath("$.nickname", is("admin")));
	}

	@Test
	public void can_add_user_and_delete_user() throws Exception {
		mockMvc.perform(
				post("/member/insert").param("username", "fortest0")
						.param("password", "fortest0")
						.param("email", "test@test.com")
						.param("phone", "1234567890").param("fname", "test")
						.param("lname", "test").param("nickname", "test")
						.param("birth", "1992-12-25")).andExpect(
				status().isOk());
		userDetails = userDetailsService.loadUserByUsername("fortest0");
		authToken = new UsernamePasswordAuthenticationToken(userDetails, "fortest0");
		SecurityContextHolder.getContext().setAuthentication(authToken);
		mockMvc.perform(delete("/member/delete")).andExpect(status().isOk());
	}

	@Test
	public void can_update_user_detail() throws Exception {
		mockMvc.perform(
				post("/member/insert").param("username", "test12")
						.param("password", "test")
						.param("email", "test@test.com")
						.param("phone", "1234567890").param("fname", "test")
						.param("lname", "test").param("nickname", "test")
						.param("birth", "1992-12-25")).andExpect(
				status().isOk());
		userDetails = userDetailsService.loadUserByUsername("test12");
		authToken = new UsernamePasswordAuthenticationToken(userDetails, "test");
		SecurityContextHolder.getContext().setAuthentication(authToken);
		mockMvc.perform(
				put("/member/update").param("email", "1234@test.com")
						.param("phone", "1234567890").param("fname", "test")
						.param("lname", "test").param("nickname", "test")
						.param("birth", "1992-12-25")).andExpect(
				status().isOk());
		mockMvc.perform(get("/member/findfromuser"))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(TestUtil.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$.username", is("test12")))
				.andExpect(jsonPath("$.email", is("1234@test.com")))
				.andExpect(jsonPath("$.phone", is("1234567890")))
				.andExpect(jsonPath("$.fname", is("test")))
				.andExpect(jsonPath("$.lname", is("test")))
				.andExpect(jsonPath("$.birth", is("1992-12-25")))
				.andExpect(jsonPath("$.nickname", is("test")));
		mockMvc.perform(delete("/member/delete")).andExpect(status().isOk());
	}

	@After
	public void clear() {
		SecurityContextHolder.clearContext();
	}
}
