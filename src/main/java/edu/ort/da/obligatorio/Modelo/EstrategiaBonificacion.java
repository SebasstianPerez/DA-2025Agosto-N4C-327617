package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

public abstract class EstrategiaBonificacion {
    private final String nombre; 

    protected EstrategiaBonificacion(String nombre) {
        this.nombre = nombre;
    }

    public String obtenerBonificacionNombre() {
        return this.nombre;
    }

    abstract double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy);
}
