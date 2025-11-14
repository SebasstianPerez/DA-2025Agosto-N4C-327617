package edu.ort.da.obligatorio.Servicios;

import java.util.Collection;

import edu.ort.da.obligatorio.Modelo.Puesto;
import edu.ort.da.obligatorio.Modelo.Transito;

public class SistemaTransito {

	private SistemaTransito sistemaTransito;

	private Puesto puesto;

	private Transito transito;

	public static SistemaTransito getInstancia() {
		return null;
	}

	public void SistemaTransito() {

	}

	public Collection<Transito> getTransitosRealizados(String cedula) {
		return null;
	}

	public void addTransito(Transito transito) {

	}

	public Collection<Puesto> getPuestos() {
		return null;
	}

	public Puesto getPuestoXNombre(String nombre) {
		return null;
	}

	public double calcularPrecioTransito(String puesto, String matricula) {
		return 0;
	}

}
