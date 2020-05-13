package ec.com.jnegocios.api;

import java.security.Principal;
import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ec.com.jnegocios.entity.Tag;
import ec.com.jnegocios.service.tag.TagService;
import ec.com.jnegocios.util.AppHelper;
import ec.com.jnegocios.util.JSONResponse;

@RestController
@RequestMapping(AppHelper.PREFIX_TAG)
public class TagController {
	
	@Autowired
	private TagService serviceTag;
		
	/** 
	 * END POINT GET ALL USER'S TAGS
	 * /tags
	 */
	@GetMapping(produces = AppHelper.JSON)
	public ResponseEntity<Collection<Tag>> findTagsByUserAuth (Principal auth) {
		Collection<Tag> tags = serviceTag.findByUsername(auth.getName());
		return ResponseEntity
			.ok(tags);
	}
	
	/** 
	 * END POINT GET BY ID 
	 * /tags/:id
	 */
	@GetMapping(value="/{id}", produces = AppHelper.JSON)
	public ResponseEntity<Tag> show (@PathVariable Integer id) {
		return ResponseEntity
			.status(HttpStatus.FOUND)
			.body(serviceTag.findById(id));
	}
	
	/** 
	 * END POINT POST NEW USER'S TAG
	 * /tags
	 */
	@PostMapping(produces = AppHelper.JSON)
	public ResponseEntity<Tag> save (@Valid @RequestBody Tag tag, Principal auth)
	{
		Tag _tag = this.serviceTag.save(tag, auth.getName());
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(_tag);
	}
	
	/** 
	 * END POINT UPDATE USER'S TAG
	 * /tags/:id
	 */
	@RequestMapping(value="/{id}", method = {RequestMethod.PATCH, RequestMethod.PUT}, produces = AppHelper.JSON)
	public ResponseEntity<Tag> update (@Valid @RequestBody Tag tag, @PathVariable int id)
	{
		Tag _tag = this.serviceTag.update(tag, id);
		return ResponseEntity
			.ok(_tag);
	}
	
	/** 
	 * END POINT DELETE TAG
	 * /tags/:id
	 */
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE, produces = AppHelper.JSON)
	public ResponseEntity<?> destroy (@PathVariable int id)
	{
		this.serviceTag.delete(id);
		return ResponseEntity.ok( JSONResponse.fromGeneralTemplate(
			AppHelper.PREFIX_TAG.concat("/" + String.valueOf(id)), 
			"Tag eliminado.", 
			200).getBody() );
	}
}
