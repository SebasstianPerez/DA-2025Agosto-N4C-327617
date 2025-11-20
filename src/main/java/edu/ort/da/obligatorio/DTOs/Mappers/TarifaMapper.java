package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Transito.TarifaDTO;
import edu.ort.da.obligatorio.Modelo.Tarifa;

public class TarifaMapper {
    private TarifaMapper() {
    }

    public static Collection<TarifaDTO> toDTOCollection(Collection<Tarifa> tarifas) {
        if (tarifas == null) {
            return List.of();
        }

        return tarifas.stream()
                .map(TarifaMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static TarifaDTO toDTO(Tarifa tarifa) {
        if (tarifa == null) {
            return null;
        }
        TarifaDTO dto = new TarifaDTO();
        dto.setMonto(tarifa.getMonto());
        dto.setCategoriaVehiculo(tarifa.getCategoriaVehiculo().getNombre());
        return dto;
    }
}
