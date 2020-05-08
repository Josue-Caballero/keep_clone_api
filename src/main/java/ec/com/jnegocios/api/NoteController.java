
package ec.com.jnegocios.api;

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

import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.service.note.NoteService;
import ec.com.jnegocios.util.AppHelper;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(AppHelper.PREFIX_NOTE)
public class NoteController {
	
	@Autowired
	private NoteService serviceNote;
	
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
		
	@GetMapping(value = "/{id}", produces = AppHelper.JSON)
	public ResponseEntity<Note> getNoteByById (@PathVariable Integer id)
	{
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.body(this.serviceNote.findById(id));
	}
	
	@PostMapping(value = "/", produces = AppHelper.JSON)
	public ResponseEntity<Note> save (@RequestBody Note note) {
		
		Authentication authUser = SecurityContextHolder
			.getContext().getAuthentication();
		
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(this.serviceNote.save(note, authUser.getName()));
	
	}
			
	@DeleteMapping(value = "/{id}", produces = AppHelper.JSON)
	public ResponseEntity<?> destroy (@PathVariable Integer id)
	{
		this.serviceNote.delete(id);
		
		// We have to delete the images of the notes in the space
		
		return ResponseEntity
			.ok("{\"message\":\"Nota borrada.\"}");
	}

}
