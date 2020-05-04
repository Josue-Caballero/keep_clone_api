
package ec.com.jnegocios.service.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.repository.UserRepository;

/**
 * UserAuthDetailsService
 */
@Service
public class UserAuthDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String username)
		throws UsernameNotFoundException {
		
		UserAccount account = accountRepository.findByUsername(username);

		if( account != null ) {
			
			List<GrantedAuthority> roles = new ArrayList(account.getRoles());

			return ( new User(
			
				account.getUsername(),
				account.getPassword(),
				account.isEnabled(),
				true, true, true,
				roles
			
			));

		}

		throw new UsernameNotFoundException("The username does not exist");
	
	}
	
}
