package hu.vargyasb.universitydatabase.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ImageService {

	
	public Resource getImage(Integer id) {
		FileSystemResource fileSystemResource = new FileSystemResource(Paths.get("/Users/vargyasb/Pictures/testimages", id.toString() + ".jpg"));
		if(!fileSystemResource.exists()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		return fileSystemResource;
	}
}
