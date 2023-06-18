package com.webuy.admin.user;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ansi.AnsiOutput.Enabled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webuy.admin.FileUploadedUtil;
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
	public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		//System.out.println(user);
		//System.out.println(multipartFile.getOriginalFilename());
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.save(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			
			FileUploadedUtil.cleanDir(uploadDir); // clean dir before upload
			FileUploadedUtil.saveFile(uploadDir, fileName, multipartFile);			
		} else {
			if(user.getPhotos().isEmpty()) 
				user.setPhotos(null);
			userService.save(user);
		}

		//userService.save(user);
		
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
			return "redirect:/users";
			
		}
		
		//return "redirect:/users";
		
	}
	
	@GetMapping("users/delete/{id}")
	public String deleteUser(@PathVariable(name = "id") Integer id, Model model, RedirectAttributes redirectAttributes) {
		
		try {
			userService.delete(id);
			redirectAttributes.addFlashAttribute("message", "The user ID" + id + " has been deleted successfully!");
		} catch (UserNotFoundException e) {
			// TODO: handle exception
			redirectAttributes.addFlashAttribute("message", e.getMessage());
		}
		
		return "redirect:/users"; 
		
	}
	
	@GetMapping("/users/{id}/enabled/{status}")
	public String updateUserStatusController(@PathVariable("id") Integer id, @PathVariable("status") boolean status, RedirectAttributes redirectAttributes) {
		userService.updateUserStatus(id, status);
		
		String s = status ? "enabled" : "disabled";
		String message = "The user ID " + id + " has been " + s;
		
		redirectAttributes.addFlashAttribute("message", message);
		
		return "redirect:/users"; 
	}
	
	
	
}
