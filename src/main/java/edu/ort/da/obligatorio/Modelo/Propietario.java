package edu.ort.da.obligatorio.Modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

import edu.ort.da.obligatorio.Excepciones.PeajeException;
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
        NUEVO_VEHICULO,
        NOTIFICACION_ELIMINADA
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

    public ResultadoCalculoTransito calcularMontoNetoAPagar(double montoBase, Puesto puesto,
            LocalDateTime fechaTransito,
            Long transitosPreviosHoy) {

        PropietarioBonificacion bonificacionActiva = this.bonificaciones.stream()
                .filter(pb -> pb.getPuesto() != null && pb.getPuesto().equals(puesto))
                .findFirst()
                .orElse(null);

        double montoNetoAPagar = montoBase;

        if (bonificacionActiva != null && estado.aplicanBonificaciones()) {
            montoNetoAPagar = bonificacionActiva.getBonificacion().calcularDescuento(
                    fechaTransito,
                    montoBase,
                    transitosPreviosHoy);
        }

        ResultadoCalculoTransito resultado = new ResultadoCalculoTransito(Math.max(0.0, montoNetoAPagar),
                bonificacionActiva);

        return resultado;
    }

    public boolean cobrarTransito(double monto) throws PeajeException {
        if (monto > this.saldo) {
            throw new PeajeException("Saldo insuficiente para peaje. Monto requerido: " + monto);
        }

        this.setSaldo(saldo - monto);
        return true;
    }

    public void agregarBonificacion(PropietarioBonificacion bonificacion) throws PeajeException {
        this.bonificaciones.add(bonificacion);
        observable.avisar(Eventos.BONIFICACION_ASIGNADA);
    }

    public void addVehiculo(Vehiculo vehiculo) throws PeajeException {
        this.vehiculos.add(vehiculo);
        observable.avisar(Eventos.NUEVO_VEHICULO);
    }

    public void agregarNotificacion(Notificacion notificacion) throws PeajeException {
        this.notificaciones.add(notificacion);
        observable.avisar(Eventos.NUEVA_NOTIFICACION);
    }

    public void agregarTransito(Transito transito) throws PeajeException {
        this.transitos.add(transito);
        this.agregarNotificacion(new Notificacion(
                "Transito realizado",
                "Pasaste por el puesto " + 
                transito.getPuesto().getNombre() + 
                " con el vehículo " + 
                transito.getVehiculo().getMatricula()
            ));

        observable.avisar(Eventos.NUEVO_TRANSITO);
    }

    public boolean puedeIngresar() {
        return estado.puedeIngresarAlSistema();
    }

    public void setEstado(EstadoPropietario nuevoEstado) throws PeajeException {

        if (this.estado != null) {
            if (estado.getNombreEstado().equals(nuevoEstado.getNombreEstado())) {
                throw new PeajeException(
                        "El propietario ya se encuentra en el estado: " + nuevoEstado.getNombreEstado());
            }
        }

        this.estado = nuevoEstado;

        this.agregarNotificacion(new Notificacion(
                "Cambio de estado",
                "Se ha cambiado tu estado en el sistema. Tu estado actual es " + nuevoEstado.getNombreEstado()));

        observable.avisar(Eventos.CAMBIO_ESTADO);
    }

    public void habilitar() throws PeajeException {
        this.estado.habilitar(this);
    }

    public void penalizar() throws PeajeException {
        this.estado.penalizar(this);
    }

    public void deshabilitar() throws PeajeException {
        this.estado.deshabilitar(this);
    }

    public void suspender() throws PeajeException {
        this.estado.suspender(this);
    }

    @Override
    public void agregarObservador(Observador obs) {
        observable.agregarObservador(obs);
    }

    @Override
    public void quitarObservador(Observador obs) {
        observable.quitarObservador(obs);
    }

    public void limpiarNotificaciones() throws PeajeException {
        this.notificaciones.clear();
        observable.avisar(Eventos.NOTIFICACION_ELIMINADA);
    }
}
