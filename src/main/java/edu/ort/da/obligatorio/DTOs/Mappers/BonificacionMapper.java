// Archivo: BonificacionMapper.java (o DashboardMapper.java)
package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Usuario.BonificacionAsignadaDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.BonificacionDTO;
import edu.ort.da.obligatorio.Modelo.Bonificacion;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;


public class BonificacionMapper {

    public static Collection<BonificacionAsignadaDTO> mapListToDTO(Collection<PropietarioBonificacion> dominioList) {
        if (dominioList == null) {
            return Collections.emptyList();
        }
        return dominioList.stream()
            .map(BonificacionMapper::mapToDTO)
            .collect(Collectors.toList());
    }

    public static BonificacionAsignadaDTO mapToDTO(PropietarioBonificacion propBonificacion) {
        if (propBonificacion == null) return null;
        
        BonificacionAsignadaDTO dto = new BonificacionAsignadaDTO();
        
        dto.setNombreBonificacion(propBonificacion.getBonificacion().getNombre()); 

        dto.setCedula(propBonificacion.getPropietario().getCedula());

        dto.setDireccionPuesto(propBonificacion.getPuesto().getDireccion());
        
        dto.setNombrePuesto(propBonificacion.getPuesto().getNombre()); 

        dto.setFechaAsignada(propBonificacion.getFechaAsignada().toString()); 
        
        return dto;
    }

    public static Collection<BonificacionDTO> mapToListDto(Collection<Bonificacion> bonificaciones){
        if(bonificaciones == null){
            return Collections.emptyList();
        }
        return bonificaciones.stream()
                .map(BonificacionMapper::mapToBonificacionDTO)
                .collect(Collectors.toList());
    }

    public static BonificacionDTO mapToBonificacionDTO(Bonificacion bonificacion){
        if(bonificacion == null) return null;

        BonificacionDTO dto = new BonificacionDTO();
        dto.setNombre(bonificacion.getNombre());
        return dto;
    }

}