package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PropietarioBonificacion {

	private Propietario propietario;

	private Puesto puesto;

	private Bonificacion bonificacion;

	private LocalDateTime fechaAsignada;

	public PropietarioBonificacion(Propietario propietario, Puesto puesto, Bonificacion bonificacion) {
		this.propietario = propietario;
		this.puesto = puesto;
		this.bonificacion = bonificacion;
		this.fechaAsignada = LocalDateTime.now();
	}
}
