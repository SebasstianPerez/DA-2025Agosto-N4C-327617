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
import edu.ort.da.obligatorio.DTOs.Transito.TransitoDTO;
import edu.ort.da.obligatorio.Servicios.Fachada;

@RestController
@RequestMapping("/transito")
public class TransitoController {

    @Autowired
    private Fachada fachada;

    @GetMapping("/inicializarVista")
    public List<Respuesta> iniciarVista() {
        try {
            Collection<PuestoDTO> puestos = PuestoMapper.toDTOList(fachada.obtenerPuestos());
            Collection<String> bonificaciones = fachada.obtenerBonificaciones();
            return Respuesta.lista(new Respuesta("puestos", puestos), new Respuesta("bonificaciones", bonificaciones));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @PostMapping("/emular")
    public List<Respuesta> CrearTransito(@RequestParam String puestoDireccion, @RequestParam String matricula,
            @RequestParam String fechaHora) {
        try {
            TransitoDTO transitoDTO = new TransitoDTO();
            transitoDTO.setVehiculoMatricula(matricula);
            transitoDTO.setPuestoDireccion(puestoDireccion);

            System.out.println("TransitoDTO recibido: " + transitoDTO);
            fachada.emularTransitoApi(transitoDTO);
            return Respuesta.lista(new Respuesta("mensajeExito", "Tránsito creado con éxito"));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("Ha ocurrido un error al crear el tránsito", e.getMessage()));
        }
    }

}
