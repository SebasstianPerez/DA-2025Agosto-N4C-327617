package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Mappers.PropietarioMapper;
import edu.ort.da.obligatorio.DTOs.Usuario.CambioEstadoDTO;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.Servicios.Fachada;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private Fachada fachada;

    @PostMapping("/changeState")
    public ResponseEntity<?> changeState(
            @RequestBody CambioEstadoDTO data) {

        fachada.cambiarEstadoPropietario(data.getCedula(), data.getNombreEstado());

        return ResponseEntity.ok("Estado cambiado correctamente a " + data.getNombreEstado());
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

    @PostMapping("/obtenerUsuario")
    public List<Respuesta> obtenerUsuario(@RequestParam String cedula) {
        try {
            PropietarioDTO entity = PropietarioMapper.toDTO(fachada.getPropietario(cedula));
            System.out.println(entity);
            return Respuesta.lista(new Respuesta("propietario", entity));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam String puestoDireccion,
            @RequestParam String bonificacionNombre, @RequestParam String cedula) {
        try {
            System.out.println("Asignando bonificación desde el controlador: " + bonificacionNombre + " al propietario: "
                    + cedula + " en el puesto: " + puestoDireccion);
            fachada.agregarPropietarioBonificacion(cedula, puestoDireccion, bonificacionNombre);
            return Respuesta.lista(new Respuesta("exito", null));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

}
