package edu.ort.da.obligatorio;

import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Servicios.Fachada;

public class DatosDePrueba {

    static {
        Fachada fachada = Fachada.getInstancia();
        Propietario propietario = new Propietario("1234123", "juan1234", "JuanPerez1234", "Perez", 1000);
        Administrador admin = new Administrador("1234123", "admin", "admin", "");

        fachada.addPropietario(propietario);
        fachada.addAdministrador(admin);

        System.out.println("Datos de prueba cargados: Propietario Juan Perez.");
    }

    public static void main(String[] args) {
        System.out.println("El programa principal comenzó.");
    }
}
