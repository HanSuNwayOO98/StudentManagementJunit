package com.sms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sms.dto.Course;

@Service
public class CourseService {
	@Autowired 
	CourseRepository repo;
	
	public List<Course> selectAllCourse(){
		return (List<Course>) repo.findAll();
	}
	
	public Course getCourse(String id) {
		return repo.findById(id).get();
	}
	
	public void save(Course course) {
		repo.save(course);
	}
}
