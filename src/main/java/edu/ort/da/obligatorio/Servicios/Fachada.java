package edu.ort.da.obligatorio.Servicios;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ort.da.obligatorio.DTOs.Mappers.BonificacionMapper;
import edu.ort.da.obligatorio.DTOs.Mappers.NotificacionMapper;
import edu.ort.da.obligatorio.DTOs.Mappers.TransitoMapper;
import edu.ort.da.obligatorio.DTOs.Mappers.VehiculoMapper;
import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.AsignarBonificacionesDataDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.BonificacionAsignadaDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.NotificacionDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDashboardDTO;
import edu.ort.da.obligatorio.DTOs.Vehiculo.VehiculoDTO;
import edu.ort.da.obligatorio.Excepciones.PeajeException;
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

    public Administrador getAdministrador(LoginDTO dto) {
        return sistemaUsuarios.getAdministrador(dto);
    }

    public Propietario getPropietario(String cedula) {
        return sistemaUsuarios.getPropietario(cedula);
    }

    public Propietario loginPropietario(LoginDTO datos) throws PeajeException {
        return sistemaUsuarios.loginPropietario(datos);
    }

    public Administrador loginAdministrador(LoginDTO datos) throws PeajeException {
        return sistemaUsuarios.loginAdministrador(datos);
    }

    public Propietario getPropietarioById(Long id) {
        return sistemaUsuarios.getPropietarioById(id);
    }

    public PropietarioDashboardDTO getDashboardData(Long userId) {
        Propietario propietario = sistemaUsuarios.getPropietarioById(Long.valueOf(userId));

        PropietarioDashboardDTO ret = new PropietarioDashboardDTO(
                propietario.getNombre(),
                propietario.getCedula(),
                propietario.getEstado().getNombreEstado(),
                propietario.getSaldo());

        Collection<PropietarioBonificacion> bonificacionesDominio = propietario.getBonificaciones();
        Collection<BonificacionAsignadaDTO> bonificacionesDTO = BonificacionMapper.mapListToDTO(bonificacionesDominio);
        Collection<VehiculoDTO> vehiculosDTO = VehiculoMapper.mapToVehiculoDTO(propietario.getVehiculos());
        Collection<TransitoDTO> transitosDTO = TransitoMapper
                .mapToTransitoDTO(sistemaUsuarios.getTransitosRealizados(propietario));
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

    public void emularTransito(String puestoDireccion, String matricula, LocalDateTime fechaHora)
            throws PeajeException {
        Vehiculo vehiculo = sistemaVehiculos.buscarVehiculoPorMatricula(matricula);
        sistemaTransito.emularTransito(puestoDireccion, vehiculo, vehiculo.getPropietario(), fechaHora);
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

    public AsignarBonificacionesDataDTO obtenerDataAsignarBonificacion() {
        AsignarBonificacionesDataDTO dto = new AsignarBonificacionesDataDTO();
        dto.setBonificaciones(sistemaUsuarios.getBonificacionesNombre());
        dto.setDireccionPuesto(sistemaTransito.getDireccionesPuestos());
        return dto;
    }

    public void asignarBonificacionApi(BonificacionAsignadaDTO dto) throws PeajeException {
        Propietario propietario = getPropietario(dto.getCedula());
        Puesto puesto = sistemaTransito.getPuestoByDireccion(dto.getDireccionPuesto());
        sistemaUsuarios.asignarBonificacionApi(propietario, puesto, dto.getNombreBonificacion());

    }

    public Puesto getPuestoByDireccion(String direccion) {
        return sistemaTransito.getPuestoByDireccion(direccion);
    }

    public void borrarNotificaciones(Long userId) throws PeajeException {
        sistemaUsuarios.deleteNotificaciones(getPropietarioById(userId).getCedula());
    }
}
