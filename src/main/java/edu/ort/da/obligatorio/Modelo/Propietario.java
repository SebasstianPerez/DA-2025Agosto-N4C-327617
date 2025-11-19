package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import edu.ort.da.obligatorio.Observador.Observable;
import edu.ort.da.obligatorio.Observador.ObservableAbstracto;
import edu.ort.da.obligatorio.Observador.ObservableConcreto;
import edu.ort.da.obligatorio.Observador.Observador;
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
public class Propietario extends Usuario implements Observable {

    private double saldo;

    private double saldoMinAlerta = 500;

    private EstadoPropietario estado;

    @ToString.Exclude
    private Collection<Notificacion> notificaciones;

    @ToString.Exclude
    private Collection<Vehiculo> vehiculos;

    @ToString.Exclude
    private Collection<PropietarioBonificacion> bonificaciones;

    @ToString.Exclude
    private Collection<Transito> transitos;

    private ObservableConcreto observable;

    public enum Eventos {
        NUEVA_NOTIFICACION,
        CAMBIO_ESTADO,
        NUEVO_TRANSITO,
        BONIFICACION_ASIGNADA,
        NUEVO_VEHICULO
    }



    public Propietario(String cedulaDeIdentidad, String contrasena, String nombre, String apellido, double saldo) {

        super(cedulaDeIdentidad, contrasena, nombre, apellido);
        this.saldo = saldo;

        this.notificaciones = new ArrayList<>();
        this.vehiculos = new ArrayList<>();
        this.bonificaciones = new ArrayList<>();
        this.transitos = new ArrayList<>();
        this.observable = new ObservableConcreto();
    }

    public String getAlerta() {
        return null;
    }

    public ResultadoCalculoTransito calcularMontoNetoAPagar(double montoBase, Puesto puesto, LocalDateTime fechaTransito,
            Long transitosPreviosHoy) {

        PropietarioBonificacion bonificacionActiva = this.bonificaciones.stream()
                .filter(pb -> pb.getPuesto() != null && pb.getPuesto().equals(puesto))
                .findFirst()
                .orElse(null);

        double montoNetoAPagar = montoBase;

        if (bonificacionActiva != null) {
            montoNetoAPagar = bonificacionActiva.getBonificacion().calcularDescuento(
                    fechaTransito,
                    montoBase,
                    transitosPreviosHoy);
        }

        ResultadoCalculoTransito resultado = new ResultadoCalculoTransito(Math.max(0.0, montoNetoAPagar), bonificacionActiva);

        return resultado;
    }

    public boolean cobrarTransito(double monto) {
        if (monto > this.saldo) {
            return false;
        }

        this.setSaldo(saldo - monto);
        return true;
    }

    public void agregarBonificacion(PropietarioBonificacion bonificacion) {
        this.bonificaciones.add(bonificacion);
        observable.avisar(Eventos.BONIFICACION_ASIGNADA);
    }

    public void addVehiculo(Vehiculo vehiculo) {
        this.vehiculos.add(vehiculo);
        observable.avisar(Eventos.NUEVO_VEHICULO);
    }

    public void agregarNotificacion(Notificacion notificacion) {
        this.notificaciones.add(notificacion);
        observable.avisar(Eventos.NUEVA_NOTIFICACION);
    }

    public void agregarTransito(Transito transito) {
        this.transitos.add(transito);
        observable.avisar(Eventos.NUEVO_TRANSITO);
    }

    public boolean puedeIngresar() {
        return estado.puedeIngresarAlSistema();
    }

    public void cambiarEstado(EstadoPropietario estadoDestinoInstancia) {
        String nuevoEstado = estadoDestinoInstancia.getNombreEstado();

        switch (nuevoEstado) {
            case "Habilitado" -> this.estado.habilitar(this);
            case "Penalizado" -> this.estado.penalizar(this);
            case "Suspendido" -> this.estado.suspender(this);
            case "Deshabilitado" -> this.estado.deshabilitar(this);
            default -> throw new IllegalArgumentException("Destino de estado inválido.");
        }

        observable.avisar(Eventos.CAMBIO_ESTADO);
    }

    @Override
    public void agregarObservador(Observador obs) {
        observable.agregarObservador(obs);
        System.out.println("Observador agregado: " + observable.toString());
    }

    @Override
    public void quitarObservador(Observador obs) {
        observable.quitarObservador(obs);
    }
}
