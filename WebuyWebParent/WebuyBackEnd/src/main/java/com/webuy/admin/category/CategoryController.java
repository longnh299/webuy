package com.webuy.admin.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
}
