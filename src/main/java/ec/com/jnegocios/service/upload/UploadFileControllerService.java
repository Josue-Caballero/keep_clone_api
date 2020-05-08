
package ec.com.jnegocios.service.upload;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ec.com.jnegocios.exception.global.file.FileUploadException;

public interface UploadFileControllerService {
	
	/**
	 * Upload an image for the user account
	 * @param file the image file
	 * @param username the username account
	 * @return a FileDetails object with details of current file
	 * @throws FileUploadException
	 */
	FileDetails uploadImageAccount(MultipartFile file, String username) 
		throws FileUploadException;
	
	/** 
	 * Upload a multiple file images for a note
	 * @param file an array of files
	 * @param noteId the note id
	 * @return a list of FileDetails object with details of each file
	 * @throws FileUploadException
	*/
	List<FileDetails> uploadImagesNote(MultipartFile[] file, int noteId) 
		throws FileUploadException;

	/**
	 * Delete a file of storage
	 * @param uniqueId the unique file id
	 * @return true only if file was deleted
	 */
	boolean deleteFile(String uniqueId);

	/**
	 * Validate the file type
	 * @param contentType a image content type
	 * @return true if image is an allowed image or false in another case
	 */
	boolean isValidImage(String contentType);

}
