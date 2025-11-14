package edu.ort.da.obligatorio.Modelo;

import java.util.ArrayList;
import java.util.Collection;

public class Propietario extends Usuario {

	private double saldo;

	private double saldoMinAlerta;

	private int estado;

	private Collection<Notificacion> notificaciones;

	private Collection<Vehiculo> vehiculos;

	private Collection<PropietarioBonificacion> bonificaciones;

	public Propietario() {

	}

	public Propietario(String cedulaDeIdentidad, int contrasena, int String, int nombreCompleto, int estado) {

	}

	public double getSaldo() {
		return 0;
	}

	public void agregarSaldo(double saldo) {

	}

	public void setSaldoMinAlerta(double saldo) {

	}

	public double getSaldoMinAlerta() {
		return 0;
	}

	public void setEstado(int estado) {

	}

	public int getEstado() {
		return 0;
	}

	public String getAlerta() {
		return null;
	}

	public ArrayList<Vehiculo> getVehiculos() {
		return null;
	}

	public ArrayList<PropietarioBonificacion> getBonifiaciones() {
		return null;
	}

	public boolean montoEsValido(double montoAPagar) {
		return false;
	}

	public double cobrarTransito(double monto, String puesto, CategoriaVehiculo categoria) {
		return 0;
	}

}
