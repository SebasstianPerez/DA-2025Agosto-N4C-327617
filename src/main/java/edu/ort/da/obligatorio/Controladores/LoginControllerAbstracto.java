package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.Modelo.Usuario;
import edu.ort.da.obligatorio.Servicios.Fachada;
import jakarta.servlet.http.HttpSession;

@RestController
public abstract class LoginControllerAbstracto {

    Fachada fachada = Fachada.getInstancia();

    @PostMapping("/login")
    public List<Respuesta> login(HttpSession sesion, @RequestBody LoginDTO dto) {
        try {
            Usuario usuarioLogueado = getUsuario(dto);

            if (usuarioLogueado != null) {
                guardarEstadoUsuario(sesion, usuarioLogueado);

                // 3. Respuesta de éxito
                String destino = "redirect:" + getDestinoLoginExitoso();
                return Respuesta.lista(new Respuesta("Login exitoso", destino));

            } else {
                // Caso 3a: Fallo de credenciales (si getUsuario devuelve null y no lanza excepción)
                return Respuesta.lista(new Respuesta("Error", "Credenciales incorrectas o usuario no encontrado."));
            }

        } catch (LoginException e) {
            // Caso 3b: Fallo por regla de negocio (Ud. ya está logueado o deshabilitado)
            // Usar e.getMessage() para devolver el mensaje de error específico.
            return Respuesta.lista(new Respuesta("Error", e.getMessage()));
        }
    }

    protected abstract void guardarEstadoUsuario(HttpSession sesion, Usuario usuario);

    protected abstract String getDestinoLoginExitoso();

    protected abstract Usuario getUsuario(LoginDTO dto) throws LoginException;

}
