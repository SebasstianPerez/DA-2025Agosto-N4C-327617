package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Transito {

	private Long Id;

	private LocalDateTime fecha = LocalDateTime.now();
	
	private Vehiculo vehiculo;

	private PropietarioBonificacion propietarioBonificacion;

	private Puesto puesto;

	private String cedula;

	private double montoTarifaBase;

	private double montoBonificacion;

	private double montoPagado;

}
