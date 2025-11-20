package edu.ort.da.obligatorio.DTOs.Transito;


import lombok.Data;

@Data
public class TransitoDTO {
	private String nombrePuesto;

	private String nombrePropietario;

	private String puestoDireccion;

	private String vehiculoMatricula;

	private String categoria;

	private String bonificacion;

	private double montoTarifa;

	private double montoBonificacion;

	private double montoPagado;

	private String fecha;


}
