package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

public class EstrategiaBonificacionNull extends EstrategiaBonificacion {

    public EstrategiaBonificacionNull() {
        super("sinBonificación");
    }

    @Override
    public double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy) {
        return montoBase;
    }

    @Override
    public String obtenerBonificacionNombre() {
        throw new UnsupportedOperationException("Unimplemented method 'obtenerBonificacionNombre'");
    }
    
}   
