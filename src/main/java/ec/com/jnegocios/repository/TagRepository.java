package ec.com.jnegocios.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ec.com.jnegocios.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	@Query("SELECT t FROM Tag t WHERE t.name = ?1 AND t.id <> ?2")
	Tag findByName (String name, int current_id);
	
	Tag findByName (String name);
	
	Collection<Tag> findByUser_Id(int id);
	
	Collection<Tag> findByUser_UsernameOrderByNameAsc(String username);
	
	Collection<Tag> findByUser_EnabledFalseAndUser_Id(int id);
	
	Collection<Tag> findByUser_EnabledFalseAndUser_Username(String username);
	
	Collection<Tag> findByUser_EnabledTrueAndUser_Id(int id);
	
	Collection<Tag> findByUser_EnabledTrueAndUser_Username(String username);
	
}
