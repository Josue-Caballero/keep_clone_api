package ec.com.jnegocios.entity.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class UserAccountDTO implements Serializable {

	private static final long serialVersionUID = 8083141750572890922L;

	private int id;
	
	private String name;
	
	private String lastname;
	
	private String username;
	
	private String email;
	
	private String storageUrl;
	
	private boolean enabled;
	
	private boolean darkmode;
	
	private Long token_exp;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;
		
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updatedAt;
		
	public UserAccountDTO() {}

	public UserAccountDTO(int id, String name, String lastname, String username, String email, String storageUrl,
			boolean enabled, boolean darkmode, Long token_exp, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.email = email;
		this.storageUrl = storageUrl;
		this.enabled = enabled;
		this.darkmode = darkmode;
		this.token_exp = token_exp;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
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

	public Long getToken_exp() {
		return token_exp;
	}

	public void setToken_exp(Long token_exp) {
		this.token_exp = token_exp;
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

	@Override
	public String toString() {
		return "UserAccountRoleDTO [id=" + id + ", name=" + name + ", lastname=" + lastname + ", username=" + username
				+ ", email=" + email + ", storageUrl=" + storageUrl + ", enabled=" + enabled + ", darkmode=" + darkmode
				+ ", token_exp=" + token_exp + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
}
