
package ec.com.jnegocios.util;

import java.util.Date;
import java.util.UUID;

public class IdentifierGenerator {

	public static String generateUniqueTokenId() {

		String uniqueId = String.format("%s-%s", UUID.randomUUID(), (new Date().getTime()));

		return uniqueId;

	}

	public static String generateUniqueFileId() {

		String uniqueId = String.format("%s-%s", UUID.randomUUID(), (new Date().getTime()));

		return uniqueId;

	}

}
