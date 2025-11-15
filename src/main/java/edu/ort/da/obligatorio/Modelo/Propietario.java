package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Propietario extends Usuario {

    private double saldo;

    private double saldoMinAlerta = 500;

    private String estado = "";

    @ToString.Exclude
    private Collection<Notificacion> notificaciones;

    @ToString.Exclude
    private Collection<Vehiculo> vehiculos;

    @ToString.Exclude
    private Collection<PropietarioBonificacion> bonificaciones;

    public Propietario(String cedulaDeIdentidad, String contrasena, String nombre, String apellido, double saldo) {

        super(cedulaDeIdentidad, contrasena, nombre, apellido);
        this.saldo = saldo;

        this.notificaciones = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
    }

    public String getAlerta() {
        return null;
    }

    public double calcularMontoNetoAPagar(double montoBase, Puesto puesto, LocalDateTime fechaTransito,
            Long transitosPreviosHoy) {

        PropietarioBonificacion bonificacionActiva = this.bonificaciones.stream()
                .filter(pb -> pb.getPuesto() != null && pb.getPuesto().equals(puesto))
                .findFirst()
                .orElse(null);

        double descuento = 0.0;

        if (bonificacionActiva != null) {
            descuento = bonificacionActiva.getBonificacion().calcularDescuento(
                    fechaTransito,
                    montoBase,
                    transitosPreviosHoy);
        }

        double montoNetoAPagar = montoBase - descuento;

        return Math.max(0.0, montoNetoAPagar);
    }

    public boolean cobrarTransito(double monto) {
        if(monto > this.saldo){
            return false;
        }
        
        this.setSaldo(saldo - monto);
        return true;
    }

    public void agregarBonificacion(PropietarioBonificacion bonificacion) {
        this.bonificaciones.add(bonificacion);
    }

    public void addVehiculo(Vehiculo vehiculo) {
        vehiculos.add(vehiculo);
    }

}
