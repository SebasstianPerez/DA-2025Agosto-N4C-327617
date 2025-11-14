package edu.ort.da.obligatorio.Servicios;

import java.util.Collection;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Usuario;

class SistemaUsuarios {
	private Usuario usuario;

	private SistemaUsuarios sistemaUsuarios;

	private PropietarioBonificacion propietarioBonificacion;

	public static SistemaUsuarios getInstancia() {
		return null;
	}

	private void SistemaUsuarios() {

	}

	public Propietario getUsuarioXCedula(String cedula) {
		return null;
	}

	public Collection<Propietario> getUsuarios() {
		return null;
	}

	/**
	 *  
	 */
	public void agregar(PropietarioDTO usuario) {

	}

	/**
	 *  
	 */
	public String login(LoginDTO datos) {
		return null;
	}

	public Collection<Propietario> getNotifiaciones(String cedula) {
		return null;
	}

	public Collection<PropietarioBonificacion> getBonificaciones(String cedula) {
		return null;
	}

	public void deleteNotificaciones(String cedula) {

	}

	public double cobrarMonto(String cedula, double monto) {
		return 0;
	}

}
