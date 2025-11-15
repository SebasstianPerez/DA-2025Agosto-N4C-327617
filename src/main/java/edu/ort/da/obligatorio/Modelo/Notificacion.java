package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Notificacion {
	private String mensaje;

	private LocalDateTime fecha;

	private Propietario propietario;

}
