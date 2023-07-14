package com.webuy.admin.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.webuy.common.entity.Category;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	//get all category service
	public List<Category> listAllCategories(){
		return (List<Category>) categoryRepository.findAll();
	}
	
	//list categories use in vew_category_form.html service 
	public List<Category> listCategoriesForm(){
		
		List<Category> categoriesInForm = new ArrayList<>();
		Iterable<Category> categoriesInDatabase = categoryRepository.findAll();
		
		for(Category category : categoriesInDatabase) {
			if(category.getParentCategory() == null) {
				//System.out.println(category.getName()); //root category
				categoriesInForm.add(new Category(category.getName()));
				
				Set<Category> childrenCategories = category.getSubCategory();
				
				for(Category sub : childrenCategories) {
					categoriesInForm.add(new Category("--" + sub.getName()));
					showChild(categoriesInForm, sub, 1);
				}
			}
		}
		
		return categoriesInForm;
	}
	
	private void showChild(List<Category> categoriesInForm, Category parentCategory, int subLevel) {
		int newSubLevel = subLevel + 1;
		
		for(Category sub : parentCategory.getSubCategory()) {
			String symbol = "";
			for(int i = 0; i < newSubLevel; i++) {
				symbol += "--";
			}
			categoriesInForm.add(new Category(symbol + sub.getName()));
			
			showChild(categoriesInForm, sub, newSubLevel);
			
		}
	}

}
