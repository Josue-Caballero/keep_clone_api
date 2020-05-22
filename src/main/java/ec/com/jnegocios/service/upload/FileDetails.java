
package ec.com.jnegocios.service.upload;

import ec.com.jnegocios.util.IdentifierGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Data
public class FileDetails {

	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private final String RESOURCES_URL = "https://nyc3.digitaloceanspaces.com/j.negocios.ecuador.usercontent/keep-clone/";

	private String originalName;

	private String uniqueId;

	private String storageUrl;

	private String extension;

	public FileDetails() {}
	
	public FileDetails(String originalName) {

		this.originalName = originalName;
		this.uniqueId = IdentifierGenerator.generateUniqueFileId();
		setExtension( 
			this.originalName.substring(this.originalName.lastIndexOf(".")) 
		);
		setStorageUrl( RESOURCES_URL + uniqueId );

	}
	
}
