package mx.edu.utez.sad.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AuthenticationDto {
	
	@NotBlank(message = "El nombre de usuario no puede estar vacío.")
	@Size(min = 3, message = "El nombre de usuario debe contener más de 3 caracteres.")
	private String username;
	
	@NotBlank(message = "La contraseña no puede estar vacía.")
	@Size(min = 3, message = "La contraseña debe contener más de 3 caracteres.")
	private String password;

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
	
	
}
