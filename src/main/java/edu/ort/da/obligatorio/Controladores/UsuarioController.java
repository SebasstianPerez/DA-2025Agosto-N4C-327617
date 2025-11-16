package edu.ort.da.obligatorio.Controladores;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.DTOs.Usuario.CambioEstadoDTO;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private Fachada fachada;

    @PostMapping("/changeState")
    public ResponseEntity<?> changeState(
        @RequestBody CambioEstadoDTO data) {
        
        fachada.cambiarEstadoPropietario(data.getCedula(), data.getNombreEstado());
        
        return ResponseEntity.ok("Estado cambiado correctamente a "+data.getNombreEstado());
    }

    @GetMapping("/changeState")
    public List<Respuesta> changeStateGet() {
        try {
            List<String> data = fachada.obtenerEstadosPropietario();
            return Respuesta.lista(new Respuesta("Datos obtenidos", data));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("Ha ocurrido un error al obtener los datos", e.getMessage()));
        }
    }
}
