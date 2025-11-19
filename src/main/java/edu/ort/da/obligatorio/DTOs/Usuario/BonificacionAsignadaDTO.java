package edu.ort.da.obligatorio.DTOs.Usuario;

import lombok.Data;

@Data
public class BonificacionAsignadaDTO {
    private String nombreBonificacion;
    private String nombrePuesto;
    private String direccionPuesto;
    private String fechaAsignada;
    private String cedula;
}
