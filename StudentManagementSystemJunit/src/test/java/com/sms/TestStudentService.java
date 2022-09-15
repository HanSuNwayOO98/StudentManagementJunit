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

import com.sms.dao.StudentRepository;
import com.sms.dao.StudentService;
import com.sms.dto.Student;



@SpringBootTest
public class TestStudentService {
	
	@Mock
	StudentRepository repo;
	
	@InjectMocks
	StudentService studentService;
	
	@Test
	public void selectAllStudentTest() {
		List<Student> list=new ArrayList<Student>();
		Student s1=new Student();
		s1.setId("STU001");
		s1.setDob("2022-09-21");
		s1.setEducation("1");
		s1.setGender("Female");
		s1.setName("hsno");
		s1.setPhone("09876543");
		
		Student s2=new Student();
		s2.setId("STU002");
		s2.setDob("2022-09-22");
		s2.setEducation("1");
		s2.setGender("Female");
		s2.setName("Jisoo");
		s2.setPhone("098765432");
		
		Student s3=new Student();
		s3.setId("STU003");
		s3.setDob("2022-09-20");
		s3.setEducation("2");
		s3.setGender("Male");
		s3.setName("Lucas");
		s3.setPhone("098765432");
		
		list.add(s1);
		list.add(s2);
		list.add(s3);
		
		when(repo.findAll()).thenReturn(list);
		List<Student> studentList=studentService.selectAllStudent();
		assertEquals(3,studentList.size());
		verify(repo, times(1)).findAll();
	}
	@Test
	public void getByIdTest() {
		Student setStudent=new Student();
		setStudent.setId("STU001");
		setStudent.setDob("2022-09-21");
		setStudent.setEducation("1");
		setStudent.setGender("Female");
		setStudent.setName("hsno");
		setStudent.setPhone("09876543");
		when(repo.findById("STU001")).thenReturn(Optional.ofNullable(setStudent));
		Student getStudent=studentService.getById("STU001");
		assertEquals("2022-09-21",getStudent.getDob());
		assertEquals("1",getStudent.getEducation());
		assertEquals("Female",getStudent.getGender());
		assertEquals("hsno",getStudent.getName());
		assertEquals("09876543",getStudent.getPhone());
		
	}
	
	@Test
	public void saveTest() {
		Student setStudent=new Student();
		setStudent.setId("STU001");
		setStudent.setDob("2022-09-21");
		setStudent.setEducation("1");
		setStudent.setGender("Female");
		setStudent.setName("hsno");
		setStudent.setPhone("09876543");
		studentService.save(setStudent);
		verify(repo,times(1)).save(setStudent);
	}
	
	@Test
	public void updateTest() {
		Student setStudent=new Student();
		setStudent.setId("STU001");
		setStudent.setDob("2022-09-21");
		setStudent.setEducation("1");
		setStudent.setGender("Female");
		setStudent.setName("hsno");
		setStudent.setPhone("09876543");
		studentService.save(setStudent);
		verify(repo,times(1)).save(setStudent);
	}
	@Test
	public void deleteTest() {
		studentService.deleteById("STU001");
		verify(repo,times(1)).deleteById("STU001");
	}
	
	

}
