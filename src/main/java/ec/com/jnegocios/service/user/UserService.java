package ec.com.jnegocios.service.user;

import ec.com.jnegocios.entity.UserAccount;

/**
 * UserService
 * Service for transactions users
 */
public interface UserService {
	
	/**
	 * Get one user for username
	 * @return One User
	 * @throws NotFoundException
	 * @param username for user
	 */
	UserAccount findByUsername (String username);
	
}
