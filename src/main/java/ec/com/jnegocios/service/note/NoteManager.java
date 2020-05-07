package ec.com.jnegocios.service.note;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.ConflictException;
import ec.com.jnegocios.exception.NotFoundException;
import ec.com.jnegocios.repository.NoteRepository;
import ec.com.jnegocios.repository.UserRepository;

@Service("noteService")
public class NoteManager implements NoteService {
	
	@Autowired
	private NoteRepository repoNote;

	@Autowired
	private UserRepository repoUser;
	
	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findAll() {
		return this.repoNote.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Note> findAll(Pageable pageable) {
		return this.repoNote.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Note findById(Integer id) {
		return this.repoNote.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado una nota con Id " + id));
	}

	@Transactional
	@Override
	public Note save(Note note, String username) {
		if(username.isEmpty())
			throw new ConflictException("Usuario no autenticado.");
		
		UserAccount user = this.repoUser.findByUsername(username.toLowerCase());
		
		if(user == null)
			throw new NotFoundException("No se ha encontrado el usuario '"+ username+ "'");
		
		note.setUser(user);
		return this.repoNote.save(note);
	}

	@Transactional
	@Override
	public Note update(Note note, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findByUser(int user_id) {
		return this.repoNote.findByUser_Id(user_id);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findByUsername(String username) {
		return this.repoNote.findByUser_Username(username);
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Note> findByUsername(String username, Pageable pageable) {
		return this.repoNote.findByUser_Username(username, pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findByFiledAndUsername(String username) {
		return this.repoNote.findByFiledTrueAndUser_Username(username);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findByNotFiledAndUsername(String username) {
		return this.repoNote.findByFiledFalseAndUser_Username(username);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		this.repoNote.findById(id)
			.map(note -> { repoNote.deleteById(note.getId()); return note; })
			.orElseThrow(() -> new NotFoundException("No se ha encontrado una nota con Id " + id));
	}

}
