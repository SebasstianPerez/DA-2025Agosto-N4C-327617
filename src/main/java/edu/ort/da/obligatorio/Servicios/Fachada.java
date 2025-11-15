package edu.ort.da.obligatorio.Servicios;

import java.util.Collection;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.DTOs.Mappers.BonificacionMapper;
import edu.ort.da.obligatorio.DTOs.Mappers.NotificacionMapper;
import edu.ort.da.obligatorio.DTOs.Mappers.TransitoMapper;
import edu.ort.da.obligatorio.DTOs.Mappers.VehiculoMapper;
import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.BonificacionAsignadaDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.NotificacionDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDashboardDTO;
import edu.ort.da.obligatorio.DTOs.Vehiculo.VehiculoDTO;
import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Bonificacion;
import edu.ort.da.obligatorio.Modelo.CategoriaVehiculo;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Puesto;
import edu.ort.da.obligatorio.Modelo.Vehiculo;

@Service
public class Fachada {
    private SistemaUsuarios sistemaUsuarios;

    private SistemaVehiculos sistemaVehiculos;

    private SistemaTransito sistemaTransito;

    @Autowired
    private Fachada(SistemaUsuarios sistemaUsuarios, SistemaVehiculos sistemaVehiculos, SistemaTransito sistemaTransito ) {
        this.sistemaUsuarios = sistemaUsuarios;
        this.sistemaTransito = sistemaTransito;
        this.sistemaVehiculos = sistemaVehiculos;
    }

    public TransitoDTO cobrarTransito(TransitoDTO transito) {
        return null;
    }

    public void addPropietario(Propietario datos) {
        sistemaUsuarios.agregarPropietario(datos);
    }

    public void addAdministrador(Administrador datos) {
        sistemaUsuarios.agregarAdministrador(datos);
    }

    public void addCategoriaVehiculo(CategoriaVehiculo datos){
        sistemaVehiculos.agregarCategoriaVehiculo(datos);
    }

    public Administrador getAdministrador(LoginDTO dto) {
        return sistemaUsuarios.getAdministrador(dto);
    }

    public Propietario getPropietario(String nombreUsuario) {
        return sistemaUsuarios.getPropietario(nombreUsuario);
    }

    public Propietario loginPropietario(LoginDTO datos) {
        return sistemaUsuarios.loginPropietario(datos);
    }

    public Administrador loginAdministrador(LoginDTO datos) throws LoginException {
        return sistemaUsuarios.loginAdministrador(datos);
    }

    public Propietario getPropietarioById(Long id) {
        return sistemaUsuarios.getPropietarioById(id);
    }

    public PropietarioDashboardDTO getDashboardData(Long userId) {
        Propietario propietario = sistemaUsuarios.getPropietarioById(Long.valueOf(userId));

        PropietarioDashboardDTO ret = new PropietarioDashboardDTO(
                propietario.getNombre(),
                propietario.getEstado(),
                propietario.getSaldo()
        );

        Collection<PropietarioBonificacion> bonificacionesDominio = propietario.getBonificaciones();
        Collection<BonificacionAsignadaDTO> bonificacionesDTO = BonificacionMapper.mapListToDTO(bonificacionesDominio);
        Collection<VehiculoDTO> vehiculosDTO = VehiculoMapper.mapToVehiculoDTO(propietario.getVehiculos());
        Collection<TransitoDTO> transitosDTO = TransitoMapper.mapToTransitoDTO(sistemaTransito.getTransitosRealizados(propietario.getCedula()));
        Collection<NotificacionDTO> notificacionesDTO = NotificacionMapper.mapToDTO(propietario.getNotificaciones());

        ret.setTransitos(transitosDTO);
        ret.setVehiculos(vehiculosDTO);
        ret.setBonificaciones(bonificacionesDTO);
        ret.setNotificaciones(notificacionesDTO);

        return ret;
    }

    public void agregarPuesto(Puesto puesto) {
        sistemaTransito.addPuesto(puesto);
    }

    public void agregarBonificacion(Bonificacion bonificacion) {
        sistemaUsuarios.addBonificacion(bonificacion);
    }

    public void agregarPropietarioBonificacion(PropietarioBonificacion pb) {
        sistemaTransito.asignarBonificacion(pb);
    }

    public void asignarVehiculoAPropietario(Propietario propietario, Vehiculo v1) {
        sistemaVehiculos.asignarVehiculoAPropietario(v1.getMatricula(), propietario);
    }

    public void addVehiculo(Vehiculo v1) {
        sistemaVehiculos.agregarVehiculo(v1);
    }

    public void emularTransito(String puestoDireccion, String matricula){
        Vehiculo vehiculo = sistemaVehiculos.getVehiculoXMatricula(matricula);
        Propietario propietario = vehiculo.getPropietario();

        sistemaTransito.emularTransito(puestoDireccion, vehiculo, propietario);
    }
}
