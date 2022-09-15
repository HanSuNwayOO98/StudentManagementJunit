package com.sms;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sms.dao.UserRepository;
import com.sms.dao.UserService;
import com.sms.dto.User;


@SpringBootTest
@AutoConfigureMockMvc
public class TestAuthenticationController {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserRepository repo;

	@MockBean
	UserService userService;
	
	@Test
	public void testdisplayRoot() throws Exception {
		this.mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void testdisplayLogin() throws Exception {
		this.mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	}
	
	@Test
	public void testloginValidated() throws Exception {
		this.mockMvc.perform(post("/login")
		.param("id", "")
		.param("password", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"))
		.andExpect(model().attributeExists("error"));
	}
	
	@Test
	public void testloginOk() throws Exception {
		Optional<User> user=Optional.of(new User());
		user.get().setId("USR001");
		user.get().setName("hsno");
		user.get().setEmail("a@gmail.com");
		user.get().setPassword("123");
		user.get().setRole("1");
		when(userService.getUserbyId("USR001")).thenReturn(user);
		this.mockMvc.perform(post("/login").param("id","USR001").param("password", "123"))
		.andExpect(status().isOk())
		.andExpect(view().name("LGN001"));
	
	}
	

	@Test
	public void testlogout() throws Exception {
		this.mockMvc.perform(get("/logout"));
	}

}
