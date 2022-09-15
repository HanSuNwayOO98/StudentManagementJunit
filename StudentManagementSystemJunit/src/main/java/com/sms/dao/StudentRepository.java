package com.sms.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sms.dto.Student;

public interface StudentRepository extends CrudRepository<Student, String>{
	
	List<Student> findDistinctStudentByIdOrNameOrCourses_Name(String id, String name, String course);
		
	
}