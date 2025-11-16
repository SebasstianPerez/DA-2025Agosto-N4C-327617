package edu.ort.da.obligatorio.Modelo;

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
    public void habilitar(Propietario propietario) {
        ejecutarTransicion(propietario, getDestinoHabilitado());
    }

    @Override
    public void penalizar(Propietario propietario) {
        ejecutarTransicion(propietario, getDestinoPenalizado());
    }

    @Override
    public void deshabilitar(Propietario propietario) {
        ejecutarTransicion(propietario, getDestinoDeshabilitado());
    }

    @Override
    public void suspender(Propietario propietario) {
        ejecutarTransicion(propietario, getDestinoSuspendido());
    }

    private void ejecutarTransicion(Propietario propietario, EstadoPropietario siguienteEstado) {
        // El hook por defecto devuelve 'this'. Si no es el estado actual, cambiamos.
        if (siguienteEstado != this) {
            System.out.println("Transición: " + this.getClass().getSimpleName() + " -> "
                    + siguienteEstado.getClass().getSimpleName());
            propietario.setEstado(siguienteEstado);
        } else {
            // Este es el comportamiento por defecto si la transición no está sobrescrita
            // por el hijo.
            System.out.println("ADVERTENCIA: Transición no permitida desde " + this.getClass().getSimpleName());
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
