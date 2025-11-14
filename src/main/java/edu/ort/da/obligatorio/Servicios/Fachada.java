package ort.da.Obligatorio;

import ort.da.Obligatorio.SubSistemas.SistemaUsuarios;
import ort.da.Obligatorio.SubSistemas.SistemaVehiculos;
import ort.da.Obligatorio.SubSistemas.SistemaTransito;
import ort.da.Obligatorio.DTOs.Transito.TransitoDTO;

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
