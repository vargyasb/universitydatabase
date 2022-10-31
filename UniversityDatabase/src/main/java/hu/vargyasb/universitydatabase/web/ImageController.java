package hu.vargyasb.universitydatabase.web;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import hu.vargyasb.universitydatabase.api.ImageControllerApi;
import hu.vargyasb.universitydatabase.service.ImageService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class ImageController implements ImageControllerApi {
	
	private final ImageService imageService;
	
	@Override
	public ResponseEntity<Resource> downloadImage(Integer id) {
		return ResponseEntity.ok(imageService.getImage(id));
	}
	
}
