package ec.com.jnegocios.repository;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.entity.dto.UserAccountDTO;

public interface UserRepository extends JpaRepository<UserAccount, Integer>{
	
	UserAccount findByEmail(String email);
	
	UserAccount findByUsername(String username);
	
	Collection<UserAccount> findByEnabledTrue();
	
	Collection<UserAccount> findByEnabledFalse();
	
	Collection<UserAccount> findByDarkmodeTrue();
	
	Collection<UserAccount> findByDarkmodeFalse();
	
	@Query("SELECT new ec.com.jnegocios.entity.dto.UserAccountDTO(u.id, u.name, u.lastname, u.username, u.email, u.storageUrl, u.enabled, "
			+ "u.darkmode, u.token_exp, u.createdAt, u.updatedAt) FROM UserAccount u ORDER BY id DESC")
	Page<UserAccountDTO> findAllDTO(Pageable pageable);
	
	@Query("SELECT new ec.com.jnegocios.entity.dto.UserAccountDTO(u.id, u.name, u.lastname, u.username, u.email, u.storageUrl, u.enabled, "
			+ "u.darkmode, u.token_exp, u.createdAt, u.updatedAt) FROM UserAccount u WHERE u.email = ?1")
	UserAccountDTO findUserDTOByEmail(String email);
	
	@Query("SELECT new ec.com.jnegocios.entity.dto.UserAccountDTO(u.id, u.name, u.lastname, u.username, u.email, u.storageUrl, u.enabled, "
			+ "u.darkmode, u.token_exp, u.createdAt, u.updatedAt) FROM UserAccount u WHERE u.username = ?1")
	UserAccountDTO findUserDTOByUsername(String username);
	
	@Query("SELECT new ec.com.jnegocios.entity.dto.UserAccountDTO(u.id, u.name, u.lastname, u.username, u.email, u.storageUrl, u.enabled, "
			+ "u.darkmode, u.token_exp, u.createdAt, u.updatedAt) FROM UserAccount u WHERE u.id = ?1")
	UserAccountDTO findUserDTOById(int id);
}
