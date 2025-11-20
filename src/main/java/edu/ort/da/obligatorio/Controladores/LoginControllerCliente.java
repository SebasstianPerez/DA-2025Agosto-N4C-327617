
package edu.ort.da.obligatorio.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Usuario.LoginDTO;
import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.Usuario;
import edu.ort.da.obligatorio.Servicios.Fachada;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth/cliente")
public class LoginControllerCliente extends LoginControllerAbstracto {
    final String PROPIETARIO_STATE_KEY = "TransitoDashboard.html";

    @Autowired
    public LoginControllerCliente(Fachada fachada) {
        super(fachada);
    }

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
    protected Usuario getUsuario(LoginDTO dto) throws PeajeException {
        Propietario propietario = fachada.getPropietario(dto.getCedula());

        if (!propietario.puedeIngresar()) {
            throw new PeajeException("El propietario se encuentra bloqueado y no puede ingresar al sistema.");
        }

        return propietario;
    }
}
