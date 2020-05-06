
package ec.com.jnegocios.api.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ec.com.jnegocios.entity.Note;

@RestController
public class NoteController {
	
	// Just a test to access the user no auth denied handler
	@GetMapping("/notes/{id}")
	public Note getNotes( @PathVariable int id) {

		Note testAuthNote = new Note();
		testAuthNote.setId(id);
		testAuthNote.setTitle("You have a valid token");

		return testAuthNote;

	}

	// Just a test to access the user auth denied handler
	@GetMapping("/premium-notes")
	public Note getPremiumNotes() {

		Note testAuthNote = new Note();
		testAuthNote.setTitle("You have a valid premium token");

		return testAuthNote;

	}
	
}
