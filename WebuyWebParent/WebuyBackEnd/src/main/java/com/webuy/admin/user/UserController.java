package com.webuy.admin.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webuy.common.entity.Role;
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
	
	//create new user controller
	@GetMapping("/users/new")
	public String createNewUser(Model model) {
		List<Role> listRoles = userService.listRoles();
		User newUser = new User();
		newUser.setEnabled(true);
		model.addAttribute("newUser", newUser);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("pageTitle", "Create new user");
		return "new_user_form";
	}
	
	//save user controller
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes) {
		//System.out.println(user);
		userService.save(user);
		
		redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
		return "redirect:/users";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable(name = "id") Integer id, Model model,RedirectAttributes redirectAttributes) {
		try {
			User user = userService.getUser(id);
			List<Role> listRoles = userService.listRoles();
			model.addAttribute("newUser", user);
			model.addAttribute("pageTitle", "Edit user has ID: " + id);
			model.addAttribute("listRoles", listRoles);
			return "new_user_form";
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("message", e.getMessage());
			
		}
		
		return "redirect:/users";
		
	}
	
	
	
}
