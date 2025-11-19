package edu.ort.da.obligatorio.Modelo;

import lombok.Data;

@Data
public class ResultadoCalculoTransito {
    private final double montoAPagar;
    private final PropietarioBonificacion bonificacion;

}