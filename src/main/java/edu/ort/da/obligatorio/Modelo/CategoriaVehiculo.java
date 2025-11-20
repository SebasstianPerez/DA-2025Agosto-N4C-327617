package edu.ort.da.obligatorio.Modelo;

import lombok.Data;

@Data
public class CategoriaVehiculo {
    private String Nombre;

	public CategoriaVehiculo(String nombre){
		this.Nombre = nombre;
	}
}
