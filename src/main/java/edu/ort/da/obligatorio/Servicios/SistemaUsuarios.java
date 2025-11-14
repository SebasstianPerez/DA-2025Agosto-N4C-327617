package ort.da.Obligatorio.SubSistemas;

import ort.da.Obligatorio.Modelo.Usuario;
import ort.da.Obligatorio.Modelo.PropietarioBonificacion;
import ort.da.Obligatorio.Modelo.String;
import ort.da.Obligatorio.Modelo.Propietario;
import ort.da.Obligatorio.DTOs.Usuario.PropietarioDTO;
import ort.da.Obligatorio.DTOs.Usuario.LoginDTO;

class SistemaUsuarios {

	private Usuario usuario;

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

	public ArrayList<Propietario> getUsuarios() {
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

	public ArrayList<Propietario> getNotifiaciones(String cedula) {
		return null;
	}

	public ArrayList<PropietarioBonificacion> getBonificaciones(String cedula) {
		return null;
	}

	public void deleteNotificaciones(String cedula) {

	}

	public double cobrarMonto(String cedula, double monto) {
		return 0;
	}

}
