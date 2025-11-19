package edu.ort.da.obligatorio.Servicios;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.Modelo.CategoriaVehiculo;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.Vehiculo;

@Service
public class SistemaVehiculos {
	private Collection<Vehiculo> vehiculos = new ArrayList<>();
	private Collection<CategoriaVehiculo> categorias = new ArrayList<>();
	private static Long nextVehiculoId = 1L;

	public void agregarVehiculo(Vehiculo data) {
		data.setId(getNextVehiculoId());
		vehiculos.add(data);
	}

	public static synchronized Long getNextVehiculoId() {
		return nextVehiculoId++;
	}

	public void agregarCategoriaVehiculo(CategoriaVehiculo data) {
		categorias.add(data);
	}

	public Collection<Vehiculo> getVehiculosXPropietario(String cedula) {
		return null;
	}

	public void asignarVehiculoAPropietario(String matricula, Propietario propietario) {
		Vehiculo vehiculo = buscarVehiculoPorMatricula(matricula);
		if (vehiculo != null) {

			vehiculo.setPropietario(propietario);
			propietario.addVehiculo(vehiculo);

		} else {
			throw new RuntimeException("Vehículo no encontrado: " + matricula);
		}
	}

	public Vehiculo buscarVehiculoPorMatricula(String matricula) {
		return vehiculos.stream()
				.filter(p -> p.getMatricula().equals(matricula))
				.findFirst()
				.orElse(null);
	}

}
