package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import edu.ort.da.obligatorio.DTOs.Mappers.PropietarioMapper;
import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDashboardDTO;
import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Observador.ObservableAbstracto;
import edu.ort.da.obligatorio.Observador.Observador;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.ConexionNavegador;
import edu.ort.da.obligatorio.Utils.Respuesta;

@RestController
@RequestMapping("/dashboard")
@Scope("session")
public class DashboardPropietarioController implements Observador {

    private Long userIdSesion;

    @Autowired
    private Fachada fachada;

    private final ConexionNavegador conexionNavegador;

    public DashboardPropietarioController(@Autowired ConexionNavegador conexionNavegador) {
        this.conexionNavegador = conexionNavegador;
    }

    @GetMapping(value = "/registrarSSE", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter registrarSSE() {
        conexionNavegador.conectarSSE();
        System.out.println("SSE registrado para el dashboard del propietario.");

        return conexionNavegador.getConexionSSE();
    }

    @GetMapping("/vistaConectada")
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "userId", required = false) Long userId)
            throws PeajeException {
        if (userId == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        this.userIdSesion = userId;

        Propietario propietario = fachada.getPropietarioById(userId);
        PropietarioDashboardDTO dashboardData = PropietarioMapper.toDashboardDTO(propietario);
        propietario.agregarObservador(this);

        return Respuesta.lista(new Respuesta("datos", dashboardData));
    }

    @Override
    public void actualizar(Object evento, ObservableAbstracto origen) throws PeajeException {
        if (evento instanceof Propietario.Eventos) {

            if (this.userIdSesion == null) {
                System.err.println("Error: Evento de actualización recibido, pero userIdSesion es nulo.");
                return;
            }

            Propietario propietario = fachada.getPropietarioById(this.userIdSesion);
            PropietarioDashboardDTO dashboardData = PropietarioMapper.toDashboardDTO(propietario);

            Respuesta respuestaActualizacion = new Respuesta("datos", dashboardData);

            System.out.println("Respuesta: " + respuestaActualizacion);
            conexionNavegador.enviarJSON(Respuesta.lista(respuestaActualizacion));

        }
    }
}
