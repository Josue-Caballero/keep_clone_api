package ec.com.jnegocios.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.jnegocios.entity.Tag;

public interface TagRepository extends JpaRepository<Tag, Integer>{
	
	Collection<Tag> findByUser_Id(int id);
	
	Collection<Tag> findByUser_EnabledFalseAndUser_Id(int id);
	
	Collection<Tag> findByUser_EnabledTrueAndUser_Id(int id);
}
