package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Usuario;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.Respuesta;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth/admin")
public class LoginControllerAdmin extends LoginControllerAbstracto {

    final String ADMINISTRADOR_STATE_KEY = "EmularTransito.html";

    @Autowired
    public LoginControllerAdmin(Fachada fachada) {
        super(fachada);
    }

    @Override
    protected void guardarEstadoUsuario(HttpSession sesion, Usuario usuario) {
        sesion.setAttribute("userId", usuario.getId());
        sesion.setAttribute("userCedula", usuario.getCedula());
    }

    @Override
    protected String getDestinoLoginExitoso() {
        return ADMINISTRADOR_STATE_KEY;
    }

    @Override
    protected Usuario getUsuario(String cedula, String contrasena) throws PeajeException {
        return fachada.loginAdministrador(cedula, contrasena);
    }

    @Override
    public List<Respuesta> logout(HttpSession sesion) throws PeajeException {
        String cedulaAdmin = (String) sesion.getAttribute("userCedula");

        fachada.logoutAdmin(cedulaAdmin);

        List<Respuesta> respuestasDelPadre = super.logout(sesion);

        return respuestasDelPadre;
    }
}
