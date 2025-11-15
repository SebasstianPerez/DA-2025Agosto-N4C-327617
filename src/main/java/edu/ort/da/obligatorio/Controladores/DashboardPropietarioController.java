package edu.ort.da.obligatorio.Controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ort.da.obligatorio.DTOs.Usuario.PropietarioDashboardDTO;
import edu.ort.da.obligatorio.Servicios.Fachada;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/dashboard")
public class DashboardPropietarioController {

    @Autowired
    private Fachada fachada;

    @GetMapping
    public ResponseEntity<?> getDashboardData(HttpSession sesion) {
        Long userId = (Long) sesion.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(401).body("Acceso denegado. No hay sesión activa.");
        }

        try {
            // 2. DELEGAR A LA FACHADA
            PropietarioDashboardDTO dashboardData = fachada.getDashboardData(userId);

            // 3. RETORNAR DATOS
            return ResponseEntity.ok(dashboardData);

        } catch (Exception e) {
            // Manejo de errores de negocio, ej. usuario no encontrado
            return ResponseEntity.status(500).body("Error al cargar datos del tablero: " + e.getMessage());
        }
    }
}
