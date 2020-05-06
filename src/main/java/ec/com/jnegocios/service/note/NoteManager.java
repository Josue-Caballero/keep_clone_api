package ec.com.jnegocios.service.note;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.Note;
import ec.com.jnegocios.repository.NoteRepository;

@Service("noteService")
public class NoteManager implements NoteService {
	
	@Autowired
	private NoteRepository repoNote;

	@Override
	public Collection<Note> findAll() {
		return this.repoNote.findAll();
	}

	@Override
	public Page<Note> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note save(Note note, String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Note update(Note note, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note> findByUser(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note> findByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note> findByFiledAndUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Note> findByNotFiledAndUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}
	
}
