
package ec.com.jnegocios.service.upload;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.jnegocios.entity.Image;
import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.global.file.FileUploadException;
import ec.com.jnegocios.repository.ImageRepository;
import ec.com.jnegocios.repository.NoteRepository;
import ec.com.jnegocios.repository.UserRepository;

@Service
public class UploadFileControllerManager implements UploadFileControllerService {

	@Value("${do.space.bucketName}")
	private String bucketName;

	@Autowired
	private AmazonS3 spaceClient;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ImageRepository imageRepository;

	private String folder = "keep-clone/";

	public boolean isValidImage(String contentType) {

		String fileExtension = contentType.substring(
			contentType.lastIndexOf("/") + 1);

		if( fileExtension.equals("png") 
			|| fileExtension.equals("jpg")
			|| fileExtension.equals("jpeg")) { return true; }
		
		return false;

	}

	@Transactional
	public FileDetails uploadImageAccount(MultipartFile file, String username) 
		throws FileUploadException {
		
		String oldImage;
		UserAccount account = userRepository.findByUsername(username);
		FileDetails fileDetails = new FileDetails( file.getOriginalFilename() );
		
		if( uploadFile(file, fileDetails) ) {

			oldImage = account.getStorageUrl();
			account.setPhoto( fileDetails.getOriginalName() );
			account.setStorageUrl( fileDetails.getStorageUrl() );
			account.setUpdatedAt( LocalDateTime.now() );

			userRepository.save(account);

		} else { throw new FileUploadException(); }

		deleteFile( oldImage.substring(oldImage.lastIndexOf("/") + 1) );

		return fileDetails;
		
	}
	
	public List<FileDetails> uploadImagesNote(MultipartFile[] file, int noteId) 
		throws FileUploadException {
		
		Note note = noteRepository.findById(noteId).get();
		List<FileDetails> filesDetails = new ArrayList<>();
		
		Arrays.asList(file).forEach( (currentFile) -> {

			FileDetails tempFileD = new FileDetails( 
				currentFile.getOriginalFilename() );
		
			if( uploadFile(currentFile, tempFileD) ) {
	
				LocalDateTime currentTime = LocalDateTime.now();
	
				Image noteImage = new Image(0, 
					tempFileD.getOriginalName(),
					tempFileD.getStorageUrl(),
					note, 
					currentTime, 
					currentTime
				);
	
				imageRepository.save(noteImage);
		
			} else { throw new FileUploadException(); }	

			filesDetails.add(tempFileD);

		});
		
		return filesDetails;
		
	}

	public boolean deleteFile(String uniqueId) {

		spaceClient.deleteObject( new DeleteObjectRequest(
			bucketName, folder + uniqueId) );

		return true;

	}

	private boolean uploadFile(MultipartFile file, FileDetails fileDetails) {

		InputStream fileStream;
		String contentType = file.getContentType();
		ObjectMetadata metadata = new ObjectMetadata();

		try {
			
			fileStream = file.getInputStream();
			metadata.setContentLength(fileStream.available());
			metadata.setContentType(contentType);

		} catch( Exception e) { return false; /** Manage set metadata error */ }

		PutObjectResult result = spaceClient.putObject( 
			new PutObjectRequest( 
				bucketName, 
				folder + fileDetails.getUniqueId(), 
				fileStream, 
				metadata
			)
			.withCannedAcl(CannedAccessControlList.PublicRead) );

		return ( (result != null) ? true : false );

	}

}
