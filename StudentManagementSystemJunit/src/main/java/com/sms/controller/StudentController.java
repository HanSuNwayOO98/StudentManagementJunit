package com.sms.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sms.dao.CourseService;
import com.sms.dao.StudentService;
import com.sms.dto.Course;
import com.sms.dto.Student;
import com.sms.model.StudentBean;

@Controller
public class StudentController {

	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;

	@GetMapping("/studentRegister")
	public ModelAndView showStudentRegister(HttpSession session) {
		if (session.getAttribute("user") == null) {
			return new ModelAndView("redirect:/");
		}
		else 
		{
			StudentBean studentBean = new StudentBean();
			 String id = ""; 
			  List<Student> list = studentService.selectAllStudent();
			  if(list == null || list.size() <= 0) { 
				  id = "STU001"; 
			  }else {
				  Student lastDTO = list.get(list.size()-1); 
				  int lastId = Integer.parseInt(lastDTO.getId().substring(3)); 
				  id = String.format("STU"+"%03d", lastId+1); 
				  } 
			  session.setAttribute("stuId", id);
			return new ModelAndView("STU001", "studentBean", studentBean);
		}
	}

	@PostMapping("/studentRegister")
	public String studentRegister(@ModelAttribute("studentBean") @Validated StudentBean studentBean, BindingResult br,
			ModelMap model,@RequestParam("id") String id) {
		if (br.hasErrors()) {
			return "STU001";
		}
		;
		Student student = new Student();
		student.setId(id);
		student.setName(studentBean.getName());
		student.setDob(studentBean.getDob());
		student.setGender(studentBean.getGender());
		student.setEducation(studentBean.getEducation());
		student.setPhone(studentBean.getPhone());
		ArrayList<Course> courses = studentBean.getCourse();
		for (Course c : courses) {
			student.addCourse(c);
		}
		studentService.save(student);
		return "redirect:/displayStudent";
	}

	@GetMapping("/displayStudent")
	public String displayStudent(ModelMap model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "redirect:/";
		} else {
			List<Student> studentList = studentService.selectAllStudent();
			model.addAttribute("studentList", studentList);
			return "STU003";
		}
	}

	@GetMapping("/searchStudent")
    public String searchStudent(@RequestParam("id") String id, @RequestParam("name") String name,
            @RequestParam("course") String course, ModelMap model) {
        if (id.isBlank() && name.isBlank() && course.isBlank()) {
            return "redirect:/displayStudent";
        } else {
            List<Student> studentList = studentService.searchStudent(id, name, course);
            model.addAttribute("studentList", studentList);
            return "STU003";
        }
    
	}

	@GetMapping("/seeMore/{id}")
	public ModelAndView seeMore(@PathVariable("id") String id) {		
		Student student = studentService.getById(id);
		List<Course> attendedCourse = student.getCourses();
		ArrayList<Course> list = new ArrayList<>();
		for (Course c : attendedCourse) {
			list.add(c);
		}
		StudentBean studentBean=new StudentBean();
		studentBean.setId(student.getId());
		studentBean.setName(student.getName());
		studentBean.setDob(student.getDob());
		studentBean.setGender(student.getGender());
		studentBean.setPhone(student.getPhone());
		studentBean.setEducation(student.getEducation());
		studentBean.setCourse(list);
		return new ModelAndView("STU002", "studentBean", studentBean);
	}

	@GetMapping("/studentDelete")
	public String deleteStudent(@RequestParam("id") String id) {
		studentService.deleteById(id);
		return "redirect:/displayStudent";
	}

	@GetMapping("/studentUpdate")
	public ModelAndView showStudentUpdate(@RequestParam("id") String id) {
		Student student = studentService.getById(id);
		ArrayList<Course> list = new ArrayList<>();
		for (Course c : student.getCourses()) {
		list.add(c);
		}
		StudentBean studentBean=new StudentBean();
		studentBean.setId(student.getId());
		studentBean.setName(student.getName());
		studentBean.setDob(student.getDob());
		studentBean.setGender(student.getGender());
		studentBean.setPhone(student.getPhone());
		studentBean.setEducation(student.getEducation());
		studentBean.setCourse(list);
		return new ModelAndView("STU002-01", "studentBean", studentBean);
	}
	@PostMapping("/updateStudent")
	public String updateStudent(@ModelAttribute("studentBean") @Validated StudentBean bean, BindingResult br) {
		if (br.hasErrors()) {
			return "STU002-01";
		}
		Student student = new Student();
		student.setId(bean.getId());
		student.setName(bean.getName());
		student.setDob(bean.getDob());
		student.setGender(bean.getGender());
		student.setEducation(bean.getEducation());
		student.setPhone(bean.getPhone());
		for (Course course : bean.getCourse()) {
			student.addCourse(course);
		}
		studentService.update(student);
		return "redirect:/displayStudent";
	}

	@ModelAttribute(value = "courseList")
	public List<Course> courseList() {
		ArrayList<String> list = new ArrayList<>();
		List<Course> courseLists = courseService.selectAllCourse();
		for (Course course : courseLists) {
			list.add(course.getName());
		}
		return courseLists;
	}

}