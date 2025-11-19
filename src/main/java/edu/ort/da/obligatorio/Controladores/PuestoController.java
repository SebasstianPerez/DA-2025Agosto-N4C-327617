package edu.ort.da.obligatorio.Controladores;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Mappers.PuestoMapper;
import edu.ort.da.obligatorio.DTOs.Transito.PuestoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.AsignarBonificacionesDataDTO;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Servicios.Fachada;

@RestController
@RequestMapping("/puesto")
public class PuestoController {

    @Autowired
    private Fachada fachada;

    @GetMapping("/")
    public List<Respuesta> getPuestos() {
        try {
            Collection<PuestoDTO> data = PuestoMapper.toDTOList(fachada.obtenerPuestos());
            return Respuesta.lista(new Respuesta("Datos obtenidos", data));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("Ha ocurrido un error al obtener los datos", e.getMessage()));
        }
    }

    @GetMapping("/asignarBonificacion")
    public List<Respuesta> getData() {
        try {
            AsignarBonificacionesDataDTO dto = fachada.obtenerDataAsignarBonificacion();
            return Respuesta.lista(new Respuesta("Datos obtenidos", dto));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("Ha ocurrido un error al asignar la bonificación", e.getMessage()));
        }
    }
}
