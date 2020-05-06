package ec.com.jnegocios.api.auth;

import java.security.Principal;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.com.jnegocios.entity.Tag;
import ec.com.jnegocios.service.tag.TagService;
import ec.com.jnegocios.util.AppHelper;

@RestController
@RequestMapping(AppHelper.PREFIX + "/tags")
public class TagController {
	
	@Autowired
	private TagService serviceTag;
	
	/** 
	 * END POINT GET ALL TAGS
	 * /api/tags
	 */
	@GetMapping
	public ResponseEntity<Collection<Tag>> index (Principal auth) {
		Collection<Tag> tags = serviceTag.findByUsername(auth.getName());
		return ResponseEntity
				.ok(tags);
	}
	
	/** 
	 * END POINT GET BY ID 
	 * /api/tags/:id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Tag> show (@PathVariable Integer id) {
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.body(serviceTag.findById(id));
	} 
}
