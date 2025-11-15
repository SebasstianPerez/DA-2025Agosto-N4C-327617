package edu.ort.da.obligatorio.Modelo;

import lombok.Data;

@Data
public class Vehiculo {

	private Long id;

	private String matricula;

	private CategoriaVehiculo categoria;

	private String color;

	private String modelo;

	private double montoTotal;

	private int cantidadTransitos;

	private Propietario propietario;

	public Vehiculo() {

	}

	public Vehiculo(String matricula, CategoriaVehiculo categoria, String color, String modelo,
			Propietario propietario) {
		this.matricula = matricula;
		this.categoria = categoria;
		this.color = color;
		this.modelo = modelo;
		this.propietario = propietario;
		this.montoTotal = 0.0;
		this.cantidadTransitos = 0;
	}

	

}
