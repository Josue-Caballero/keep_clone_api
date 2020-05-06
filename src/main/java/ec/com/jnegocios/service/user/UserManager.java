package ec.com.jnegocios.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.NotFoundException;
import ec.com.jnegocios.repository.UserRepository;

@Service("userService")
public class UserManager implements UserService {

	@Autowired
	private UserRepository repoUser;
	
	@Override
	public UserAccount findByUsername(String username) {
		UserAccount user = this.repoUser.findByUsername(username);
		if(user == null)
			throw new NotFoundException("No se ha encontrado el usuario '"+ username +"'");
		
		return user;
	}
	
}
