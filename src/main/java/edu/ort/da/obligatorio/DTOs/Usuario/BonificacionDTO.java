package edu.ort.da.obligatorio.DTOs.Usuario;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class BonificacionDTO {

	private String nombre;

	private String puesto;

	private LocalDateTime fechaAsignada;

	public BonificacionDTO() {

	}

}
