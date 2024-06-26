package com.webuy.admin.category;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.webuy.common.entity.Category;

public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer>, CrudRepository<Category, Integer>{
	
	@Query("SELECT c FROM Category c WHERE c.parentCategory.id is NULL")
	public List<Category> listParentCategories();
}
