package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Usuario.NotificacionDTO;
import edu.ort.da.obligatorio.Modelo.Notificacion;

public class NotificacionMapper {
    public static NotificacionDTO mapToDTO(Notificacion notificacion) {
        if (notificacion == null) {
            return null;
        }

        NotificacionDTO dto = new NotificacionDTO();

        dto.setMensaje(notificacion.getMensaje());
        dto.setFecha(notificacion.getFecha());

        return dto;
    }

    public static Collection<NotificacionDTO> mapToDTO(Collection<Notificacion> notificaciones) {
        if (notificaciones == null || notificaciones.isEmpty()) {
            return new ArrayList<>();
        }

        return notificaciones.stream()
                .map(NotificacionMapper::mapToDTO)
                .collect(Collectors.toList());
    }
}
