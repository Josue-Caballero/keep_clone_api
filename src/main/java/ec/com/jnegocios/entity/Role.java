package ec.com.jnegocios.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "roles")
@JsonInclude(Include.NON_NULL)
public class Role implements Serializable {

	private static final long serialVersionUID = -6110046546369481631L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 30, message = "El rol debe ser mayor a 3 y menor a 30 caracteres.")
	@NotEmpty(message = "El rol es obligatorio.")
	private String name;
	
	@ManyToOne
	@JsonIgnoreProperties({"roles", "notes", "tags"})
	private UserAccount user;
	
	public Role() {}

	public Role(int id,
			@Size(min = 3, max = 30, message = "El rol debe ser mayor a 3 y menor a 30 caracteres.") @NotEmpty(message = "El rol es obligatorio.") String name,
			UserAccount user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserAccount getUser() {
		return user;
	}

	public void setUser(UserAccount user) {
		this.user = user;
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
		Role other = (Role) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", user=" + user + "]";
	}
	
}
