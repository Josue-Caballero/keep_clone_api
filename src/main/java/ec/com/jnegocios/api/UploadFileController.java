package ec.com.jnegocios.api;

import java.io.IOException;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ec.com.jnegocios.exception.BadRequestException;
import ec.com.jnegocios.util.AppHelper;

@Controller
public class UploadFileController {

	@RequestMapping(value=AppHelper.PREFIX_IMAGE, method=RequestMethod.POST, headers=("content-type=multipart/form-data"))
	public ResponseEntity<byte[]> uplaodImage (@RequestParam("image") MultipartFile multipartfile) throws IOException {
		if(multipartfile.isEmpty())
			throw new BadRequestException("Seleccione una imagen.");
		
		// UPLOAD IMAGE IN STORAGE CLOUD
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(multipartfile.getBytes());
	}
}
