package ec.com.jnegocios.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.jnegocios.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{
	
}
