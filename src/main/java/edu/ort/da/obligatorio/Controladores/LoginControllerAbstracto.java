package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Usuario;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.Respuesta;
import jakarta.servlet.http.HttpSession;

@RestController
public abstract class LoginControllerAbstracto {

    protected final Fachada fachada;

    public LoginControllerAbstracto(Fachada fachada) {
        this.fachada = fachada;
    }

    @PostMapping("/login")
    public List<Respuesta> login(
            HttpSession sesion,
            @RequestParam String cedula,
            @RequestParam String contrasena) throws PeajeException {

        LoginDTO dto = new LoginDTO();
        dto.setCedula(cedula);
        dto.setContrasena(contrasena);
        Usuario usuarioLogueado = getUsuario(dto);

        guardarEstadoUsuario(sesion, usuarioLogueado);

        String destino = "redirect:" + getDestinoLoginExitoso();
        return Respuesta.lista(new Respuesta("loginExitoso", destino));

    }

    protected abstract void guardarEstadoUsuario(HttpSession sesion, Usuario usuario);

    protected abstract String getDestinoLoginExitoso();

    protected abstract Usuario getUsuario(LoginDTO dto) throws PeajeException;

}
