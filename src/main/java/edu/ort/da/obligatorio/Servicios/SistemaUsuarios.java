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

import edu.ort.da.obligatorio.DTOs.Usuario.BonificacionAsignadaDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
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

    public Propietario getUsuarioXCedula(String cedula) {
        return null;
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

    public Propietario loginPropietario(LoginDTO datos) throws PeajeException {
        Propietario propietario = (Propietario) login(datos.getCedula(), datos.getContrasena(), propietarios);

        if (propietario == null) {
            throw new PeajeException("Acceso denegado (Cédula y/o contraseña incorrectos)");
        }

        if (!propietario.puedeIngresar()) {
            throw new PeajeException("El propietario no puede ingresar. Estado actual: "
                    + propietario.getEstado().getNombreEstado());
        }

        // Exceptions
        return propietario;
    }

    public Administrador loginAdministrador(LoginDTO datos) throws PeajeException {
        if (this.estaAdminLogueado(datos.getCedula())) {
            throw new PeajeException("Ud. Ya está logueado");
        }

        Administrador admin = (Administrador) login(datos.getCedula(), datos.getContrasena(), administradores);
        if (admin == null) {
            throw new PeajeException("Acceso denegado (Cédula y/o contraseña incorrectos)");
        }

        this.registrarAdminLogueado(admin);

        return admin;
    }

    public boolean estaAdminLogueado(String nombre) {
        return sesiones.stream()
                .anyMatch(a -> a.getAdmin().getNombre().equals(nombre));
    }

    public void registrarAdminLogueado(Administrador admin) {
        if (!estaAdminLogueado(admin.getNombre())) {
            Sesion sesion = new Sesion(admin);
            sesiones.add(sesion);
        }
    }

    public Collection<Notificacion> getNotifiaciones(String cedula) {
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

    Administrador getAdministrador(LoginDTO dto) {
        return administradores.stream()
                .filter(a -> a.getCedula().equals(dto.getCedula()))
                .findFirst()
                .orElse(null);
    }

    Propietario getPropietario(String cedula) {
        return propietarios.stream()
                .filter(p -> p.getCedula().equals(cedula))
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
        if(!pb.getPropietario().getEstado().puedeRecibirBonificaciones()){
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

    public Propietario getPropietarioByCedula(String cedula) {
        return propietarios.stream()
                .filter(p -> p.getCedula().equals(cedula))
                .findFirst()
                .orElse(null);
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

    public void asignarBonificacionApi(Propietario propietario, Puesto puesto, String nombreBonificacion) throws PeajeException {
        Bonificacion bonificacion = getBonificacionByNombre(nombreBonificacion);
        PropietarioBonificacion pb = new PropietarioBonificacion(propietario, puesto, bonificacion);

        asignarBonificacion(pb);
    }

    public void asignarBonificacion(String cedula, Puesto puesto, String bonificacionNombre) throws PeajeException {
        Bonificacion bonificacion = getBonificacionByNombre(bonificacionNombre);
        Propietario propietario = getPropietario(cedula);

        if (propietario == null || puesto == null || bonificacion == null) {
            throw new PeajeException("Datos de asignación incompletos o inválidos.");
        }

        Collection<PropietarioBonificacion> asignacionesActuales = propietario.getBonificaciones();

        asignacionesActuales.removeIf(ba -> ba.getPuesto().getDireccion().equals(puesto.getDireccion()));

        PropietarioBonificacion nuevaAsignacion = new PropietarioBonificacion(propietario, puesto, bonificacion);

        propietario.agregarBonificacion(nuevaAsignacion);
    }

    public Collection<Transito> getTransitosRealizados(Propietario propietario) {
        return propietario.getTransitos().stream()
                .sorted(Comparator.comparing(Transito::getFecha).reversed())
                .collect(Collectors.toList());
    }
}
