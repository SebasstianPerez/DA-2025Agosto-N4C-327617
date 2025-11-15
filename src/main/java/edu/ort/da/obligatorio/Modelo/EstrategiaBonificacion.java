package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

public interface EstrategiaBonificacion {
    double calcularDescuento(LocalDateTime fechaTransito, double montoBase , Long transitosPreviosHoy);
}
