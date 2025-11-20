package edu.ort.da.obligatorio.Servicios;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Bonificacion;
import edu.ort.da.obligatorio.Modelo.CategoriaVehiculo;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.Puesto;
import edu.ort.da.obligatorio.Modelo.Transito;
import edu.ort.da.obligatorio.Modelo.Vehiculo;

@Service
public class Fachada {
    private SistemaUsuarios sistemaUsuarios;

    private SistemaVehiculos sistemaVehiculos;

    private SistemaTransito sistemaTransito;

    @Autowired
    private Fachada(SistemaUsuarios sistemaUsuarios, SistemaVehiculos sistemaVehiculos,
            SistemaTransito sistemaTransito) {
        this.sistemaUsuarios = sistemaUsuarios;
        this.sistemaTransito = sistemaTransito;
        this.sistemaVehiculos = sistemaVehiculos;
    }

    public void addPropietario(Propietario datos) throws PeajeException {
        sistemaUsuarios.agregarPropietario(datos);
    }

    public void addAdministrador(Administrador datos) throws PeajeException {
        sistemaUsuarios.agregarAdministrador(datos);
    }

    public void addCategoriaVehiculo(CategoriaVehiculo datos) {
        sistemaVehiculos.agregarCategoriaVehiculo(datos);
    }

    public Administrador getAdministrador(String cedula) {
        return sistemaUsuarios.getAdministrador(cedula);
    }

    public Propietario getPropietario(String cedula) throws PeajeException {
        return sistemaUsuarios.getPropietarioByCedula(cedula);
    }

    public Propietario loginPropietario(String cedula, String contrasena) throws PeajeException {
        return sistemaUsuarios.loginPropietario(cedula, contrasena);
    }

    public Administrador loginAdministrador(String cedula, String contrasena) throws PeajeException {
        return sistemaUsuarios.loginAdministrador(cedula, contrasena);
    }

    public void logoutAdmin(String cedula) throws PeajeException{
        sistemaUsuarios.logoutAdmin(cedula);
    }

    public Propietario getPropietarioById(Long id) {
        return sistemaUsuarios.getPropietarioById(id);
    }

    public void agregarPuesto(Puesto puesto) {
        sistemaTransito.addPuesto(puesto);
    }

    public void agregarBonificacion(Bonificacion bonificacion) {
        sistemaUsuarios.addBonificacion(bonificacion);
    }

    public void agregarPropietarioBonificacion(String cedula, String puestoDireccion, String bonificacionNombre)
            throws PeajeException {
        sistemaUsuarios.asignarBonificacion(cedula, getPuestoByDireccion(puestoDireccion), bonificacionNombre);
    }

    public void asignarVehiculoAPropietario(Propietario propietario, Vehiculo v1) throws PeajeException {
        sistemaVehiculos.asignarVehiculoAPropietario(v1.getMatricula(), propietario);
    }

    public void addVehiculo(Vehiculo v1) {
        sistemaVehiculos.agregarVehiculo(v1);
    }

    public Transito emularTransito(String puestoDireccion, String matricula, LocalDateTime fechaHora)
            throws PeajeException {
        Vehiculo vehiculo = sistemaVehiculos.buscarVehiculoPorMatricula(matricula);
        return sistemaTransito.emularTransito(puestoDireccion, vehiculo, fechaHora);
    }

    public List<String> obtenerEstadosPropietario() {
        return sistemaUsuarios.obtenerNombresDeTodosLosEstados();
    }

    public void cambiarEstadoPropietario(String cedula, String nombreEstado) throws PeajeException {
        sistemaUsuarios.cambiarEstado(cedula, nombreEstado);
    }

    public Collection<Puesto> obtenerPuestos() {
        return sistemaTransito.getPuestos();
    }

    public Collection<String> obtenerBonificaciones() {
        return sistemaUsuarios.getBonificacionesNombre();
    }

    public Puesto getPuestoByDireccion(String direccion) {
        return sistemaTransito.getPuestoByDireccion(direccion);
    }

    public void borrarNotificaciones(Long userId) throws PeajeException {
        sistemaUsuarios.deleteNotificaciones(getPropietarioById(userId).getCedula());
    }
}
