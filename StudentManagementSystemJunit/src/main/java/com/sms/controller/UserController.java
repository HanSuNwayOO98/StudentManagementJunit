package com.sms.controller;

import java.util.List;
import java.util.Optional;

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

import com.sms.dao.UserService;
import com.sms.dto.User;
import com.sms.model.UserBean;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(value = "/userRegister")
	public ModelAndView displayUserRegister(ModelMap map) {
		UserBean userBean = new UserBean();
		return new ModelAndView("USR001", "userBean", userBean);
	}

	@PostMapping("/userRegister")
	public String userRegister(@ModelAttribute("userBean") @Validated UserBean userBean, BindingResult br,
			ModelMap model) {
		if (br.hasErrors()) {
			return "USR001";
		} else if (!userBean.getPassword().equals(userBean.getConfirmPassword())) {
			model.addAttribute("passwordError", "Password doesn't match!");
			return "USR001";
		} else {
			// id auto generate
			String id = "";
			List<User> list = userService.selectAllUser();
			if (list == null || list.size() <= 0) {
				id = "USR001";
			} else {
				User lastDTO = list.get(list.size() - 1);
				int lastId = Integer.parseInt(lastDTO.getId().substring(3));
				id = String.format("USR" + "%03d", lastId + 1);
			}
			userBean.setId(id);
			User dto = new User();
			dto.setId(userBean.getId());
			dto.setName(userBean.getName());
			dto.setEmail(userBean.getEmail());
			dto.setPassword(userBean.getPassword());
			dto.setRole(userBean.getRole());
			userService.save(dto);
			model.addAttribute("success", "Successfully registered.");
			return "redirect:/displayUser";
		}

	}

	@GetMapping("/displayUser")
	public String displayUser(ModelMap model, HttpSession session) {
		if (session.getAttribute("user") == null) {
			return "redirect:/";
		} else {
			List<User> list = userService.selectAllUser();
			model.addAttribute("userList", list);
			return "USR003";
		}

	}

	@GetMapping("/userSearch")
	public String userSearch(ModelMap model, @RequestParam("id") String id, @RequestParam("name") String name) {
		if (name.isBlank() && id.isBlank()) {
			return "redirect:/displayUser";
		} else {
			List<User> list = userService.searchUser(id, name);
			model.addAttribute("userList", list);
			return "USR003";

		}
	}

	@GetMapping("/userUpdate")
	public ModelAndView displayUserUpdate(@RequestParam("id") String id,HttpSession session) {
		Optional<User> user=userService.getUserbyId(id);
		UserBean usr=new UserBean();
		usr.setId(user.get().getId());
		usr.setName(user.get().getName());
		usr.setEmail(user.get().getEmail());
		usr.setPassword(user.get().getPassword());
		usr.setConfirmPassword(user.get().getPassword());
		usr.setRole(user.get().getRole());
		session.setAttribute("password", user.get().getPassword());
		return new ModelAndView("USR002", "userBean", usr);
	}

	@PostMapping("/userUpdate")
	public String userUpdate(@ModelAttribute("userBean") @Validated UserBean userBean, BindingResult br,
			HttpSession session, ModelMap model,@RequestParam("password") String pwd,@RequestParam("cfpassword") String cfPwd) {
		if (br.hasErrors()) {
			return "USR002";
		} else if (!pwd.equals(cfPwd)) {
			model.addAttribute("Error", "Password doesn't match.");
			return "USR002";
		} else {
			User user = new User();
			user.setId(userBean.getId());
			user.setName(userBean.getName());
			user.setEmail(userBean.getEmail());
			user.setPassword(pwd);
			user.setRole(userBean.getRole());
			userService.update(user, user.getId());
			return "redirect:/displayUser";
		}

	}

	@GetMapping("/userDelete")
	public String userDelete(@RequestParam("id") String id, ModelMap model) {

		userService.delete(id);
		return "redirect:/displayUser";
	}

}