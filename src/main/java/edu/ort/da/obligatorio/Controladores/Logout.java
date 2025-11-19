package edu.ort.da.obligatorio.Controladores;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/auth")
public class Logout {

    @PostMapping("/logout")
    public List<Respuesta> logout(HttpSession sesionHttp) {
        if (sesionHttp != null) {
            sesionHttp.removeAttribute("userId");
            sesionHttp.removeAttribute("userRole");
        }
        return Respuesta.lista(new Respuesta("usuarioNoAutenticado", "index.html"));
    }
}
