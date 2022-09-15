package com.sms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.sms.dao.CourseRepository;
import com.sms.dao.CourseService;
import com.sms.dto.Course;



@SpringBootTest
public class TestCourseService {
	@Mock
	CourseRepository repo;
	
	@InjectMocks
	CourseService courseService;
	
	@Test
	public void selectAllCourseTest() {
		List<Course> list=new ArrayList<Course>();
		Course c1=new Course();
		c1.setId("COU001");
		c1.setName("Java");
		
		Course c2=new Course();
		c2.setId("COU002");
		c2.setName("Html");
		
		Course c3=new Course();
		c3.setId("COU003");
		c3.setName("OJT");
		
		Course c4=new Course();
		c4.setId("COU004");
		c4.setName("CSS");
		
		list.add(c1);
		list.add(c2);
		list.add(c3);
		list.add(c4);
		
		when(repo.findAll()).thenReturn(list);
		List<Course> courseList=courseService.selectAllCourse();
		assertEquals(4,courseList.size());
		verify(repo, times(1)).findAll();
	}
	
	@Test
	public void getCourseTest() {
		Course setCourse=new Course();
		setCourse.setId("COU001");
		setCourse.setName("Java");
		
		when(repo.findById("COU001")).thenReturn(Optional.ofNullable(setCourse));
		Course getCourse=courseService.getCourse("COU001");
		assertEquals("Java",getCourse.getName());
	}
	
	
}
