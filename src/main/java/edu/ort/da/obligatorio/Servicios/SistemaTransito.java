package edu.ort.da.obligatorio.Servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Puesto;
import edu.ort.da.obligatorio.Modelo.ResultadoCalculoTransito;
import edu.ort.da.obligatorio.Modelo.Tarifa;
import edu.ort.da.obligatorio.Modelo.Transito;
import edu.ort.da.obligatorio.Modelo.Vehiculo;

@Service
public class SistemaTransito {

	private Collection<Transito> transitos = new ArrayList<>();
	private static Long nextTransitoId = 1L;

	private static Long nextPuestoId = 1L;

	private Collection<Puesto> puestos = new ArrayList<>();

	public void addTransito(Transito transito) {
		transito.setId(getNextTransitoId());
		transitos.add(transito);
	}

	public static synchronized Long getNextTransitoId() {
		return nextTransitoId++;
	}

	public Collection<Puesto> getPuestos() {
		return puestos;
	}

	public double calcularPrecioTransito(String puesto, String matricula) {
		return 0;
	}

	public static synchronized Long getnextPuestoId() {
		return nextPuestoId++;
	}

	public void addPuesto(Puesto puesto) {
		puesto.setId(getnextPuestoId());
		puestos.add(puesto);
	}

	public Puesto getPuestoById(Long id) {
		return puestos.stream()
				.filter(p -> p.getId().equals(id))
				.findFirst()
				.orElse(null);
	}

	public Puesto getPuestoByDireccion(String direccion) {
		System.out.println("Buscando puesto por dirección: " + direccion);
		return puestos.stream()
				.filter(p -> p.getDireccion().equals(direccion))
				.findFirst()
				.orElse(null);
	}

	public void asignarBonificacion(PropietarioBonificacion pb) {
		Puesto puesto = pb.getPuesto();
		if (puesto == null) {
			throw new RuntimeException("Puesto no puede ser nulo al asignar bonificación");
		}

		pb.getPuesto().addPropietarioBonificacion(pb);
	}

	public Collection<Transito> getTransitosRealizados(String cedula) {
		return transitos.stream()
				.filter(transito -> transito.getCedula().equals(cedula))
				.collect(Collectors.toList());
	}

	public Long getCantidadTransitosHoy(String cedula) {
		Collection<Transito> transitos = getTransitosRealizados(cedula);
		LocalDateTime inicioHoy = LocalDate.now().atStartOfDay();

		int size = transitos.stream()
				.filter(t -> t.getFecha().isAfter(inicioHoy) || t.getFecha().isEqual(inicioHoy))
				.collect(Collectors.toList()).size();

		return Long.valueOf(size);
	}

	public void emularTransito(String puestoDireccion, Vehiculo vehiculo, Propietario propietario) {
		Puesto puesto = getPuestoByDireccion(puestoDireccion);
		Tarifa tarifa = puesto.getTarifaParaCategoria(vehiculo.getCategoria());

		double montoBase = tarifa.getMonto();

		// Que pasa si el vehiculo no es del propietario?

		// Buscar bonificacion
		ResultadoCalculoTransito resultado = propietario.calcularMontoNetoAPagar(
				montoBase,
				puesto,
				LocalDateTime.now(),
				Long.valueOf(getCantidadTransitosHoy(propietario.getCedula())));

		double montoNetoAPagar = resultado.getMontoAPagar();

		double montoDescuento = montoBase - montoNetoAPagar;

		Transito transito = new Transito(vehiculo, puesto, propietario.getCedula(), montoBase, montoNetoAPagar,
				montoDescuento, resultado.getBonificacion());

		if (propietario.getSaldo() < montoNetoAPagar) {
			throw new RuntimeException("Saldo insuficiente para peaje. Monto requerido: " + montoNetoAPagar);
		}

		propietario.cobrarTransito(montoNetoAPagar);
		propietario.agregarTransito(transito);
		vehiculo.agregarUnTransito(montoNetoAPagar);

		transito.setId(getNextTransitoId());
		transitos.add(transito);
	}

	public Collection<String> getDireccionesPuestos() {
		return puestos.stream()
				.map(Puesto::getDireccion)
				.collect(Collectors.toList());
	}
}
