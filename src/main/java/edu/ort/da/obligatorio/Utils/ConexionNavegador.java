
package edu.ort.da.obligatorio.Utils;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Component
@Scope("session")
public class ConexionNavegador {

    private SseEmitter conexionSSE;

    private final ObjectMapper mapper;

    public ConexionNavegador() {
        this.mapper = new ObjectMapper();

        // *************************************************************
        // 3. REGISTRO CLAVE: Agrega el módulo para Java 8 Time
        this.mapper.registerModule(new JavaTimeModule());
        // *************************************************************

        // (Opcional: Si quieres que las fechas no se serialicen como milisegundos)
        // mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    public void conectarSSE() {
        if (conexionSSE != null) {
            cerrarConexion();
        }
        long timeOut = 30 * 60 * 1000;
        conexionSSE = new SseEmitter(timeOut);

    }

    public void cerrarConexion() {
        try {
            if (conexionSSE != null) {
                conexionSSE.complete();
                conexionSSE = null;
            }
        } catch (Exception e) {
        }
    }

    public SseEmitter getConexionSSE() {
        return conexionSSE;
    }

    public void enviarJSON(Object informacion) {
        try {
            String json = this.mapper.writeValueAsString(informacion);
            enviarMensaje(json);

        } catch (JsonProcessingException e) {
            System.out.println("Error al convertir a JSON:" + e.getMessage());

        }
    }

    public void enviarMensaje(String mensaje) {

        if (conexionSSE == null)
            return;
        try {
            conexionSSE.send(mensaje);

        } catch (Throwable e) {
            System.out.println("Error al enviar mensaje:" + e.getMessage());
            cerrarConexion();
        }
    }

}
