package edu.ort.da.obligatorio.DTOs.Transito;


import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransitoDTO {
	private String nombrePuesto;

	private String puestoDireccion;

	private String vehiculoMatricula;

	private String categoria;

	private String bonificacion;

	private double montoTarifa;

	private double montoBonificacion;

	private double montoPagado;

	private LocalDateTime fecha;


}
