package edu.ort.da.obligatorio.Servicios;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.security.auth.login.LoginException;

import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Bonificacion;
import edu.ort.da.obligatorio.Modelo.Deshabilitado;
import edu.ort.da.obligatorio.Modelo.EstadoPropietario;
import edu.ort.da.obligatorio.Modelo.Habilitado;
import edu.ort.da.obligatorio.Modelo.Penalizado;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Sesion;
import edu.ort.da.obligatorio.Modelo.Suspendido;
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
            Penalizado.getInstancia()
        );
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

    /**
     *
     */
    public void agregarPropietario(Propietario usuario) {
        if (usuario == null) {
            // throw new Exception();
            return;
        }

        Long id = getNextId();
        usuario.setId(id); 

        EstadoPropietario estado = Habilitado.getInstancia();
        usuario.setEstado(estado);

        propietarios.add(usuario);
    }

    public void agregarAdministrador(Administrador usuario){
        if(usuario == null){
            return;
        }

        Long id = getNextId();
        usuario.setId(id); 
        administradores.add(usuario);
    }

    /**
     *
     */
    private Usuario login(String nom, String password, Collection lista) {
        Usuario usuario;
        for (Object o : lista) {
            usuario = (Usuario) o;

            //TODO CAMBIAR LOGIN POR CEDULA Y CONTRASENA
            if (usuario.getNombre().equals(nom) && usuario.coincideContrasenia(password)) {
                return usuario;
            }
        }
        return null;
    }

    public Propietario loginPropietario(LoginDTO datos) {
        Propietario propietario = (Propietario) login(datos.getNombre(), datos.getContrasena(), propietarios);

        if (propietario == null) {
            return null;
        }

        if(!propietario.puedeIngresar()){
            return null;
        }

        //Exceptions
        return propietario;
    }

    public Administrador loginAdministrador(LoginDTO datos) throws LoginException {
        if (this.estaAdminLogueado(datos.getNombre())) {
            throw new LoginException("Ud. Ya está logueado");
        }

        Administrador admin = (Administrador) login(datos.getNombre(), datos.getContrasena(), administradores);
        if (admin == null) {
            throw new LoginException("Acceso denegado (Cédula y/o contraseña incorrectos)");
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

    public Collection<Propietario> getNotifiaciones(String cedula) {
        return null;
    }

    public Collection<PropietarioBonificacion> getBonificaciones(String cedula) {
        return null;
    }

    public void deleteNotificaciones(String cedula) {

    }

    public double cobrarMonto(String cedula, double monto) {
        return 0;
    }

    Administrador getAdministrador(LoginDTO dto) {
        return administradores.stream()
                .filter(a -> a.getNombre().equals(dto.getNombre()))
                .findFirst()
                .orElse(null);
    }

    Propietario getPropietario(String nombreUsuario) {
        return propietarios.stream()
                .filter(p -> p.getNombre().equals(nombreUsuario))
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

    public void asignarBonificacion(PropietarioBonificacion pb) {
        Propietario propietario = pb.getPropietario();
        propietario.agregarBonificacion(pb);
    }

    public static EstadoPropietario getInstanciaPorNombre(String nombreEstado) {
        return TODOS_LOS_ESTADOS.stream()
            .filter(estado -> estado.getNombreEstado().equalsIgnoreCase(nombreEstado))
            .findFirst() 
            .orElseThrow(() -> new NoSuchElementException("Estado no encontrado: " + nombreEstado));
    }

    public Propietario getPropietarioByCedula(String cedula){
        return propietarios.stream()
            .filter(p -> p.getCedula().equals(cedula))
            .findFirst() 
            .orElse(null);
    }

    public void cambiarEstado(String cedula, String nombreEstadoDestino) {
        Propietario propietario = getPropietarioByCedula(cedula);
        EstadoPropietario estadoDestinoInstancia = getInstanciaPorNombre(nombreEstadoDestino);

        propietario.cambiarEstado(estadoDestinoInstancia);
    }
}
