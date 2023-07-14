package com.webuy.admin.category;

import java.util.List;

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


}
