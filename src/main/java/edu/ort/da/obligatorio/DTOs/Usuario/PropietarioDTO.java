package edu.ort.da.obligatorio.DTOs.Usuario;

import java.util.Collection;

import lombok.Data;

@Data
public class PropietarioDTO {

	private String cedulaDeIdentidad;

	private String contrasena;

	private String nombre;

	private String apellido;

	private String estado;

	private Collection<BonificacionAsignadaDTO> bonificaciones;

}
