package com.sms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.sms.dao.CourseService;
import com.sms.dto.Course;
import com.sms.model.CourseBean;

@Controller
public class CourseController {

	@Autowired
	private CourseService courseService;

	@GetMapping("/courseRegister")
	public ModelAndView displayCourseRegister(HttpSession session) {
		if (session.getAttribute("user") == null) {
			return new ModelAndView("redirect:/");
		} else {
			CourseBean bean = new CourseBean();
			String id = "";
			List<Course> list = courseService.selectAllCourse();
			if (list == null || list.size() <= 0) {
				id = "COU001";
			} else {
				Course lastDTO = list.get(list.size() - 1);
				int lastId = Integer.parseInt(lastDTO.getId().substring(3));
				id = String.format("COU" + "%03d", lastId + 1);
			}
			session.setAttribute("courseID", id);
			return new ModelAndView("BUD003", "courseBean", bean);

		}

	}

	@PostMapping("/courseRegister")
	public String courseRegister(@RequestParam("id") String id,@ModelAttribute("courseBean") @Validated CourseBean courseBean, BindingResult br,
			ModelMap model) {
		if (br.hasErrors()) {
			return "BUD003";
		} else if (this.isCourseExist(courseBean.getName())) {
			model.addAttribute("error", courseBean.getName() + " already exists! ");
			return "STU001";
		} else {
			Course course = new Course();
			course.setId(id);
			course.setName(courseBean.getName());
			courseService.save(course);
			model.addAttribute("success", course.getName() + " is successfully added!");
			return "STU001";
		}

	}

	public boolean isCourseExist(String courseName) {
		List<Course> list = courseService.selectAllCourse();
		if (list != null) {
			for (Course course : list) {
				if (course.getName().equals(courseName))
					return true;
			}
		}
		return false;

	}
}