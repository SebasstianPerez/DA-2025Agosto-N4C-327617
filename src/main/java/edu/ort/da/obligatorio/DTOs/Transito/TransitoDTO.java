package edu.ort.da.obligatorio.DTOs.Transito;


import java.time.LocalDateTime;

import edu.ort.da.obligatorio.DTOs.Usuario.BonificacionDTO;
import edu.ort.da.obligatorio.DTOs.Vehiculo.VehiculoDTO;

public class TransitoDTO {

	private VehiculoDTO vehiculo;

	private BonificacionDTO bonificacion;

	private double montoTarifa;

	private double montoPagado;

	private double montoBonificacion;

	private LocalDateTime fecha;

	public TransitoDTO() {

	}

	/**
	 *  
	 */
	public void setVehiculo(VehiculoDTO vehiculo) {

	}

	/**
	 *  
	 */
	public void setBonificacion(BonificacionDTO bonificacion) {

	}

	public void setMontoTarifa(double monto) {

	}

	/**
	 *  
	 */
	public void setMontoPagado(double monto) {

	}

	/**
	 *  
	 */
	public void setMontoBonificacion(double monto) {

	}

	/**
	 *  
	 */
	public void setFecha(LocalDateTime fecha) {

	}

	/**
	 *  
	 */
	public VehiculoDTO getVehiculo() {
		return null;
	}

	public BonificacionDTO getBonificacion() {
		return null;
	}

	public double getMontoTarifa() {
		return 0;
	}

	public double getMontoPagado() {
		return 0;
	}

	public double getMontoBonificacion() {
		return 0;
	}

	public LocalDateTime getFecha() {
		return null;
	}

}
