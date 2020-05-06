package ec.com.jnegocios.service.tag;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.com.jnegocios.entity.Tag;
import ec.com.jnegocios.exception.NotFoundException;
import ec.com.jnegocios.repository.TagRepository;

@Service("taskService")
public class TagManager implements TagService {
	
	@Autowired
	private TagRepository repoTag;
	
	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findAll() {
		return repoTag.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Page<Tag> findAll(Pageable pageable) {
		return repoTag.findAll(pageable);
	}
	
	@Transactional(readOnly = true)
	@Override
	public Tag findById(Integer id) {
		Tag tag = repoTag.findById(id).orElse(null);
		if(tag == null) 
			throw new NotFoundException("No se ha encontrado la etiqueta con id " + id);
		return tag;
	}

	@Override
	public Tag save(Tag tag) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUser(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUserDisable(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(readOnly = true)
	@Override
	public Collection<Tag> findByUserEnable(int user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional
	@Override
	public void delete(Integer id) {
		Tag tag = this.repoTag.findById(id).orElse(null);
		if(tag == null) 
			throw new NotFoundException("No se ha encontrado la etiqueta con id " + id);
		
		this.repoTag.deleteById(id);
	}

	@Override
	public Collection<Tag> findByUsername(String username) {		
		return this.repoTag.findByUser_Username(username);
	}

}
