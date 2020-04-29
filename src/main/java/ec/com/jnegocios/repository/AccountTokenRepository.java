
package ec.com.jnegocios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ec.com.jnegocios.entity.AccountToken;
import ec.com.jnegocios.util.enums.EnumToken;

public interface AccountTokenRepository  
	extends JpaRepository<AccountToken, Integer> {

	AccountToken findByToken(String token);
	
	@Query("SELECT at FROM AccountToken at WHERE at.user.id = ?1 AND at.typetoken = ?2")
	AccountToken findByUserId(int userId, EnumToken typetoken);

	AccountToken findByTypetoken(EnumToken typetoken);
	
}
