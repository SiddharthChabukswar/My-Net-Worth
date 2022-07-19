package com.webproject.mynetworth.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public class SaveImageFileToStorage {

	public static Boolean saveImageToStorge(String type, MultipartFile image_file) throws IllegalStateException, IOException {
		String FileSaveDir = "file-storage-system/images/";
		if(type == "profile")
			FileSaveDir += "profile_photos/";
		else if(type == "cover")
			FileSaveDir += "cover_photos/";
		else
			return true;
		Path uploadPath = Paths.get(FileSaveDir);
		String image_name = StringUtils.cleanPath(image_file.getOriginalFilename());
		if (!Files.exists(uploadPath))
			Files.createDirectories(uploadPath);
		if (image_name == "")
			return true;
		try (InputStream inputStream = image_file.getInputStream()) {
			Path filePath = uploadPath.resolve(image_name);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + image_name, ioe);
		}
		return true;
	}

}
