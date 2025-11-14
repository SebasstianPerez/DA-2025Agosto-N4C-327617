package edu.ort.da.obligatorio.Modelo;

import lombok.Data;

@Data
public class Transito {

	private Vehiculo vehiculo;

	private PropietarioBonificacion propietarioBonificacion;

	private double montoPagado;

}
