package mx.edu.utez.sad.entity;

import java.io.Serializable;
import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "brand")
public class BrandEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El nombre de la marca no debe estar vacío")
	@Size(min = 3, max = 50, message = "El nombre de la marca debe tener mínimo 3 caracteres y máximo 50")
	@Column(nullable = false, length = 50)
	private String name;

	
	private boolean status;

	private String userB;
	
	private String host;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getUser() {
		return userB;
	}
	public void setUser(String userB) {
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
