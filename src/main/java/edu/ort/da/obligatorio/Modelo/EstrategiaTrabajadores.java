package edu.ort.da.obligatorio.Modelo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class EstrategiaTrabajadores implements EstrategiaBonificacion {

    private static final double DESCUENTO_PORCENTAJE = 0.80;

    @Override
    public double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy) {
        DayOfWeek dia = fechaTransito.getDayOfWeek();

        if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY) {
            return montoBase * DESCUENTO_PORCENTAJE;
        }

        return 0.0;
    }

}