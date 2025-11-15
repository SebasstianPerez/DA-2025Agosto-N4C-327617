
package edu.ort.da.obligatorio.Controladores;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.Modelo.Usuario;
import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/auth/cliente")
public class LoginControllerCliente extends LoginControllerAbstracto {
    final String PROPIETARIO_STATE_KEY = "TransitoDashboard.html";

    @Override
    protected void guardarEstadoUsuario(HttpSession sesion, Usuario usuario) {
        sesion.setAttribute("userId", usuario.getId());
        sesion.setAttribute("userRole", "Propietario");
    }

    @Override
    protected String getDestinoLoginExitoso() {
        return PROPIETARIO_STATE_KEY;
    }

    @Override
    protected Usuario getUsuario(LoginDTO dto) {
        return fachada.loginPropietario(dto);
    }
}
