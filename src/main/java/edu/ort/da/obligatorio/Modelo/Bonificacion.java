package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Bonificacion {

	private String nombre;

	private EstrategiaBonificacion estrategiaBonificacion;

	public double calcularDescuento(LocalDateTime fechaTransito, double monto, Long transitosPreviosHoy) {
        return this.estrategiaBonificacion.calcularDescuento(fechaTransito, monto, transitosPreviosHoy);
    }
}
