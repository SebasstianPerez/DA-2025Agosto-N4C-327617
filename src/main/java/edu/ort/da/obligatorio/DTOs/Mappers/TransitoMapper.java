package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.Modelo.Transito;

public class TransitoMapper {

    public static Collection<TransitoDTO> mapToTransitoDTO(Collection<Transito> transitos) {
        if (transitos == null || transitos.isEmpty()) {
            return new ArrayList<>();
        }

        return transitos.stream()
                .map(TransitoMapper::mapToTransitoDTO)
                .collect(Collectors.toList());
    }

    public static TransitoDTO mapToTransitoDTO(Transito transito) {
        if (transito == null) {
            return null;
        }

        TransitoDTO dto = new TransitoDTO();

        dto.setFecha(transito.getFecha());
        dto.setPuestoDireccion(transito.getPuesto().getDireccion());
        dto.setCategoria(transito.getVehiculo().getCategoria().getNombre());
        dto.setBonificacion(transito.getPropietarioBonificacion().getBonificacion().getNombre());
        dto.setMontoTarifa(transito.getMontoTarifaBase());
        dto.setMontoBonificacion(transito.getMontoBonificacion());
        dto.setMontoPagado(transito.getMontoPagado());

        if (transito.getVehiculo() != null) {
            dto.setVehiculoMatricula(transito.getVehiculo().getMatricula());
        }

        if (transito.getPuesto() != null) {
            dto.setNombrePuesto(transito.getPuesto().getNombre());
        }

        return dto;
    }
}
