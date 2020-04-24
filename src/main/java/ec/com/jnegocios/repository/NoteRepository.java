package ec.com.jnegocios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.jnegocios.entity.Note;

public interface NoteRepository extends JpaRepository<Note, Integer>{
		
}
