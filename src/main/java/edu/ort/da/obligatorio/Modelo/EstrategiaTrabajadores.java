package edu.ort.da.obligatorio.Modelo;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class EstrategiaTrabajadores extends EstrategiaBonificacion {

    private static final double DESCUENTO_PORCENTAJE = 0.20;

    public EstrategiaTrabajadores() {
        super("Trabajadores");
    }

    @Override
    public double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy) {
        DayOfWeek dia = fechaTransito.getDayOfWeek();

        if (dia != DayOfWeek.SATURDAY && dia != DayOfWeek.SUNDAY) {
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