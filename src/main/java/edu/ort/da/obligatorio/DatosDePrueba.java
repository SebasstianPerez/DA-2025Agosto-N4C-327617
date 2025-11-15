package edu.ort.da.obligatorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import edu.ort.da.obligatorio.Modelo.Administrador;
import edu.ort.da.obligatorio.Modelo.Bonificacion;
import edu.ort.da.obligatorio.Modelo.CategoriaVehiculo;
import edu.ort.da.obligatorio.Modelo.EstrategiaBonificacion;
import edu.ort.da.obligatorio.Modelo.EstrategiaBonificacionNull;
import edu.ort.da.obligatorio.Modelo.EstrategiaExonerados;
import edu.ort.da.obligatorio.Modelo.EstrategiaFrecuentes;
import edu.ort.da.obligatorio.Modelo.EstrategiaTrabajadores;
import edu.ort.da.obligatorio.Modelo.Propietario;
import edu.ort.da.obligatorio.Modelo.PropietarioBonificacion;
import edu.ort.da.obligatorio.Modelo.Puesto;
import edu.ort.da.obligatorio.Modelo.Tarifa;
import edu.ort.da.obligatorio.Modelo.Vehiculo;
import edu.ort.da.obligatorio.Servicios.Fachada;

@Component
public class DatosDePrueba implements CommandLineRunner {
    @Autowired
    private Fachada fachada;

    @Override
    public void run(String... args) throws Exception {

        // AGREGAR USUARIOS
        Propietario propietario = new Propietario("1234123", "juan1234", "JuanPerez1234", "Perez", 1000);
        Administrador admin = new Administrador("1234123", "admin", "admin", "");

        fachada.addPropietario(propietario);
        fachada.addAdministrador(admin);

        // AGREGAR CATEGORIAS

        CategoriaVehiculo cat1 = new CategoriaVehiculo("AUTO");
        CategoriaVehiculo cat2 = new CategoriaVehiculo("CAMION");
        CategoriaVehiculo cat3 = new CategoriaVehiculo("MOTO");

        fachada.addCategoriaVehiculo(cat1);
        fachada.addCategoriaVehiculo(cat2);
        fachada.addCategoriaVehiculo(cat3);

        // VEHICULOS

        Vehiculo v1 = new Vehiculo("AIJ1234", cat1, "Verde", "Tera", propietario);

        fachada.addVehiculo(v1);
        
        fachada.asignarVehiculoAPropietario(propietario, v1);

        // ESTRATEGIAS

        EstrategiaBonificacion exoneradosStrategy = new EstrategiaExonerados();
        EstrategiaBonificacion trabajadoresStrategy = new EstrategiaTrabajadores();
        EstrategiaBonificacion frecuentesStrategy = new EstrategiaFrecuentes();
        EstrategiaBonificacion sinDescuentoStrategy = new EstrategiaBonificacionNull();

        // AGREGAR PUESTOS

        Collection<Tarifa> tarifas = new ArrayList<>();
        Tarifa tarifa1 = new Tarifa(100, cat1);
        Tarifa tarifa2 = new Tarifa(200, cat2);
        Tarifa tarifa3 = new Tarifa(50, cat3);

        tarifas.addAll(Arrays.asList(tarifa1, tarifa2, tarifa3));

        Puesto puesto = new Puesto("Accesos1", "Accesos", tarifas);

        fachada.agregarPuesto(puesto);

        Bonificacion b1 = new Bonificacion("Exonerados", exoneradosStrategy);
        Bonificacion b2 = new Bonificacion("Trabajadores", trabajadoresStrategy);
        Bonificacion b3 = new Bonificacion("Frecuentes", frecuentesStrategy);
        Bonificacion b4 = new Bonificacion("Null", sinDescuentoStrategy);

        fachada.agregarBonificacion(b1);
        fachada.agregarBonificacion(b2);
        fachada.agregarBonificacion(b3);
        fachada.agregarBonificacion(b4);

        PropietarioBonificacion pb = new PropietarioBonificacion(propietario, puesto, b2);

        fachada.agregarPropietarioBonificacion(pb);

        System.out.println(propietario);

        System.out.println("--- PRECARGA FINALIZADA ---");
    }
}
