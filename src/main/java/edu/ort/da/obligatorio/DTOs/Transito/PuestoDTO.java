package edu.ort.da.obligatorio.DTOs.Transito;

import java.util.Collection;

import lombok.Data;

@Data
public class PuestoDTO {

	private Long id;

	private String nombre;

	private String direccion;

	private Collection<TarifaDTO> tarifas;

}
