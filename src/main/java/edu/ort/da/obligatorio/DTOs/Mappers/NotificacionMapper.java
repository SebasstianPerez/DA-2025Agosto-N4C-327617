package edu.ort.da.obligatorio.DTOs.Mappers;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Usuario.NotificacionDTO;
import edu.ort.da.obligatorio.Modelo.Notificacion;

public class NotificacionMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static NotificacionDTO mapToDTO(Notificacion notificacion) {
        if (notificacion == null) {
            return null;
        }

        NotificacionDTO dto = new NotificacionDTO();

        dto.setMensaje(notificacion.getMensaje());
        dto.setFecha(notificacion.getFecha().format(FORMATTER));

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
