package edu.ort.da.obligatorio.DTOs.Vehiculo;

import lombok.Data;

@Data
public class VehiculoDTO {

	private String matricula;

	private String color;

	private String categoriaVehiculo;

	private String modelo;

	private int cantidadTransitos;

	private double montoTotal;

	public VehiculoDTO() {

	}

}
