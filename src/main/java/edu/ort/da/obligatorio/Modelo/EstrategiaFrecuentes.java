package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

public class EstrategiaFrecuentes implements EstrategiaBonificacion {

    private static final double DESCUENTO_PORCENTAJE = 0.50;

    @Override
    public double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy) {

        if (transitosPreviosHoy >= 1) {
            return montoBase * DESCUENTO_PORCENTAJE;
        }

        return montoBase;
    }
}