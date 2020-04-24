package ec.com.jnegocios.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.jnegocios.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	User findByEmail(String email);
	
	Collection<User> findByEnabledTrue();
	
	Collection<User> findByEnabledFalse();
}
