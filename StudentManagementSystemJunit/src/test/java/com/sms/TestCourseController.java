package com.sms;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.sms.dao.CourseRepository;
import com.sms.dao.CourseService;
import com.sms.dto.Course;

@SpringBootTest
@AutoConfigureMockMvc
public class TestCourseController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CourseService courseService;
	
	@MockBean
	CourseRepository repo;
	
	@Test
	public void TestdisplayCourseRegisterUserNull() throws Exception{
		this.mockMvc.perform(get("/courseRegister"))
		.andExpect(redirectedUrl("/"));
	}

	
	@Test
	public void TestCourseRegisterValidate() throws Exception{
		this.mockMvc.perform(post("/courseRegister")
		.param("id","COU001"))	
		.andExpect(view().name("BUD003"));
	}
	
	@Test
	public void TestisCourseExist() throws Exception{
		Course course = new Course();
		course.setName("Java");
		course.setId("COU001");	
		this.mockMvc.perform(post("/courseRegister").flashAttr("course", course)
		.param("id","COU001"))	
		.andExpect(view().name("BUD003"));
	}
	
	@Test
	public void TestCourseRegisterOkay() throws Exception{
		Course course = new Course();
		course.setName("Java");
		course.setId("COU001");
		this.mockMvc.perform(post("/courseRegister").flashAttr("course", course)
		.param("id","COU001"))	
		.andExpect(view().name("BUD003"));
	}
	
	

}
