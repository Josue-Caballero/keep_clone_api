package ec.com.jnegocios.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.jnegocios.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{
	
	Collection<Note> findByFiledTrueAndUser_Id(int id);
	
	Collection<Note> findByFiledTrueAndUser_Username(String username);
	
	Collection<Note> findByFiledFalseAndUser_Id(int id);
	
	Collection<Note> findByFiledFalseAndUser_Username(String username);
	
	Collection<Note> findByUser_Id(int id);
	
	Collection<Note> findByUser_Username(String username);
	
	Page<Note> findByUser_Username(String username, Pageable pageable);
	
	Collection<Note> findTop15ByUser_UsernameAndIdGreaterThanOrderByIdAsc(String username, Integer id);
}
