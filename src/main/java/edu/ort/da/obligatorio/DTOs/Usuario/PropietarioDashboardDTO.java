package edu.ort.da.obligatorio.DTOs.Usuario;

import java.util.ArrayList;
import java.util.Collection;

import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.DTOs.Vehiculo.VehiculoDTO;
import lombok.Data;

@Data
public class PropietarioDashboardDTO {
    private String nombre;
    private String cedula;
    private String estado;
    private double saldoActual;

    private Collection<BonificacionAsignadaDTO> bonificaciones = new ArrayList<>();
    private Collection<VehiculoDTO> vehiculos = new ArrayList<>();
    private Collection<TransitoDTO> transitos = new ArrayList<>();
    private Collection<NotificacionDTO> notificaciones = new ArrayList<>();

    public PropietarioDashboardDTO(String nombre, String cedula, String estado, double saldoActual) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.estado = estado;
        this.saldoActual = saldoActual;
    }
}
