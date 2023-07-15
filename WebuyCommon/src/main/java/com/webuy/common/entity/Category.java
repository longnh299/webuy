package com.webuy.common.entity;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "categories")
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@Column(unique = true, nullable = false)
	private String alias;
	
	@Column(nullable = false)
	private String image;
	
	private boolean enabled;
	
	@ManyToOne // change @OneToOne to @ManyToOne to fix unique constraint in parent_category_id
	@JoinColumn(name = "parentCategory_id")
	private Category parentCategory;
	
	@OneToMany(mappedBy = "parentCategory")
	private Set<Category> subCategory = new HashSet<>();
	
	

	public Category(String name, String alias, String image, boolean enabled, Category parentCategory) {
		this.name = name;
		this.alias = alias;
		this.image = image;
		this.enabled = enabled;
		this.parentCategory = parentCategory;
	}
	
	

	public Category() {
		// TODO Auto-generated constructor stub
	}
	
	public Category(Integer id) {
		this.id = id;
	}

	public Category(String alias ,String name, String image) {
		this.alias = alias;
		this.name = name;
		this.image = image;
	}
	
	public Category(String name) {
		this.alias = name;
		this.name = name;
		this.image = "default.png";
	}
	
	public Category(Integer id, String name) {
		this.id = id;
		this.name = name;
		
	}
	
	public static Category transferIdAndName(Category category) {
		Category categoryCopy = new Category();
		
		categoryCopy.setId(category.getId());
		categoryCopy.setName(category.getName());
		
		return categoryCopy;
	}
	
	public static Category transferIdAndNameSymbol(Integer id, String name) {
		Category categoryCopySymBol = new Category();
		
		categoryCopySymBol.setId(id);
		categoryCopySymBol.setName(name);
		
		return categoryCopySymBol;
	}
	
	public Category(String alias, String name, String image, Category parentCategory) {
		this(alias, name, image);
		this.parentCategory = parentCategory;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParentCategory() {
		return parentCategory;
	}

	public void setParentCategory(Category parentCategory) {
		this.parentCategory = parentCategory;
	}

	public Set<Category> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(Set<Category> subCategory) {
		this.subCategory = subCategory;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", alias=" + alias + ", image=" + image + ", enabled="
				+ enabled + "]";
	}
	
	
	
	


}
