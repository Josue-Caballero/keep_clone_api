package ec.com.jnegocios.service.tag;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.jnegocios.entity.Tag;
import ec.com.jnegocios.entity.UserAccount;
import ec.com.jnegocios.exception.ConflictException;
import ec.com.jnegocios.exception.NotFoundException;
import ec.com.jnegocios.repository.TagRepository;
import ec.com.jnegocios.repository.UserRepository;

@Service("tagService")
public class TagManager implements TagService {
	
	@Autowired
	private TagRepository repoTag;
	
	@Autowired
	private UserRepository repoUser;
	
	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findAll () {
		return repoTag.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Tag> findAll (Pageable pageable) {
		return repoTag.findAll(pageable);
	}

	@Transactional(readOnly = true)
	@Override
	public Tag findById (Integer id) {
		return repoTag.findById(id)
				.orElseThrow(() -> new NotFoundException("No se ha encontrado la etiqueta con id " + id));
	}

	@Transactional
	@Override
	public Tag save (Tag tag, String username) {
		if(username.isEmpty())
			throw new ConflictException("Usuario no autenticado.");
		
		String _username = username.toLowerCase();
		UserAccount user = repoUser.findByUsername(_username);
		
		if(user == null)
			throw new NotFoundException("El usuario '"+ username +"' no existe.");
		
		String nameTag = tag.getName().toLowerCase();
		Tag _tag = this.repoTag.findByName(nameTag);
		
		if(_tag != null)
			throw new ConflictException("La etiqueta con nombre '" + nameTag +"' ya existe.");		
		
		tag.setUser(user);
		tag.setName(nameTag);
		return this.repoTag.save(tag);
	}

	@Transactional
	@Override
	public Tag update (Tag tag, Integer id) {
		Tag tmpTag = this.repoTag.findById(id).orElse(null);
		
		if(tmpTag == null)
			throw new NotFoundException("El tag con Id "+ id +" no existe");

		String nameTag = tag.getName().toLowerCase();
		Tag _tag = this.repoTag.findByName(nameTag, id);
		
		if(_tag != null)
			throw new ConflictException("La etiqueta con nombre '" + nameTag +"' ya esta en uso.");
		
		tag.setId(id);
		tag.setUser(tmpTag.getUser());
		tag.setName(nameTag);
		return this.repoTag.save(tag);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUser(int user_id) {
		return this.repoTag.findByUser_Id(user_id);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUserDisable(int user_id) {
		return this.repoTag.findByUser_EnabledFalseAndUser_Id(user_id);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUserEnable(int user_id) {
		return this.repoTag.findByUser_EnabledTrueAndUser_Id(user_id);
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Tag tag = this.repoTag.findById(id).orElse(null);
		if(tag == null) 
			throw new NotFoundException("No se ha encontrado la etiqueta con id " + id);
		
		this.repoTag.deleteById(id);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUsername(String username) {
		return this.repoTag.findByUser_UsernameOrderByNameAsc(username);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUserDisable(String username) {
		return this.repoTag.findByUser_EnabledFalseAndUser_Username(username);
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUserEnable(String username) {
		return this.repoTag.findByUser_EnabledTrueAndUser_Username(username);
	}

}
