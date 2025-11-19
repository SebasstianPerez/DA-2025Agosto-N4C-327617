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

import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDashboardDTO;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Observador.ObservableAbstracto;
import edu.ort.da.obligatorio.Observador.Observador;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.ConexionNavegador;

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
    public List<Respuesta> inicializarVista(@SessionAttribute(name = "userId", required = false) Long userId) {
        if (userId == null) {
            return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "login.html"));
        }

        try {
            this.userIdSesion = userId;

            PropietarioDashboardDTO dashboardData = fachada.getDashboardData(userId);
            Propietario propietario = fachada.getPropietarioById(userId);
            propietario.agregarObservador(this);

            return Respuesta.lista(new Respuesta("datos", dashboardData));
        } catch (Exception e) {
            return Respuesta.lista(new Respuesta("error", e.getMessage()));
        }
    }

    @Override
    public void actualizar(Object evento, ObservableAbstracto origen) {
        System.out.println("DashboardPropietarioController - actualizar llamado con evento: " + evento);
        if (evento instanceof Propietario.Eventos) {

            if (this.userIdSesion == null) {
                System.err.println("Error: Evento de actualización recibido, pero userIdSesion es nulo.");
                return;
            }

            try {
                PropietarioDashboardDTO dashboardDataActualizada = fachada.getDashboardData(this.userIdSesion);

                Respuesta respuestaActualizacion = new Respuesta("datos", dashboardDataActualizada);

                System.out.println("Respuesta: " + respuestaActualizacion);
                conexionNavegador.enviarJSON(Respuesta.lista(respuestaActualizacion));

            } catch (Exception e) {
                System.err.println("error: " + e.getMessage());
            }
        }
    }
}
