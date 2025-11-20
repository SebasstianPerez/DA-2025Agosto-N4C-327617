package edu.ort.da.obligatorio.Modelo;

import edu.ort.da.obligatorio.Excepciones.PeajeException;

public interface EstadoPropietario {
    
    void habilitar(Propietario propietario) throws PeajeException;
    void penalizar(Propietario propietario) throws PeajeException;
    void deshabilitar(Propietario propietario) throws PeajeException;
    void suspender(Propietario propietario) throws PeajeException;

    void manejarEntrada(Propietario propietario); 

    boolean puedeIngresarAlSistema();
    boolean puedeRealizarTransitos();
    boolean puedeRecibirBonificaciones();
    boolean aplicanBonificaciones();
    boolean registranNotificaciones();

    String getNombreEstado();

}