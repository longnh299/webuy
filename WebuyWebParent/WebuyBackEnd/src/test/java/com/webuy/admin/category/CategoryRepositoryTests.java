package com.webuy.admin.category;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.intThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.webuy.common.entity.Category;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTests {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
//	@Test
//	public void testCreateCategory() {
//		//Category category = new Category("Smart Phones" ,"Smart Phones", "default.png");
//		Category category = new Category("Electonics" ,"Electronics", "default.png");
//		Category savedCategoryTest =  categoryRepository.save(category);	
//		
//		assertThat(savedCategoryTest.getId()).isGreaterThan(0);
//	}
	
//	@Test
//	public void testCreateSubCategory() {
////		Category parent = new Category(1);
////		
////		Category laptop = new Category("Laptops", "Laptops", "default.png", parent);
////		Category component = new Category("Computers Components", "Computers Components", "default.png", parent);
////		
////		List<Category> categories = new ArrayList<>();
////		categories.add(laptop);
////		categories.add(component);
////		
////		categoryRepository.saveAll(categories);
////		
////		//assertThat(saveCategory.getId()).isGreaterThan(0);
//		
////		Category parent = new Category(4);
////		
////		Category camera = new Category("Cameras", "Cameras", "default.png", parent);
////		Category smartphone = new Category("Smart Phones", "Smart Phones", "default.png", parent);
////		
////		List<Category> categories = new ArrayList<>();
////		categories.add(camera);
////		categories.add(smartphone);
////		
////		categoryRepository.saveAll(categories);
//		
//		//assertThat(saveCategory.getId()).isGreaterThan(0);
//		
//		Category parent = new Category(3);
//		
//		Category memory = new Category("Memorys", "Memorys", "default.png", parent);
//		
//		categoryRepository.save(memory);
//		
//		
//	}
	
//	@Test
//	public void testGetCategoryInDB() {
//		Category category = categoryRepository.findById(1).get();
//		
//		System.out.println(category.getName());
//		
//		Set<Category> children = category.getSubCategory();
//		
//		for(Category c : children) {
//			System.out.println(c.getName());
//		}
//		
//		assertThat(children.size()).isGreaterThan(0);
//		
//		
//	}
	
//	@Test
//	public void testGetHierarchicalCategories() {
//		Iterable<Category> categories = categoryRepository.findAll();
//		
//		for(Category category : categories) {
//			if(category.getParentCategory() == null) {
//				System.out.println(category.getName()); //root category
//				
//				Set<Category> childrenCategories = category.getSubCategory();
//				
//				for(Category sub : childrenCategories) {
//					System.out.println("--" + sub.getName());
//					showChild(sub, 1);
//				}
//			}
//		}
//	}
	
	@Test
	public void testListRootCategories() {
		List<Category> listParentCategories = categoryRepository.listParentCategories();
		
		for(Category category : listParentCategories) {
			System.out.println(category.getName());
		}
	}
	
//	@Test
//	public void testCreateSubCategory() {
//	
//	Category parent = new Category(7);
//	
//	Category HDD = new Category("HDD", "HDD", "default.png", parent);
//	Category SSD = new Category("SSD", "SSD", "default.png", parent);
//	categoryRepository.save(HDD);
//	categoryRepository.save(SSD);
//}
	
	private void showChild(Category parentCategory, int subLevel) {
		int newSubLevel = subLevel + 1;
		
		for(Category sub : parentCategory.getSubCategory()) {
			for(int i = 0; i < newSubLevel; i++) {
				System.out.print("--");
			}
			System.out.println(sub.getName());
			
			showChild(sub, newSubLevel);
			
		}
	}
	


}
