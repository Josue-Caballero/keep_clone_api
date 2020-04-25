package ec.com.jnegocios.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "users")
@JsonInclude(Include.NON_NULL)
public class UserAccount implements Serializable {

	private static final long serialVersionUID = -612571457691264492L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Size(min = 3, max = 30, message = "El nombre debe ser mayor a 3 y menor a 30 caracteres.")
	@NotEmpty(message = "El nombre es obligatorio.")
	private String name;
	
	@Size(min = 3, max = 30, message = "El apellido debe ser mayor a 3 y menor a 30 caracteres.")
	@NotEmpty(message = "El apellido es obligatorio.")
	private String lastname;
	
	@Size(min = 3, max = 20, message = "El nombre de usuario debe ser mayor a 3 y menor a 20 caracteres.")
	@NotEmpty(message = "El nombre de usuario es obligatorio.")
	private String username;
	
	@Size(min = 10, max = 60, message = "El correo debe ser mayor a 10 y menor a 60 caracteres.")
	@NotEmpty(message = "El correo es obligatorio.")
	private String email;
	
	@NotEmpty(message = "La contraseña es obligatoria.")
	@JsonProperty(access = Access.WRITE_ONLY)
	private String password;
	
	private String photo;
	
	@Column(name = "storage_url")
	private String storageUrl;
	
	private boolean enabled;
	
	private boolean darkmode;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"user"})
	private RegistrationToken registrationToken;
	
	@Column(name = "created_at", updatable = false)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"user"})
	private Collection<Role> roles;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"user", "tags"})
	private Collection<Note> notes;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"user", "notes"})
	private Collection<Tag> tags;
	
	
	public UserAccount(int id,
			@Size(min = 3, max = 30, message = "El nombre debe ser mayor a 3 y menor a 30 caracteres.") @NotEmpty(message = "El nombre es obligatorio.") String name,
			@Size(min = 3, max = 30, message = "El apellido debe ser mayor a 3 y menor a 30 caracteres.") @NotEmpty(message = "El apellido es obligatorio.") String lastname,
			@Size(min = 3, max = 20, message = "El nombre de usuario debe ser mayor a 3 y menor a 20 caracteres.") @NotEmpty(message = "El nombre de usuario es obligatorio.") String username,
			@Size(min = 10, max = 60, message = "El correo debe ser mayor a 10 y menor a 60 caracteres.") @NotEmpty(message = "El correo es obligatorio.") String email,
			@NotEmpty(message = "La contraseña es obligatoria.") String password, String photo, String storageUrl,
			boolean enabled, boolean darkmode, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.password = password;
		this.photo = photo;
		this.storageUrl = storageUrl;
		this.enabled = enabled;
		this.darkmode = darkmode;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}
	
	public UserAccount() {
		this.roles = new ArrayList<Role>();
		this.notes = new ArrayList<Note>();
		this.tags = new ArrayList<Tag>();
		this.updatedAt = LocalDateTime.now();
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getStorageUrl() {
		return storageUrl;
	}

	public void setStorageUrl(String storageUrl) {
		this.storageUrl = storageUrl;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isDarkmode() {
		return darkmode;
	}

	public void setDarkmode(boolean darkmode) {
		this.darkmode = darkmode;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
		
	public Collection<Note> getNotes() {
		return notes;
	}

	public void setNotes(Collection<Note> notes) {
		this.notes = notes;
	}

	public Collection<Tag> getTags() {
		return tags;
	}

	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}
	
	public RegistrationToken getRegistrationToken() {
		return registrationToken;
	}

	public void setRegistrationToken(RegistrationToken registrationToken) {
		this.registrationToken = registrationToken;
	}
	
	
	

	public void addRole (Role role)
	{
		this.roles.add(role);
	}
	
	public void clearRoles ()
	{
		this.roles.clear();
	}
	
	public void removeRole (Role role)
	{
		this.roles.remove(role);
	}
	
	public void addNote (Note note)
	{
		this.notes.add(note);
	}
	
	public void clearNotes ()
	{
		this.notes.clear();
	}
	
	public void removeNote (Note note)
	{
		this.notes.remove(note);
	}
	
	public void addTag (Tag tag)
	{
		this.tags.add(tag);
	}
	
	public void clearTags ()
	{
		this.tags.clear();
	}
	
	public void removeTag (Tag tag)
	{
		this.tags.remove(tag);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		UserAccount other = (UserAccount) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserAccount [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username
				+ ", email=" + email + ", password=" + password + ", photo=" + photo + ", storageUrl=" + storageUrl
				+ ", enabled=" + enabled + ", darkmode=" + darkmode + ", registrationToken=" + registrationToken
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
