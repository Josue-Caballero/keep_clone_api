
package ec.com.jnegocios.api;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

@RestController
public class NoteController {
	
	@Autowired
	private NoteService serviceNote;
	
	// Just a test to access the user no auth denied handler
	@GetMapping(AppHelper.PREFIX_NOTE + "/fv/{id}")
	public Note getNotes( @PathVariable int id) {

		Note testAuthNote = new Note();
		testAuthNote.setId(id);
		testAuthNote.setTitle("You have a valid token");

		return testAuthNote;

	}

	// Just a test to access the user auth denied handler
	@GetMapping(AppHelper.PREFIX_PREMIUM_NOTE)
	public Note getPremiumNotes() {

		Note testAuthNote = new Note();
		testAuthNote.setTitle("You have a valid premium token");

		return testAuthNote;

	}
	
	
	/****************************************
	 * 
	 * ********General End points************
	 * 
	 * **************************************/
	
	@GetMapping(value = AppHelper.PREFIX_NOTE + "/pg")
	public ResponseEntity<Page<Note>> getNotesPaginatedByUserAuth (
			@RequestParam(required = false, defaultValue = "0") int page, 
			@RequestParam(required = false, defaultValue = "3") int size, Principal auth)
	{
		Page<Note> notes = this.serviceNote
				.findByUsername(auth.getName(), 
						PageRequest.of(page, size, Sort.by("id").descending()));
		
		return new ResponseEntity<Page<Note>>(notes, HttpStatus.OK);
	}
	
	@GetMapping(value = AppHelper.PREFIX_NOTE)
	public ResponseEntity<Collection<Note>> getNotesByUserAuth (Principal auth)
	{
		Collection<Note> notes = this.serviceNote
				.findByUsername(auth.getName());
		
		return new ResponseEntity<Collection<Note>>(notes, HttpStatus.OK);
	}
	
	@GetMapping(value = AppHelper.PREFIX_NOTE + "/{id}")
	public ResponseEntity<Note> getNoteByById (@PathVariable Integer id)
	{
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.body(this.serviceNote.findById(id));
	}
	
	@PostMapping(AppHelper.PREFIX_NOTE)
	public ResponseEntity<Note> save (@RequestBody Note note, Principal auth)
	{
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(this.serviceNote.save(note, auth.getName()));
	}
			
	@DeleteMapping(AppHelper.PREFIX_NOTE + "/{id}")
	public ResponseEntity<?> destroy (@PathVariable Integer id)
	{
		this.serviceNote.delete(id);
		return ResponseEntity
				.ok("{\"message\":\"Nota borrada.\"}");
	}
}
