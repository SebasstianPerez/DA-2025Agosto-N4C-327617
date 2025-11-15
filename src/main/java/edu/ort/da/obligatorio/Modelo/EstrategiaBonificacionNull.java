package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

public class EstrategiaBonificacionNull implements EstrategiaBonificacion {

    @Override
    public double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy) {
        return 0.0;
    }
    
}   
