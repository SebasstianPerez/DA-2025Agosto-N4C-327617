package edu.ort.da.obligatorio.Controladores;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Mappers.PuestoMapper;
import edu.ort.da.obligatorio.DTOs.Transito.PuestoDTO;
import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.Respuesta;

@RestController
@RequestMapping("/transito")
@Scope("session")
public class TransitoController {

    @Autowired
    private Fachada fachada;

    @GetMapping("/inicializarVista")
    public List<Respuesta> iniciarVista() {
        Collection<PuestoDTO> puestos = PuestoMapper.toDTOList(fachada.obtenerPuestos());
        Collection<String> bonificaciones = fachada.obtenerBonificaciones();
        return Respuesta.lista(new Respuesta("puestos", puestos), new Respuesta("bonificaciones", bonificaciones));
    }

    @PostMapping("/emular")
    public List<Respuesta> CrearTransito(@RequestParam String puestoDireccion, @RequestParam String matricula,
            @RequestParam LocalDateTime fechaHora) throws PeajeException {
                
        fachada.emularTransito(puestoDireccion, matricula, fechaHora);
        return Respuesta.lista(new Respuesta("mensajeExito", "Tránsito creado con éxito"));
    }

}
