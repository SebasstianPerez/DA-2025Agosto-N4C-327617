package edu.ort.da.obligatorio.Servicios;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.security.auth.login.LoginException;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Sesion;
import edu.ort.da.obligatorio.Modelo.Usuario;

class SistemaUsuarios {

    private static Long nextUserId = 1L;
    private Collection<Propietario> propietarios = new ArrayList<Propietario>();
    private Collection<Administrador> administradores = new ArrayList<Administrador>();
    private Collection<Sesion> sesiones = new ArrayList<Sesion>();

    private void SistemaUsuarios() {

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

        if (propietario != null) {
            return propietario;
        }
        return null;
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
        System.out.println(nombreUsuario);
        return propietarios.stream()
                .filter(p -> p.getNombre().equals(nombreUsuario))
                .findFirst()
                .orElse(null);
    }

    public static synchronized Long getNextId() {
        return nextUserId++;
    }
}
