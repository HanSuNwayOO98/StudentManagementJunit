package com.sms.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sms.dao.UserService;
import com.sms.dto.User;
import com.sms.helper.DateUtil;



@Controller
public class AuthenticationController {
	@Autowired
	private UserService userService ;
	
		@GetMapping("/")
		public String displayRoot() {
			return "LGN001";
		}
		@GetMapping("/login")
		public String displayLogin() {
			return "LGN001";
		}
		
		@PostMapping("/login")
		public String login(ModelMap model, @RequestParam("id") String id, @RequestParam("password") String password, HttpSession session) {
			String current_date = DateUtil.now();
			
			List<User> list = userService.selectAllUser();
			if(list != null) {			
				for(User user: list) {
					if( user.getId().equals(id) &&
							 user.getPassword().equals(password)) {
						session.setAttribute("userid", user.getId());
						session.setAttribute("user", user.getId()+"-"+user.getName());
						session.setAttribute("isLogin","Okay");
						session.setAttribute("date", current_date);
						return "MNU001";
					}
				}
			}		
			model.addAttribute("error", "Please check your data again! ");
			return "LGN001";		
		}
		@GetMapping("/Logout")
		public String logout(HttpSession session) {
			session.setAttribute("user", "");
			session.setAttribute("isLogin","");
			session.setAttribute("date", "");
			session.invalidate();
			return "LGN001";
		}
	
}