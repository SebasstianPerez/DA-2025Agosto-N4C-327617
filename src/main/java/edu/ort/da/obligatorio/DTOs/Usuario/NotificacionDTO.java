package edu.ort.da.obligatorio.DTOs.Usuario;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class NotificacionDTO {
	private String mensaje;

	private LocalDateTime fecha;

}
