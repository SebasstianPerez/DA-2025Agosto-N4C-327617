package edu.ort.da.obligatorio.DTOs.Usuario;

import lombok.Data;

@Data
public class PropietarioDTO {

	private String cedulaDeIdentidad;

	private String contrasena;

	private String nombre;

	private String apellido;

	private int estado;

	public PropietarioDTO(String cedulaDeIdentidad, String contrasena, String nombre, String apellido, int estado) {

	}

	public PropietarioDTO() {

	}

}
