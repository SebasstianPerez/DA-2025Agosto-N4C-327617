package edu.ort.da.obligatorio.Modelo;

public interface EstadoPropietario {

    // transiciones;
    void habilitar(Propietario propietario);

    void penalizar(Propietario propietario);

    void deshabilitar(Propietario propietario);

    void suspender(Propietario propietario);

    // validaciones
    boolean puedeIngresarAlSistema();

    boolean puedeRealizarTransitos();

    boolean puedeRecibirBonificaciones();

    boolean aplicanBonificaciones();

    boolean registranNotificaciones();

    String getNombreEstado();
}
