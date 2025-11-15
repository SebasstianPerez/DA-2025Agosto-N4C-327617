package edu.ort.da.obligatorio.Servicios;

import javax.security.auth.login.LoginException;

import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.Usuario;

public class Fachada {

    private static Fachada instancia;

    private SistemaUsuarios sistemaUsuarios;

    private SistemaVehiculos sistemaVehiculos;

    private SistemaTransito sistemaTransito;

    public static Fachada getInstancia() {
        if (instancia == null) {
            instancia = new Fachada();
        }
        return instancia;
    }

    private Fachada() {
        sistemaUsuarios = new SistemaUsuarios();
        sistemaTransito = new SistemaTransito();
        sistemaVehiculos = new SistemaVehiculos();
    }

    public TransitoDTO cobrarTransito(TransitoDTO transito) {
        return null;
    }
    
    public void addPropietario(Propietario datos){
        sistemaUsuarios.agregarPropietario(datos);
    }

    public void addAdministrador(Administrador datos){
        sistemaUsuarios.agregarAdministrador(datos);
    }

    public Administrador getAdministrador(LoginDTO dto) {
        return sistemaUsuarios.getAdministrador(dto);
    }

    public Propietario getPropietario(String nombreUsuario) {
        return sistemaUsuarios.getPropietario(nombreUsuario);
    }

    public Propietario loginPropietario(LoginDTO datos){
        return sistemaUsuarios.loginPropietario(datos);
    }

    public Administrador loginAdministrador(LoginDTO datos) throws LoginException{
        return sistemaUsuarios.loginAdministrador(datos);
    }
}
