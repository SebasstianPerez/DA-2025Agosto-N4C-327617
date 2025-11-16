package edu.ort.da.obligatorio.Modelo;

import java.util.ArrayList;
import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Puesto {
	private Long id;

	private String nombre;

	private String direccion;

	private Collection<Tarifa> tarifas;

	private Collection<PropietarioBonificacion> propietarioBonificacion;

	public Puesto(String nombre, String direccion, Collection<Tarifa> tarifas) {
		this.nombre = nombre;
		this.direccion = direccion;
		this.tarifas = tarifas;
		this.propietarioBonificacion = new ArrayList<>();
	}

	public void addTarifa(Tarifa tarifa) {
        this.tarifas.add(tarifa);
    }

	public void addPropietarioBonificacion(PropietarioBonificacion pb) {
        this.propietarioBonificacion.add(pb);
    }

	public Tarifa getTarifaParaCategoria(CategoriaVehiculo categoriaVehiculo) {
        if (this.tarifas == null) {
            return null;
        }

        return this.tarifas.stream()
            .filter(t -> t.getCategoriaVehiculo().getNombre().equals(categoriaVehiculo.getNombre()))
            .findFirst()
            .orElse(null); 
    }
}
