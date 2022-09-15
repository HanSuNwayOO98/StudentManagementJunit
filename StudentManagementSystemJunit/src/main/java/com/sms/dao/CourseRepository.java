package com.sms.dao;

import org.springframework.data.repository.CrudRepository;

import com.sms.dto.Course;


public interface CourseRepository extends CrudRepository<Course, String> {

}