
package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.Excepciones.PeajeException;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.Usuario;
import edu.ort.da.obligatorio.Servicios.Fachada;
import edu.ort.da.obligatorio.Utils.Respuesta;
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
    protected Usuario getUsuario(String cedula, String contrasena) throws PeajeException {
        Propietario propietario = fachada.loginPropietario(cedula, contrasena);

        if (!propietario.puedeIngresar()) {
            throw new PeajeException("El propietario se encuentra bloqueado y no puede ingresar al sistema.");
        }

        return propietario;
    }

    @Override
    public List<Respuesta> logout(HttpSession sesion) throws PeajeException {
        return super.logout(sesion);
    }
}
