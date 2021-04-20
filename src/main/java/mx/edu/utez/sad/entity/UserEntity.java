package mx.edu.utez.sad.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "El nombre de usuario no debe ser estar vacío")
	@Size(min = 3, max = 15, message = "El nombre de usuario debe tener entre 3 y 15 caracteres")
	@Column(nullable = false, unique = true)
	private String username;

	@NotBlank(message = "La contraseña no debe ser estar vacía")
	@Size(min = 8, message = "La contraseña debe ser mayor o igual a 8 caracteres")
	@Column(nullable = false)
	private String password;

	@Column(name = "status")
	private boolean status;

	@Column(name = "last_login")
	private Date lastLogin;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = false)
	private RoleEntity role;

	private String userB;

	private String host;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public String getUserB() {
		return userB;
	}

	public void setUserB(String userB) {
		this.userB = userB;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
