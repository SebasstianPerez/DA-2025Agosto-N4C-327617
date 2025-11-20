package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.Utils.Respuesta;
import jakarta.servlet.http.HttpSession;


// Corrección del controlador Logout.java

@RestController
@RequestMapping("/auth")
public class Logout {

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sesionHttp) {
        if (sesionHttp != null) {
            sesionHttp.invalidate(); 
        }

        return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
    }
}
