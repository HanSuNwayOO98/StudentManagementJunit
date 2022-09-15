package com.sms;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import com.sms.dao.StudentRepository;
import com.sms.dao.StudentService;
import com.sms.dto.Course;
import com.sms.dto.Student;
import com.sms.model.StudentBean;
import com.sms.model.UserBean;



@SpringBootTest
@AutoConfigureMockMvc
public class TestStudentController {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	StudentService studentService;
	
	
	@MockBean
	StudentRepository repo;
	
	@Test
	public void testshowStudentRegister() throws Exception {
		this.mockMvc.perform(get("/studentRegister"))
		.andExpect(redirectedUrl("/"));
	}
	
	
	@Test
	public void TeststudentRegisterValidate() throws Exception {
		this.mockMvc.perform(post("/studentRegister")
		.param("id","STU001"))
		.andExpect(view().name("STU001"));
	}
	
	@Test
	public void teststudentRegisterok() throws Exception {
		StudentBean studentBean=new StudentBean();
		studentBean.setId("STU001");
		studentBean.setName("hsno");
		studentBean.setDob("2022-09-21");
		studentBean.setGender("female");
		studentBean.setEducation("1");
		studentBean.setPhone("09876543");
		
		ArrayList<Course> course = new ArrayList<>();
		Course c1=new Course();
		c1.setId("COU001");
		c1.setName("Java");
		Course c2=new Course();
		c2.setId("COU002");
		c2.setName("Html");
		course.add(c1);course.add(c2);
		this.mockMvc.perform(post("/studentRegister").flashAttr("studentBean", studentBean)
		.param("id","STU001"));
	 
	}
	
	@Test
	public void testdisplayStudentUserNull() throws Exception{
		this.mockMvc.perform(get("/displayStudent"))
		.andExpect(redirectedUrl("/"));
	}
	@Test
	public void testdisplayAllStudent() throws Exception{
		List<StudentBean> list = new ArrayList<StudentBean>();
	
		this.mockMvc.perform(get("/displayStudent").sessionAttr("user", new UserBean()))
		.andExpect(view().name("STU003"));
	}
	
	
	@Test
	public void testdeleteStudent() throws Exception {
		this.mockMvc.perform(get("/studentDelete").param("id","STU001"))
		.andExpect(redirectedUrl("/displayStudent"));;
	}
	
	
	@Test
	public void testsearchStudentNull() throws Exception{
		this.mockMvc.perform(get("/searchStudent")
		.param("id", "").param("name", "").param("course", ""))
		.andExpect(redirectedUrl("/displayStudent"));
	}
	
	@Test
	public void testsearchStudent() throws Exception{
		
		this.mockMvc.perform(get("/searchStudent")
		.param("id", "STU001").param("name", "").param("course", ""))
		.andExpect(view().name("STU003"));
	}
	
	@Test
	public void testseeMore() throws Exception {
		Student stu=new Student();
		stu.setId("STU001");
		stu.setName("hsno");
		stu.setDob("2022-09-21");
		stu.setGender("Female");
		stu.setPhone("09876543");
		stu.setEducation("1");
		
		
		ArrayList<Course> course = new ArrayList<>();
		Course c1=new Course();
		c1.setId("COU001");
		c1.setName("Java");
		Course c2=new Course();
		c2.setId("COU002");
		c2.setName("Html");
		course.add(c1);course.add(c2);
		stu.setCourses(course);	
		when(studentService.getById("STU001")).thenReturn(stu);
		ArrayList<Course> list = new ArrayList<>();
		for(Course c : course) {
			list.add(c);
		}
		this.mockMvc.perform(get("/seeMore/STU001"))
		.andExpect(view().name("STU002"))
		.andExpect(model().attributeExists("studentBean"));
	}
	
	
	@Test
	public void testshowStudentUpdatevalidate() throws Exception {
		this.mockMvc.perform(post("/studentUpdate"));
	}
	
	
	@Test
	public void testupdateStudentok() throws Exception {
		StudentBean studuentBean=new StudentBean();
		studuentBean.setId("STU001");
		studuentBean.setName("hsno");
		studuentBean.setDob("2022-09-21");
		studuentBean.setGender("female");
		studuentBean.setEducation("1");
		studuentBean.setPhone("09876543");
		
		ArrayList<Course> course = new ArrayList<>();
		Course c1=new Course();
		c1.setId("COU001");
		c1.setName("Java");
		Course c2=new Course();
		c2.setId("COU002");
		c2.setName("Html");
		course.add(c1);course.add(c2);
		this.mockMvc.perform(post("/studentUpdate").flashAttr("studentBean", studuentBean));
	}
	
	

}
