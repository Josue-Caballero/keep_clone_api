package ec.com.jnegocios.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import ec.com.jnegocios.util.enums.EnumToken;

@Entity
@Table(name = "account_token")
@JsonInclude(Include.NON_NULL)
public class AccountToken implements Serializable {

	private static final long serialVersionUID = -2820331992177022446L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JsonIgnoreProperties({"registrationTokens", "notes", "tags"})
	private UserAccount user;
	
	@NotEmpty(message = "El token es requerido.")
	private String token;
	
	@NotEmpty(message = "El valor de la expiración es requerido.")
	private Long expiration;
	
	private boolean validate;
	
	@NotNull(message = "El tipo de token no puede ser nulo.")
	@Column(name = "type_token")
	private EnumToken typetoken;
	
	public AccountToken() {}

	public AccountToken(int id, UserAccount user, @NotEmpty(message = "El token es requerido.") String token,
			@NotEmpty(message = "El valor de la expiración es requerido.") Long expiration, boolean validate,
			@NotNull(message = "El tipo de token no puede ser nulo.") EnumToken typetoken) {
		super();
		this.id = id;
		this.user = user;
		this.token = token;
		this.expiration = expiration;
		this.validate = validate;
		this.typetoken = typetoken;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getExpiration() {
		return expiration;
	}

	public void setExpiration(Long expiration) {
		this.expiration = expiration;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}
	
	public EnumToken getTypetoken() {
		return typetoken;
	}

	public void setTypetoken(EnumToken typetoken) {
		this.typetoken = typetoken;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AccountToken other = (AccountToken) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AccountToken [id=" + id + ", user=" + user + ", token=" + token + ", expiration=" + expiration
				+ ", validate=" + validate + ", typetoken=" + typetoken + "]";
	}
	
}
