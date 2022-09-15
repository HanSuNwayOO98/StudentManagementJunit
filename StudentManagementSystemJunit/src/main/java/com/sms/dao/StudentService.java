package com.sms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.Student;

@Service
public class StudentService {
	@Autowired
	StudentRepository repo;
	
	public void save(Student s) {
		repo.save(s);
	}
	
	public Student getById(String id) {
		return repo.findById(id).get();
	}
	
	public List<Student> selectAllStudent(){
		return (List<Student>) repo.findAll();
	}
	
	public void update(Student s) {
		repo.save(s);
	}
	
	public void deleteById(String id) {
		repo.deleteById(id);
	}
	
	public List<Student> searchStudent(String id, String name, String course){
		return repo.findDistinctStudentByIdOrNameOrCourses_Name(id, name, course);
	}

}