package edu.ort.da.obligatorio.Servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Bonificacion;
import edu.ort.da.obligatorio.Modelo.Deshabilitado;
import edu.ort.da.obligatorio.Modelo.EstadoPropietario;
import edu.ort.da.obligatorio.Modelo.Habilitado;
import edu.ort.da.obligatorio.Modelo.Notificacion;
import edu.ort.da.obligatorio.Modelo.Penalizado;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.Propietario.Eventos;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Puesto;
import edu.ort.da.obligatorio.Modelo.Sesion;
import edu.ort.da.obligatorio.Modelo.Suspendido;
import edu.ort.da.obligatorio.Modelo.Transito;
import edu.ort.da.obligatorio.Modelo.Usuario;

@Service
class SistemaUsuarios {

    private static Long nextUserId = 1L;
    private Collection<Propietario> propietarios = new ArrayList<Propietario>();
    private Collection<Administrador> administradores = new ArrayList<Administrador>();
    private Collection<Sesion> sesiones = new ArrayList<Sesion>();
    private Collection<Bonificacion> bonificaciones = new ArrayList<Bonificacion>();

    private static final List<EstadoPropietario> TODOS_LOS_ESTADOS;

    static {
        TODOS_LOS_ESTADOS = Arrays.asList(
                Habilitado.getInstancia(),
                Deshabilitado.getInstancia(),
                Suspendido.getInstancia(),
                Penalizado.getInstancia());
    }

    public static List<EstadoPropietario> getTodosLosEstados() {
        return TODOS_LOS_ESTADOS;
    }

    public static List<String> obtenerNombresDeTodosLosEstados() {
        return TODOS_LOS_ESTADOS.stream()
                .map(EstadoPropietario::getNombreEstado)
                .collect(Collectors.toList());
    }

    public Collection<Propietario> getUsuarios() {
        return (Collection) propietarios;
    }

    public void agregarPropietario(Propietario usuario) throws PeajeException {
        if (usuario == null) {
            throw new PeajeException("No se puede agregar un propietario nulo");
        }

        Long id = getNextId();
        usuario.setId(id);

        EstadoPropietario estado = Habilitado.getInstancia();
        usuario.setEstado(estado);

        propietarios.add(usuario);
    }

    public void agregarAdministrador(Administrador usuario) throws PeajeException {
        if (usuario == null) {
            throw new PeajeException("No se puede agregar un administrador nulo");
        }

        Long id = getNextId();
        usuario.setId(id);
        administradores.add(usuario);
    }

    private Usuario login(String cedula, String password, Collection lista) {
        Usuario usuario;
        for (Object o : lista) {
            usuario = (Usuario) o;

            if (usuario.getCedula().equals(cedula) && usuario.coincideContrasenia(password)) {
                return usuario;
            }
        }

        return null;
    }

    public Propietario loginPropietario(String cedula, String contrasena) throws PeajeException {
        Propietario propietario = (Propietario) login(cedula, contrasena, propietarios);

        if (propietario == null) {
            throw new PeajeException("Acceso denegado (Cédula y/o contraseña incorrectos)");
        }

        if (!propietario.puedeIngresar()) {
            throw new PeajeException("El propietario no puede ingresar. Estado actual: "
                    + propietario.getEstado().getNombreEstado());
        }

        return propietario;
    }

    public Administrador loginAdministrador(String cedula, String contrasena) throws PeajeException {
        if (this.estaAdminLogueado(cedula)) {
            throw new PeajeException("Ud. Ya está logueado");
        }

        Administrador admin = (Administrador) login(cedula, contrasena, administradores);
        if (admin == null) {
            throw new PeajeException("Acceso denegado (Cédula y/o contraseña incorrectos)");
        }

        this.registrarAdminLogueado(admin);

        return admin;
    }

    public boolean estaAdminLogueado(String cedula) {
        return sesiones.stream()
                .anyMatch(a -> a.getAdmin().getCedula().equals(cedula));
    }

    public void registrarAdminLogueado(Administrador admin) {
        if (!estaAdminLogueado(admin.getNombre())) {
            Sesion sesion = new Sesion(admin);
            sesiones.add(sesion);
        }
    }

    public void logoutAdmin(String cedula) throws PeajeException {
        Sesion sesion = sesiones.stream().filter(s -> s.getAdmin().getCedula().equals(cedula)).findFirst().orElse(null);

        if (sesion != null) {
            sesiones.remove(sesion);
        } else {
            throw new PeajeException("No se encontro sesion activa");

        }
    }

    public Collection<Notificacion> getNotifiaciones(String cedula) throws PeajeException {
        return getPropietarioByCedula(cedula).getNotificaciones().stream()
                .sorted(Comparator.comparing(Notificacion::getFecha).reversed())
                .collect(Collectors.toList());
    }

    public Collection<String> getBonificacionesNombre() {
        return this.bonificaciones.stream()
                .map(Bonificacion::getNombre)
                .collect(Collectors.toList());
    }

    public void deleteNotificaciones(String cedula) throws PeajeException {
        Propietario propietario = getPropietarioByCedula(cedula);
        propietario.limpiarNotificaciones();
    }

    Administrador getAdministrador(String cedula) {
        return administradores.stream()
                .filter(a -> a.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
    }

    public static synchronized Long getNextId() {
        return nextUserId++;
    }

    public Propietario getPropietarioById(Long userId) {
        return propietarios.stream()
                .filter(p -> p.getId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    public void addBonificacion(Bonificacion bonificacion) {
        bonificaciones.add(bonificacion);
    }

    public void asignarBonificacion(PropietarioBonificacion pb) throws PeajeException {
        if (!pb.getPropietario().getEstado().puedeRecibirBonificaciones()) {
            throw new PeajeException("El propietario se encuentra en un estado que no permite recibir bonificaciones.");
        }

        Propietario propietario = pb.getPropietario();
        propietario.agregarBonificacion(pb);
    }

    public static EstadoPropietario getInstanciaPorNombre(String nombreEstado) throws PeajeException {
        return TODOS_LOS_ESTADOS.stream()
                .filter(estado -> estado.getNombreEstado().equalsIgnoreCase(nombreEstado))
                .findFirst()
                .orElseThrow(() -> new PeajeException("Estado no encontrado: " + nombreEstado));
    }

    public Propietario getPropietarioByCedula(String cedula) throws PeajeException {
        Propietario propietario = propietarios.stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);

        if(propietario == null){
            throw new PeajeException("no existe el propietario");
        }

        return propietario;
    }

    public void cambiarEstado(String cedula, String nombreEstadoDestino) throws PeajeException {
        Propietario propietario = getPropietarioByCedula(cedula);
        EstadoPropietario estadoDestinoInstancia = getInstanciaPorNombre(nombreEstadoDestino);
        propietario.setEstado(estadoDestinoInstancia);

    }

    public Bonificacion getBonificacionByNombre(String nombreBonificacion) {
        return bonificaciones.stream()
                .filter(p -> p.getNombre().equals(nombreBonificacion))
                .findFirst()
                .orElse(null);
    }

    public void asignarBonificacion(String cedula, Puesto puesto, String bonificacionNombre) throws PeajeException {
        Bonificacion bonificacion = getBonificacionByNombre(bonificacionNombre);
        Propietario propietario = getPropietarioByCedula(cedula);

        if(!propietario.getEstado().puedeRecibirBonificaciones()){
            throw new PeajeException("No puede recibir bonificaciones.");
        }

        if(propietario == null){
            throw new PeajeException("No existe el propietario");
        }

        if (bonificacion == null) {
            throw new PeajeException("Debe especificar una bonificación.");
        }

        if(puesto == null){
            throw new PeajeException("Debe especificar un puesto");
        }

        Collection<PropietarioBonificacion> asignacionesActuales = propietario.getBonificaciones();

        PropietarioBonificacion bonificacionExistente = asignacionesActuales.stream().filter(
                a -> a.getPuesto().getDireccion()
                .equals(puesto.getDireccion()))
                .findFirst()
            .orElse(null);

        if(bonificacionExistente != null){
            throw new PeajeException("Ya tiene una bonificacion asignada para ese puesto");
        }
        
        // asignacionesActuales.removeIf(ba -> ba.getPuesto().getDireccion().equals(puesto.getDireccion()));

        PropietarioBonificacion nuevaAsignacion = new PropietarioBonificacion(propietario, puesto, bonificacion);

        propietario.agregarBonificacion(nuevaAsignacion);
    }

    public Collection<Transito> getTransitosRealizados(Propietario propietario) {
        return propietario.getTransitos().stream()
                .sorted(Comparator.comparing(Transito::getFecha).reversed())
                .collect(Collectors.toList());
    }
}
