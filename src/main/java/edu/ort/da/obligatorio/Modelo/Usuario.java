package edu.ort.da.obligatorio.Modelo;

import lombok.Data;
import lombok.Getter;

@Data
public abstract class Usuario {

	private String cedulaDeIdentidad;

	//except
	@Getter()
	private String contrasena;

	private int nombreCompleto;

}
