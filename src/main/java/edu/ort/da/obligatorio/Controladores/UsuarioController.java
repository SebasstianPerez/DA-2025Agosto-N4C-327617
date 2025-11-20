package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import edu.ort.da.obligatorio.DTOs.Mappers.PropietarioMapper;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDTO;
import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.Respuesta;

@RestController
@RequestMapping("/usuario")
@Scope("session")
public class UsuarioController {

    @Autowired
    private Fachada fachada;

    @PostMapping("/changeState")
    public List<Respuesta> changeState(
            @RequestParam String cedula,
            @RequestParam String nombreEstado) throws PeajeException {
        fachada.cambiarEstadoPropietario(cedula, nombreEstado);
        return Respuesta.lista(new Respuesta("exito", nombreEstado));

    }

    @GetMapping("/getStates")
    public List<Respuesta> changeStateGet() {
        List<String> data = fachada.obtenerEstadosPropietario();
        return Respuesta.lista(new Respuesta("estados", data));
    }

    @PostMapping("/obtenerUsuario")
    public List<Respuesta> obtenerUsuario(@RequestParam String cedula) {
        PropietarioDTO entity = PropietarioMapper.toDTO(fachada.getPropietario(cedula));
        return Respuesta.lista(new Respuesta("propietario", entity));
    }

    @PostMapping("/asignarBonificacion")
    public List<Respuesta> asignarBonificacion(@RequestParam String puestoDireccion,
            @RequestParam String bonificacionNombre, @RequestParam String cedula) throws PeajeException {
        fachada.agregarPropietarioBonificacion(cedula, puestoDireccion, bonificacionNombre);
        return Respuesta.lista(new Respuesta("exito", null));
    }

    @GetMapping("/borrarNotificaciones")
    public List<Respuesta> deleteNotificaciones(@SessionAttribute(name = "userId", required = false) Long userId)
            throws PeajeException {

        System.out.println("Borrando notificaciones para el usuario con ID: " + userId);

        if (userId == null) {
            throw new PeajeException("Debe iniciar sesión para realizar esta acción.");
        }
        
        fachada.borrarNotificaciones(userId);
        return Respuesta.lista(new Respuesta("exito", null));
    }

}
