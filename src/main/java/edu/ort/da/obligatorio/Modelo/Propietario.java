package edu.ort.da.obligatorio.Modelo;

import java.util.ArrayList;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Propietario extends Usuario {

    private double saldo;

    private double saldoMinAlerta = 500;

    private int estado = 1;

    private Collection<Notificacion> notificaciones;

    private Collection<Vehiculo> vehiculos;

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

    public boolean montoEsValido(double montoAPagar) {
        return false;
    }

    public double cobrarTransito(double monto, String puesto, CategoriaVehiculo categoria) {
        return 0;
    }

}
