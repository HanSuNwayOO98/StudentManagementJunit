package com.sms;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.sms.dao.UserRepository;
import com.sms.dao.UserService;
import com.sms.model.UserBean;



@SpringBootTest
@AutoConfigureMockMvc
public class TestUserController {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	UserService userService;
	
	@MockBean
	UserRepository repo;
	
	
	@Test
	public void testdisplayUserRegister() throws Exception {
		this.mockMvc.perform(get("/userRegister"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("userBean"));
	}
	
	@Test
	public void testuserRegistervalidate() throws Exception {
		this.mockMvc.perform(post("/userRegister"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));	
	}
	
	@Test
	public void testuserRegisterok() throws Exception {
		UserBean userBean=new UserBean();
		userBean.setId("USR001");
		userBean.setEmail("a@gmail.com");
		userBean.setName("Han Su Nway Oo");
		userBean.setPassword("123");
		userBean.setRole("1");
		this.mockMvc.perform(post("/userRegister").flashAttr("userBean", userBean));
		
	}
	
	@Test
	public void testdisplayUserNull() throws Exception {
		this.mockMvc.perform(get("/displayUser"));
	}
	
	@Test
	public void testdisplayUser() throws Exception {
		UserBean user = new UserBean();
		user.setId("USR001");
		user.setName("Han Su Nway Oo");
		user.setEmail("a@gmail.com");
		user.setPassword("123");
		this.mockMvc.perform(get("/displayUser")
		.sessionAttr("user", user))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("userList"));
	}
	
	@Test
	public void TestuserSearchNull() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "").param("name", ""))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/displayUser"));
	}

	@Test
	public void TestuserSearch() throws Exception {
		this.mockMvc.perform(get("/userSearch").param("id", "USR001").param("name", "pika"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
	}
	
	
	@Test
	public void testuserDelete() throws Exception {
		this.mockMvc.perform(get("/userDelete").param("id","USR001"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/displayUser"));	
	}
	@Test
	public void testuserUpdatevalidate() throws Exception {
		this.mockMvc.perform(post("/userUpdate"));
	}
	@Test
	public void testuserUpdateok() throws Exception {
		UserBean userBean=new UserBean();
		userBean.setId("USR001");
		userBean.setEmail("a@gmail.com");
		userBean.setName("Han Su Nway Oo");
		userBean.setPassword("123");
		userBean.setRole("1");
		this.mockMvc.perform(post("/userUpdate").flashAttr("userBean", userBean));
		
		
	}
	
	
	
	
	

}
