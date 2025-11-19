package edu.ort.da.obligatorio.Controladores;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.Modelo.Usuario;
import edu.ort.da.obligatorio.Servicios.Fachada;
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
        //guardar sesion?
        return;
    }

    @Override
    protected String getDestinoLoginExitoso() {
        return ADMINISTRADOR_STATE_KEY;
    }

    @Override
    protected Usuario getUsuario(LoginDTO dto) throws LoginException {
        return fachada.loginAdministrador(dto);
    }
}
