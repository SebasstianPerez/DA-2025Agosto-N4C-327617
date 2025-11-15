package edu.ort.da.obligatorio.DTOs.Mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import edu.ort.da.obligatorio.DTOs.Vehiculo.VehiculoDTO;
import edu.ort.da.obligatorio.Modelo.CategoriaVehiculo;
import edu.ort.da.obligatorio.Modelo.Vehiculo;

public class VehiculoMapper {

    public static Vehiculo ToVehiculo(VehiculoDTO vehiculoDTO, CategoriaVehiculo categoria) {
        if (vehiculoDTO == null) return null;
        
        Vehiculo v = new Vehiculo();
        
        v.setMatricula(vehiculoDTO.getMatricula());
        v.setModelo(vehiculoDTO.getModelo());
        v.setColor(vehiculoDTO.getColor());
        v.setCategoria(categoria); 
        
        return v;
    }

    public static VehiculoDTO ToVehiculoDTO(Vehiculo vehiculo) {
        if (vehiculo == null) return null;
        
        VehiculoDTO dto = new VehiculoDTO();
        
        dto.setMatricula(vehiculo.getMatricula());
        dto.setModelo(vehiculo.getModelo());
        dto.setColor(vehiculo.getColor());
        
        if (vehiculo.getCategoria() != null) {
            dto.setCategoriaVehiculo(vehiculo.getCategoria().getNombre());
        }
        
        return dto;
    }

    public static Collection<VehiculoDTO> mapToVehiculoDTO(Collection<Vehiculo> vehiculos) {
        if (vehiculos == null) {
            return new ArrayList<>(); // Retorna una colección vacía si la entrada es null
        }
        
        // Usamos Streams de Java 8+ para iterar y mapear cada elemento
        return vehiculos.stream()
                // Llama al método de mapeo individual para cada Vehiculo
                .map(VehiculoMapper::mapToVehiculoDTO) 
                .collect(Collectors.toList());
    }

    public static VehiculoDTO mapToVehiculoDTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null; 
        }

        VehiculoDTO dto = new VehiculoDTO();
        
        dto.setMatricula(vehiculo.getMatricula());
        dto.setCantidadTransitos(vehiculo.getCantidadTransitos());
        dto.setColor(vehiculo.getColor());
        dto.setModelo(vehiculo.getModelo());
        dto.setMontoTotal(vehiculo.getMontoTotal());
        
        if (vehiculo.getCategoria() != null) {
             dto.setCategoriaVehiculo(vehiculo.getCategoria().getNombre());
        }
        
        return dto;
    }
}