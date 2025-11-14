package edu.ort.da.obligatorio.Servicios;

import edu.ort.da.obligatorio.Servicios.SistemaUsuarios;
import edu.ort.da.obligatorio.Servicios.SistemaVehiculos;
import edu.ort.da.obligatorio.Servicios.SistemaTransito;
import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;

public class Fachada {

	private Fachada intancia;

	private SistemaUsuarios sistemaUsuarios;

	private SistemaVehiculos sistemaVehiculos;

	private SistemaTransito sistemaTransito;

	public static Fachada getInstancia() {
		return null;
	}

	/**
	 *  
	 */
	private Fachada() {

	}

	public TransitoDTO cobrarTransito(TransitoDTO transito) {
		return null;
	}

}
