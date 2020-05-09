
package ec.com.jnegocios.api;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.com.jnegocios.entity.Image;
import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.exception.NotFoundException;
import ec.com.jnegocios.repository.ImageRepository;
import ec.com.jnegocios.service.note.NoteService;
import ec.com.jnegocios.service.upload.UploadFileControllerService;
import ec.com.jnegocios.util.AppHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping(AppHelper.PREFIX_NOTE)
public class NoteController {
	
	@Autowired
	private NoteService serviceNote;
	
	@Autowired
	private UploadFileControllerService uploadService;
	
	@Autowired
	private ImageRepository repoImage;
	
	@GetMapping(value = "/pg", produces = AppHelper.JSON)
	public ResponseEntity<Page<Note>> getNotesPaginatedByUserAuth (
		@RequestParam(required = false, defaultValue = "0") int page, 
		@RequestParam(required = false, defaultValue = "3") int size) {
		
		Authentication authUser = SecurityContextHolder
			.getContext().getAuthentication();
		
		Page<Note> notes = this.serviceNote
			.findByUsername(authUser.getName(), 
			PageRequest.of(page, size, Sort.by("id").descending()));
		
		return new ResponseEntity<Page<Note>>(notes, HttpStatus.OK);
	}
	
	@GetMapping(produces = AppHelper.JSON)
	public ResponseEntity<Collection<Note>> getNotesSinceByUserAuth (
		@RequestParam(required = false, defaultValue = "0") int since,
		@RequestParam(required = false, defaultValue = "false") Boolean filed) {
		
		Authentication authUser = SecurityContextHolder
			.getContext().getAuthentication();
		
		Collection<Note> notes = this.serviceNote
				.findByNotFiledUsernameSince(authUser.getName(), since);
		
		if(filed)
			notes = this.serviceNote
				.findByFiledAndUsernameSince(authUser.getName(), since);
		
		return ResponseEntity
				.ok(notes);
	}
		
	@GetMapping(value = "/{id}", produces = AppHelper.JSON)
	public ResponseEntity<Note> getNoteByById (@PathVariable Integer id)
	{
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.body(this.serviceNote.findById(id));
	}
	
	@PostMapping(produces = AppHelper.JSON)
	public ResponseEntity<Note> save (@Valid @RequestBody Note note) {
		
		Authentication authUser = SecurityContextHolder
			.getContext().getAuthentication();
		
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(this.serviceNote.save(note, authUser.getName()));
	
	}
	
	@RequestMapping(value="/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT}, produces = AppHelper.JSON)
	public ResponseEntity<Note> update (@Valid @RequestBody Note note, @PathVariable Integer id) {
			
		return ResponseEntity
			.ok(this.serviceNote.update(note, id));
	
	}
	
	@DeleteMapping(value = "/{id}", produces = AppHelper.JSON)
	public ResponseEntity<?> destroy (@PathVariable Integer id)
	{	
		Note note = this.serviceNote.findById(id);
		for (Image img : note.getImages()) 
		{
			this.uploadService.deleteFile(img.getNameImage());
		}
		this.serviceNote.delete(id);
		
		return ResponseEntity
			.ok("{\"message\":\"Nota borrada.\"}");
	}
	
	@DeleteMapping(value = "/image/{id}", produces = AppHelper.JSON)
	public ResponseEntity<?> destroyImage (@PathVariable Integer id)
	{	
		Image image = repoImage.findById(id)
				.orElseThrow(() -> new NotFoundException("La imagen con id '"+id+ "' no existe."));
		
		if(this.uploadService.deleteFile(image.getNameImage()))
			this.repoImage.deleteById(image.getId());
		
		return ResponseEntity
			.ok("{\"message\":\"Imagen borrada.\"}");
	}
}
