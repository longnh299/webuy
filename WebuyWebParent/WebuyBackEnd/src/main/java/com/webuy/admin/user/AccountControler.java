package com.webuy.admin.user;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webuy.admin.FileUploadedUtil;
import com.webuy.admin.security.WebuyUserDetail;
import com.webuy.common.entity.User;

@Controller
public class AccountControler {
	
	@Autowired
	private UserService userService;
	
	//get account detail controller
	@GetMapping("/account")
	public String viewAccountDetail(@AuthenticationPrincipal WebuyUserDetail account, Model model) {
		String email = account.getUsername();
		User user = userService.getUserByEmail(email);
		
		model.addAttribute("user", user);
		
		return "account_detail";
		
	}
	
	//update user info from account_detail.html controller
	@PostMapping("/account/update")
	public String saveUserDetailFromForm(User user, 
		@AuthenticationPrincipal WebuyUserDetail account ,RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		//System.out.println(user);
		//System.out.println(multipartFile.getOriginalFilename());
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			user.setPhotos(fileName);
			User savedUser = userService.updateAccountDetail(user);
			String uploadDir = "user-photos/" + savedUser.getId();
			
			FileUploadedUtil.cleanDir(uploadDir); // clean dir before upload
			FileUploadedUtil.saveFile(uploadDir, fileName, multipartFile);			
		} else {
			if(user.getPhotos().isEmpty()) 
				user.setPhotos(null);
			userService.updateAccountDetail(user);
		}
		
		account.setFirstName(user.getFirstName());
		account.setLastName(user.getLastName());
		
		//userService.save(user);
		
		redirectAttributes.addFlashAttribute("message", "Your Account infomation has been updated.");
		
		return "redirect:/account";
	}

}
