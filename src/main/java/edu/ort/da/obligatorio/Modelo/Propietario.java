package ort.da.Obligatorio.Modelo;

import java.util.Collection;

public class Propietario extends Usuario {

	private double saldo;

	private double saldoMinAlerta;

	private int estado;

	private ArrayList<Notificacion> notificaciones;

	private ArrayList<Vehiculo> vehiculos;

	private ArrayList<PropietarioBonificacion> bonificaciones;

	private Bonificacion bonificacion;

	private Collection<PropietarioBonificacion> propietarioBonificacion;

	private Collection<Vehiculo> vehiculo;

	private Vehiculo vehiculo;

	public Propietario() {

	}

	public Propietario(String cedulaDeIdentidad, int contrasena, int String, int nombreCompleto, int estado) {

	}

	public double getSaldo() {
		return 0;
	}

	public void agregarSaldo(int double) {

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
