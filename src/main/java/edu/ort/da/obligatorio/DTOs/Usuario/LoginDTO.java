package edu.ort.da.obligatorio.DTOs.Usuario;

import lombok.Data;

@Data
public class LoginDTO {

	private String cedula;

	private String contrasena;

	public LoginDTO(String cedula, String contrasena) {

	}

	public LoginDTO() {

	}
}
