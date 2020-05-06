package ec.com.jnegocios.service.tag;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ec.com.jnegocios.entity.Tag;

/**
 * TagService
 * Service for transactions tags
 */
public interface TagService {
	
	/**
	 * Get All Tags
	 * @return all tags ordered
	 */
	Collection<Tag> findAll ();
	
	/**
	 * Get All Tags paginated
	 * @return all tags for page
	 * @param pageable object
	 */
	Page<Tag> findAll(Pageable pageable);
	
	/**
	 * Get one tag for Id
	 * @return One Tag
	 * @throws NotFoundException
	 * @param id for tag
	 */
	Tag findById (Integer id);
	
	/**
	 * Save a new tag
	 * @return a new tag
	 * @throws Exception
	 * @param a new Tag to create
	 */
	Tag save(Tag tag, String username);
	
	/**
	 * Save a changes to tag
	 * @return tag
	 * @throws Exception
	 * @param a Tag and id for update
	 */
	Tag update(Tag tag, Integer id);
	
	/**
	 * Get collection tags for user
	 * @return collection tags
	 * @throws NotFoundException
	 * @param user_id
	 */ 
	Collection<Tag> findByUser (int user_id);
	
	/**
	 * Get collection tags for user
	 * @return collection tags
	 * @param username
	 */ 
	Collection<Tag> findByUsername (String username);
	
	/**
	 * Get collection tags for user disabled
	 * @return collection tags
	 * @param user_id
	 */ 
	Collection<Tag> findByUserDisable (int user_id);
	
	/**
	 * Get collection tags for user disabled
	 * @return collection tags
	 * @param username
	 */ 
	Collection<Tag> findByUserDisable (String username);
	
	
	/**
	 * Get collection tags for user enabled
	 * @return collection tags
	 * @param user_id
	 */ 
	Collection<Tag> findByUserEnable (int user_id);
	
	/**
	 * Get collection tags for user enabled
	 * @return collection tags
	 * @param username
	 */ 
	Collection<Tag> findByUserEnable (String username);
	
	/**
	 * Delete tag by id
	 * @return void
	 * @throws NotFoundException
	 * @param user_id
	 */ 
	void delete(Integer id);
}
