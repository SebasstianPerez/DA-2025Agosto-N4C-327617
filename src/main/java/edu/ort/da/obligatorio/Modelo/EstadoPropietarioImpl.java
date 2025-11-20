package edu.ort.da.obligatorio.Modelo;

import edu.ort.da.obligatorio.Excepciones.PeajeException;

public abstract class EstadoPropietarioImpl implements EstadoPropietario {

    @Override
    public String getNombreEstado() {
        return this.getClass().getSimpleName();
    }

    @Override
    public boolean puedeIngresarAlSistema() {
        return true;
    }

    @Override
    public boolean puedeRealizarTransitos() {
        return true;
    }

    @Override
    public boolean puedeRecibirBonificaciones() {
        return true;
    }

    @Override
    public boolean aplicanBonificaciones() {
        return true;
    }

    @Override
    public boolean registranNotificaciones() {
        return true;
    }

    @Override
    public void habilitar(Propietario propietario) throws PeajeException {
        ejecutarTransicion(propietario, getDestinoHabilitado());
    }

    @Override
    public void penalizar(Propietario propietario) throws PeajeException {
        ejecutarTransicion(propietario, getDestinoPenalizado());
    }

    @Override
    public void deshabilitar(Propietario propietario) throws PeajeException {
        ejecutarTransicion(propietario, getDestinoDeshabilitado());
    }

    @Override
    public void suspender(Propietario propietario) throws PeajeException {
        ejecutarTransicion(propietario, getDestinoSuspendido());
    }

    private void ejecutarTransicion(Propietario propietario, EstadoPropietario siguienteEstado) throws PeajeException {
        if (siguienteEstado != this) {
            propietario.setEstado(siguienteEstado);
            siguienteEstado.manejarEntrada(propietario);
        } else {
            throw new PeajeException("Transición no permitida desde el estado " + this.getClass().getSimpleName());
        }
    }

    protected EstadoPropietario getDestinoHabilitado() {
        return this;
    }

    protected EstadoPropietario getDestinoPenalizado() {
        return this;
    }

    protected EstadoPropietario getDestinoDeshabilitado() {
        return this;
    }

    protected EstadoPropietario getDestinoSuspendido() {
        return this;
    }
}
