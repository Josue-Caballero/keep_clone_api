
package ec.com.jnegocios.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ec.com.jnegocios.entity.RegistrationToken;

public interface RegistrationTokenRepository  
	extends JpaRepository<RegistrationToken, Integer> {

	RegistrationToken findByToken(String token);
	
	@Query("select rt from RegistrationToken rt where rt.user.id = ?1")
	RegistrationToken findByUserId(int userId);

}
