package edu.ort.da.obligatorio.DTOs.Usuario;

import java.util.Collection;

import lombok.Data;

@Data
public class AsignarBonificacionesDataDTO {
    private Collection<String> bonificaciones;
    private Collection<String> direccionPuesto;
}
