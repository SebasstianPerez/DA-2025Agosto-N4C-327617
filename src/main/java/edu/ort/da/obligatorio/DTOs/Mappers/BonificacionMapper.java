// Archivo: BonificacionMapper.java (o DashboardMapper.java)
package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
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
        
        // 1. Nombre de la Bonificación (Navegación: PropietarioBonificacion -> Bonificacion)
        dto.setNombreBonificacion(propBonificacion.getBonificacion().getNombre()); 
        
        // 2. Nombre del Puesto (Navegación: PropietarioBonificacion -> Puesto)
        dto.setNombrePuesto(propBonificacion.getPuesto().getNombre()); 
        
        // 3. Fecha de Asignación (Directo de PropietarioBonificacion)
        // Usamos toString() o un SimpleDateFormat si fuera necesario un formato específico.
        dto.setFechaAsignada(propBonificacion.getFechaAsignada().toString()); 
        
        return dto;
    }

}