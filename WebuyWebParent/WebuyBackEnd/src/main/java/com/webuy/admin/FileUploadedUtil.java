package com.webuy.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadedUtil {
	
	//save file to dir
	public static void saveFile(String dir, String fileName, MultipartFile multipartFile) throws IOException {
		
		Path path = Paths.get(dir);
		
		if(!Files.exists(path)){
			Files.createDirectories(path);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (Exception e) {
			// TODO: handle exception
			throw new IOException("Could not save file: " + fileName, e);
		}
	}
	
	//clean old image 
	public static void cleanDir(String dir) {
		Path dirPath = Paths.get(dir);
		
		try {
			Files.list(dirPath).forEach(file -> {
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
						// TODO: handle exception
						System.out.println("Could not delete file: " + file);
					}
					
				}
			});
		} catch (IOException e) {
			// TODO: handle exception
			System.out.println("Colud not list directory: " + dirPath);
		}
	}

}
