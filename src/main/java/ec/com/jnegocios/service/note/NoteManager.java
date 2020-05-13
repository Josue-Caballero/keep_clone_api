package ec.com.jnegocios.service.note;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.ConflictException;
import ec.com.jnegocios.exception.ForbiddenException;
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
		Note note = this.repoNote.findById(id)
			.orElseThrow(() -> new NotFoundException("No se ha encontrado una nota con Id " + id));
		
		Authentication authUser = SecurityContextHolder
			.getContext().getAuthentication();
		
		if(!note.getUser().getUsername().equals(authUser.getName()))
			throw new ForbiddenException("No puedes acceder a este recurso.");
		
		return note;
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
		Note saveNote = this.repoNote.save(note);
		
		if(saveNote == null)
			throw new ConflictException("No se ha podido crear la nota, por favor intente luego.");
		
		return saveNote;
	}

	@Transactional
	@Override
	public Note update(Note note, Integer id) {
		Note _note = this.repoNote.findById(id)
			.orElseThrow(() -> new NotFoundException("No hemos encontrado una nota con un identificador válido."));

		String 
			title = note.getTitle(),
			description = note.getDescription(),
			color = note.getColor();
		Boolean filed = note.isFiled();

		if ( title != null ) {
			_note.setTitle(title); }

		if ( description != null ) {
			_note.setDescription( description ); }
		
		if ( filed != null ) {
			_note.setFiled( filed ); }
		
		if ( color != null ) {
			_note.setColor( color ); }
		
		if ( note.getTags() != null ) {
			_note.setTags( new HashSet<>(note.getTags()) ); }

		_note.setDeletedAt(null);

		Note updateNote = this.repoNote.save(_note);
		
		if(updateNote == null)
			throw new ConflictException("No se ha podido actualizar la nota, por favor intente luego.");
		
		return updateNote;
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
	public Collection<Note> findByNotFiledUsernameSince(String username, Integer since) {
		return this.repoNote.findTop10ByFiledFalseAndDeletedAtIsNullAndUser_UsernameAndIdGreaterThanOrderByIdAsc(username, since);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findByFiledAndUsernameSince(String username, Integer since) {
		return this.repoNote.findTop10ByFiledTrueAndDeletedAtIsNullAndUser_UsernameAndIdGreaterThanOrderByIdAsc(username, since);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Page<Note> findByUsername(String username, Pageable pageable) {
		return this.repoNote.findByUser_UsernameAndDeletedAtIsNull(username, pageable);
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

	@Transactional(readOnly = true)
	@Override
	public Collection<Note> findByUsernameSinceInTrash(String username, Integer since) {
		return this.repoNote.findTop10ByDeletedAtIsNotNullAndUser_UsernameAndIdGreaterThanOrderByIdAsc(username, since);
	}
	
	@Transactional
	@Override
	public void delete(Integer id) {
		this.repoNote.findById(id)
			.map(note -> { repoNote.deleteById(note.getId()); return note; })
			.orElseThrow(() -> new NotFoundException("No se ha encontrado una nota con Id " + id));
	}

	@Transactional
	@Override
	public void softDelete(Integer id) {
		this.repoNote.findById(id)
			.map(note -> { 
				note.setDeletedAt(LocalDateTime.now());
				repoNote.save(note); 
				return note; 
			})
			.orElseThrow(() -> new NotFoundException("No se ha encontrado una nota con Id " + id));
	}

}
