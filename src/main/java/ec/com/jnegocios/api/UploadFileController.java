
package ec.com.jnegocios.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ec.com.jnegocios.exception.BadRequestException;
import ec.com.jnegocios.exception.global.file.FileNotSupportException;
import ec.com.jnegocios.service.upload.FileDetails;
import ec.com.jnegocios.service.upload.UploadFileControllerService;
import ec.com.jnegocios.util.AppHelper;
import ec.com.jnegocios.util.JSONResponse;

@RestController
@RequestMapping(AppHelper.PREFIX_UPLOAD)
public class UploadFileController {

	@Autowired
	private UploadFileControllerService uploadService;

	@PostMapping("/image-account")
	public ResponseEntity<?> uploadImageAccount(@RequestParam MultipartFile img ) {

		Authentication authUser = SecurityContextHolder.getContext()
			.getAuthentication();
		String username = authUser.getName();

		if( img.isEmpty() ) {
			throw new BadRequestException("You have not sent any picture"); }
		
		String contentType = img.getContentType();
		if( !uploadService.isValidImage(contentType) ) {
			throw new FileNotSupportException(contentType); }
		
		FileDetails fileDetails = uploadService.uploadImageAccount(img, username);
			
		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(fileDetails);

	}
	
	@PostMapping("/image-note")
	public ResponseEntity<?> uploadImageNote (
		@RequestParam Integer noteId, @RequestParam MultipartFile[] img) {

		if( img.length < 0 ) {
			throw new BadRequestException("You have not sent any picture"); }
		
		String contentType;
		for(int index = 0; index < img.length; index++) {

			contentType = img[index].getContentType();
			if( !uploadService.isValidImage(img[index].getContentType()) ) {
				throw new FileNotSupportException(contentType); } 
			
		}
		
		List<FileDetails> filesDetails = uploadService.uploadImagesNote(img, noteId);
		
		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body(filesDetails);

	}

	@DeleteMapping("/delete-file")
	public ResponseEntity<?> deleteImage(@RequestParam String uniqueId) {

		uploadService.deleteFile(uniqueId);

		return ResponseEntity.ok()
			.contentType(MediaType.APPLICATION_JSON)
			.body( JSONResponse.fromGeneralTemplate(
				AppHelper.PREFIX_UPLOAD + "/delete-file", 
				"The file " + uniqueId + " is successfuly deleted", 
				200).getBody() 
			);

	}

}
