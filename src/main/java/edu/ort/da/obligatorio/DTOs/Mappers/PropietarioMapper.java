package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.Collection;

import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;

public class PropietarioMapper {
    
    public static PropietarioDTO toDTO(Propietario propietario) {
        PropietarioDTO dto = new PropietarioDTO();
        dto.setNombre(propietario.getNombre());
        dto.setCedulaDeIdentidad(propietario.getCedula());
        dto.setEstado(propietario.getEstado().getNombreEstado());

        Collection<PropietarioBonificacion> bonificacionesDominio = propietario.getBonificaciones();

        dto.setBonificaciones(BonificacionMapper.mapListToDTO(bonificacionesDominio));
        return dto;
    }
}
