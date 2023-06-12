package com.webuy.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webuy.common.entity.User;


@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	//list all users controller
	@GetMapping("/users")
	public String listAllUsers(Model model) {
		
		List<User> listUsers = userService.listAllUsers();
		model.addAttribute("listUsers", listUsers);
		return "users";
	}
}
