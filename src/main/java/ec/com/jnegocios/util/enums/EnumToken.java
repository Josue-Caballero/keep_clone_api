package ec.com.jnegocios.util.enums;

public enum EnumToken {
	
	/**
	 * {@enum registration}.
	 * 
	 */
	REGISTRATION("registration"), 
	
	
	/**
	 * {@enum reset}.
	 * 
	 */
	RESET("reset"),
	
	/**
	 * {@enum unsubscribe}.
	 * 
	 */
	UNSUBSCRIBE("unsubscribe");
	
	private final String value;
	
	private EnumToken(String value) {
		this.value = value;
	}
	
	/**
	 * Return the string value of this enum token.
	 */
	public String value() {
		return this.value;
	}
	
}
