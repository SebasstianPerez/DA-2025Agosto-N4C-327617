package edu.ort.da.obligatorio.Controladores;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Mappers.PuestoMapper;
import edu.ort.da.obligatorio.DTOs.Transito.PuestoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.AsignarBonificacionesDataDTO;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.Respuesta;

@RestController
@RequestMapping("/puesto")
public class PuestoController {

    @Autowired
    private Fachada fachada;

    @GetMapping("/")
    public List<Respuesta> getPuestos() {
        Collection<PuestoDTO> data = PuestoMapper.toDTOList(fachada.obtenerPuestos());
        return Respuesta.lista(new Respuesta("Datos obtenidos", data));
    }

    @GetMapping("/asignarBonificacion")
    public List<Respuesta> getData() {

        AsignarBonificacionesDataDTO dto = new AsignarBonificacionesDataDTO();
        
        Collection<String> nombresBonificaciones = fachada.obtenerBonificaciones();
        Collection<String> direccionesPuestos = fachada.obtenerPuestos().stream()
                .map(p -> p.getDireccion())
                .toList();

        dto.setBonificaciones(nombresBonificaciones);
        dto.setDireccionPuesto(direccionesPuestos);
    

        return Respuesta.lista(new Respuesta("Datos obtenidos", dto));
    }
}
