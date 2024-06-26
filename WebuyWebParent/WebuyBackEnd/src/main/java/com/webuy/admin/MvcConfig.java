package com.webuy.admin;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		String dir = "user-photos";
		Path photoDir = Paths.get(dir);
		
		String photoPath = photoDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/" + dir + "/**")
		.addResourceLocations("file:" + photoPath + "/");
		
		String categoryImageDirectoryName = "../category-images";
		Path imageDir = Paths.get(categoryImageDirectoryName);
		
		String imagePath = imageDir.toFile().getAbsolutePath();
		
		registry.addResourceHandler("/category-images/**")
		.addResourceLocations("file:" + imagePath + "/");
	}
	
	
	

}
