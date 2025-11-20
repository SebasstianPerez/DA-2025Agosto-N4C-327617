package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Notificacion {

    private String mensaje;

	private String descripcion;

	private LocalDateTime fecha;

	public Notificacion(String descripcion, String mensaje) {
		this.descripcion = descripcion;
		this.mensaje = mensaje;
		this.fecha = LocalDateTime.now();
    }

}
