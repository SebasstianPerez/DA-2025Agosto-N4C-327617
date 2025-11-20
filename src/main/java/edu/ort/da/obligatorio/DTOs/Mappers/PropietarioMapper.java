package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.NotificacionDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDashboardDTO;
import edu.ort.da.obligatorio.Modelo.Notificacion;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Transito;

public class PropietarioMapper {

    public static PropietarioDTO toDTO(Propietario propietario) {
        PropietarioDTO dto = new PropietarioDTO();
        dto.setNombreCompleto(propietario.getNombreCompleto());
        dto.setCedulaDeIdentidad(propietario.getCedula());
        dto.setEstado(propietario.getEstado().getNombreEstado());

        Collection<PropietarioBonificacion> bonificacionesDominio = propietario.getBonificaciones();

        dto.setBonificaciones(BonificacionMapper.mapListToDTO(bonificacionesDominio));
        return dto;
    }

    public static PropietarioDashboardDTO toDashboardDTO(Propietario propietario) {
        PropietarioDashboardDTO dto = new PropietarioDashboardDTO(
                propietario.getNombreCompleto(),
                propietario.getCedula(),
                propietario.getEstado().getNombreEstado(),
                propietario.getSaldo());

        dto.setBonificaciones(BonificacionMapper.mapListToDTO(propietario.getBonificaciones()));
        dto.setVehiculos(VehiculoMapper.mapToVehiculoDTO(propietario.getVehiculos()));

        Collection<TransitoDTO> transitosOrdenadosDTO = propietario.getTransitos().stream()
                .sorted(Comparator.comparing(Transito::getFecha).reversed())
                .map(TransitoMapper::mapToTransitoDTO)
                .collect(Collectors.toList());

        dto.setTransitos(transitosOrdenadosDTO);


        Collection<NotificacionDTO> notificacionesOrdenadasDTO = propietario.getNotificaciones().stream()
                .sorted(Comparator.comparing(Notificacion::getFecha).reversed())
                .map(NotificacionMapper::mapToDTO)
                .collect(Collectors.toList());

        dto.setNotificaciones(notificacionesOrdenadasDTO);
        return dto;
    }
}
