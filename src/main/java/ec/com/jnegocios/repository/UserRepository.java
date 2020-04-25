package ec.com.jnegocios.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.jnegocios.entity.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, Integer>{
	
	UserAccount findByEmail(String email);
	
	Collection<UserAccount> findByEnabledTrue();
	
	Collection<UserAccount> findByEnabledFalse();
}
