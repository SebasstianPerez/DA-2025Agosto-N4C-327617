package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Transito.PuestoDTO;
import edu.ort.da.obligatorio.Modelo.Puesto;

public class PuestoMapper {
    
    public static Collection<PuestoDTO> toDTOList(Collection<Puesto> puestos) {
        if (puestos == null) {
            return List.of();
        }
        
        return puestos.stream()
            .map(PuestoMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    public static PuestoDTO toDTO(Puesto puesto) {
        if (puesto == null) {
            return null;
        }

        PuestoDTO dto = new PuestoDTO();
        dto.setId(puesto.getId());
        dto.setNombre(puesto.getNombre());
        dto.setDireccion(puesto.getDireccion());
        
        dto.setTarifas(TarifaMapper.toDTOCollection(puesto.getTarifas()));

        return dto;
    }
}
