package edu.ort.da.obligatorio.Modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Tarifa {
	private double monto;

	private CategoriaVehiculo categoriaVehiculo;

}
