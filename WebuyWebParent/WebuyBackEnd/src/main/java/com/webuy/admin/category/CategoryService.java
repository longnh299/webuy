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
		//return (List<Category>) categoryRepository.findAll();
		List<Category> parentCategories = categoryRepository.listParentCategories();
		return listHierarCategories(parentCategories);
	}
	
	public List<Category> listHierarCategories(List<Category> parentCategories){
		
		List<Category> categoriesInHierar = new ArrayList<>();
		//Iterable<Category> categoriesInDatabase = categoryRepository.findAll();
		
		for(Category parentCategory : parentCategories) {
			
			categoriesInHierar.add(Category.copyFullCategoryInfo(parentCategory));
			
			Set<Category> subCategories = parentCategory.getSubCategory();
			for(Category sub : subCategories) {
				String nameWithSymbol = "--" + sub.getName();
				categoriesInHierar.add(Category.copyFullCategoryInfo(sub, nameWithSymbol));
				
				listSubHierarchicalCategories(sub, 1, categoriesInHierar);
			}
			
		}
		
		return categoriesInHierar;
	}
	
	public void listSubHierarchicalCategories(Category parentCategory, int subLevel, List<Category> categoriesInHierar) {
		Set<Category> subCategories = parentCategory.getSubCategory();
		int newSubLevel = subLevel + 1;
		
		for(Category sub : subCategories) {
			String symbol = "";
			for(int i = 0; i < newSubLevel; i++) {
				symbol += "--";
			}
			symbol += sub.getName();
			categoriesInHierar.add(Category.copyFullCategoryInfo(sub, symbol));
			
			listSubHierarchicalCategories(sub, newSubLevel, categoriesInHierar);
		}
	}
	
	//list categories use in vew_category_form.html service 
	public List<Category> listCategoriesForm(){
		
		List<Category> categoriesInForm = new ArrayList<>();
		Iterable<Category> categoriesInDatabase = categoryRepository.findAll();
		
		for(Category category : categoriesInDatabase) {
			if(category.getParentCategory() == null) {
				//System.out.println(category.getName()); //root category
				categoriesInForm.add(Category.transferIdAndName(category));
				
				Set<Category> childrenCategories = category.getSubCategory();
				
				for(Category sub : childrenCategories) {
					categoriesInForm.add(Category.transferIdAndNameSymbol(sub.getId(), "--" + sub.getName()));
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
			categoriesInForm.add(Category.transferIdAndNameSymbol(sub.getId(), symbol + sub.getName()));
			
			showChild(categoriesInForm, sub, newSubLevel);
			
		}
	}
	
	//save category service
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}
}
