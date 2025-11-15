package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Sesion {
    
    private LocalDateTime fechaIngreso;
    private Administrador admin;

    public Sesion(Administrador admin) {
        this.admin = admin;
        this.fechaIngreso = LocalDateTime.now();
    }
    
}