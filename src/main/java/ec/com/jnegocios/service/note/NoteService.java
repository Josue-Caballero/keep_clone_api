package ec.com.jnegocios.service.note;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ec.com.jnegocios.entity.Note;

/**
 * NoteService
 * Service for transactions notes
 */
public interface NoteService {
	
	/**
	 * Get All Notes
	 * @return all notes ordered
	 */
	Collection<Note> findAll ();
	
	/**
	 * Get All Notes paginated
	 * @return all Notes for page
	 * @param pageable object
	 */
	Page<Note> findAll(Pageable pageable);
	
	/**
	 * Get one Note for Id
	 * @return One Note
	 * @throws NotFoundException
	 * @param id for Note
	 */
	Note findById (Integer id);
	
	/**
	 * Save a new Note
	 * @return a new Note
	 * @throws Exception
	 * @param a new Note to create
	 */
	Note save(Note note, String username);
	
	/**
	 * Save a changes to Note
	 * @return Note
	 * @throws Exception
	 * @param a Note and id for update
	 */
	Note update(Note note, Integer id);
	
	/**
	 * Get collection Notes for user
	 * @return collection Notes
	 * @throws NotFoundException
	 * @param user_id
	 */ 
	Collection<Note> findByUser (int user_id);
	
	/**
	 * Get collection Notes for user
	 * @return collection Notes
	 * @param username
	 */ 
	Collection<Note> findByUsername (String username);
		
	/**
	 * Get collection Notes for user and since
	 * @return collection Notes
	 * @param username, since
	 */ 
	Collection<Note> findByUsernameSince (String username, Integer since);
	
	/**
	 * Get collection paginated Notes for user
	 * @return collection Notes for page
	 * @param username, pageable
	 */ 
	Page<Note> findByUsername (String username, Pageable pageable);
	
	/**
	 * Get collection Notes filed for user
	 * @return collection Notes
	 * @param username
	 */ 
	Collection<Note> findByFiledAndUsername (String username);
	
	/**
	 * Get collection Notes not filed for user
	 * @return collection Notes
	 * @param username
	 */ 
	Collection<Note> findByNotFiledAndUsername (String username);
	
	/**
	 * Delete Note by id
	 * @return void
	 * @throws NotFoundException
	 * @param note_id
	 */ 
	void delete(Integer id);
}
