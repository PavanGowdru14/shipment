package org.jsp.backend.controller;

import java.io.IOException;

import org.jsp.backend.model.CustomOAuth2User;
import org.jsp.backend.model.User;
import org.jsp.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user")
	public CustomOAuth2User getUser(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
		String email = customOAuth2User.getEmail();
		User user = userService.findByEmail(email);
		if (user == null) {
			user = new User();
			user.setEmail(email);
			user.setUsername(customOAuth2User.getName());
			userService.saveUser(user);
		}
		return customOAuth2User;
	}

	@GetMapping("/")
	public void form(HttpServletResponse response) throws IOException {
		response.sendRedirect("http://localhost:3000/continue");
	}	
}
