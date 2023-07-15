package com.webuy.admin.category;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.webuy.admin.FileUploadedUtil;
import com.webuy.common.entity.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	//list all categories controller
	@GetMapping("/categories")
	public String getAllCategory(Model model) {
		
		List<Category> listCategories = categoryService.listAllCategories();
		
		model.addAttribute("listCategories", listCategories);
		
		for(Category category : listCategories) {
			System.out.println(category);
		}
		
		return "/category-template/category";
		
	}
	
	// add new category controller
	@GetMapping("/categories/new")
	public String createNewCategory(Model model) {
		
		List<Category> listCategories = categoryService.listCategoriesForm();
		String pageTitle = "Create a new category";
		Category category = new Category();
		model.addAttribute("pageTitle", pageTitle);
		model.addAttribute("category", category);
		model.addAttribute("listCategories", listCategories);
		
		
		return "/category-template/new_category_form";
	}
	
	//save category controller
	@PostMapping("/categories/save")
	public String savedCategory(Category category, RedirectAttributes redirectAttributes, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {
		
		String file = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		category.setImage(file);
		Category saveCategory = categoryService.saveCategory(category);
		
		String uploadImageDir = "../category-images/" + saveCategory.getId();
		FileUploadedUtil.saveFile(uploadImageDir, file, multipartFile);
		redirectAttributes.addFlashAttribute("message", "The category has been saved successfully!");
		
		return "redirect:/categories";
	}
}
