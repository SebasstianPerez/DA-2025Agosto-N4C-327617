package edu.ort.da.obligatorio.DTOs.Usuario;

import java.time.LocalDateTime;

public class SesionDTO {

    private String nombre;
    private LocalDateTime fecha;

    public SesionDTO(){

    }

    public SesionDTO(String nombre, LocalDateTime fecha){
        this.nombre = nombre;
        this.fecha = fecha;
    }
}