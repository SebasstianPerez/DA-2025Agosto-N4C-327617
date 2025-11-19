package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

public class EstrategiaFrecuentes extends EstrategiaBonificacion {

    private static final double DESCUENTO_PORCENTAJE = 0.50;

    public EstrategiaFrecuentes() {
        super("Frecuentes");
    }

    @Override
    public double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy) {

        if (transitosPreviosHoy >= 1) {
            return montoBase * DESCUENTO_PORCENTAJE;
        }

        return montoBase;
    }

    @Override
    public String obtenerBonificacionNombre() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerBonificacionNombre'");
    }
}