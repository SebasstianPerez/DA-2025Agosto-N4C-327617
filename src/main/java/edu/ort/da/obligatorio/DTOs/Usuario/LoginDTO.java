package edu.ort.da.obligatorio.DTOs.Usuario;

import lombok.Data;

@Data
public class LoginDTO {

	private String nombre;

	private String contrasena;

	public LoginDTO(String nombre, String contrasena) {

	}

	public LoginDTO() {

	}
}
