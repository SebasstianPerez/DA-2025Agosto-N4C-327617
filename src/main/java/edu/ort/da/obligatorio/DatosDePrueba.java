package edu.ort.da.obligatorio;

import java.time.LocalDateTime;
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
        Propietario propietario = new Propietario("23456789", "prop.123", "Usuario", "Propietario", 1000);
        Administrador admin = new Administrador("12345678", "admin.123", "Usuario", "Administrador");

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

        Collection<Tarifa> tarifas2 = new ArrayList<>();
        Tarifa tarifa4 = new Tarifa(200, cat1);
        Tarifa tarifa5 = new Tarifa(300, cat2);
        Tarifa tarifa6 = new Tarifa(100, cat3);

        tarifas2.addAll(Arrays.asList(tarifa4, tarifa5, tarifa6));

        Puesto puesto = new Puesto("Accesos-101", "Accesos 101", tarifas);
        Puesto puesto2 = new Puesto("Accesos-202", "Accesos 202", tarifas2);

        fachada.agregarPuesto(puesto);
        fachada.agregarPuesto(puesto2);

        Bonificacion b1 = new Bonificacion("Exonerados", exoneradosStrategy);
        Bonificacion b2 = new Bonificacion("Trabajadores", trabajadoresStrategy);
        Bonificacion b3 = new Bonificacion("Frecuentes", frecuentesStrategy);
        Bonificacion b4 = new Bonificacion("Vacio", sinDescuentoStrategy);

        fachada.agregarBonificacion(b1);
        fachada.agregarBonificacion(b2);
        fachada.agregarBonificacion(b3);
        fachada.agregarBonificacion(b4);

        PropietarioBonificacion pb = new PropietarioBonificacion(propietario, puesto, b2);
        PropietarioBonificacion pb1 = new PropietarioBonificacion(propietario, puesto2, b4);

        fachada.agregarPropietarioBonificacion(propietario.getCedula(), puesto.getDireccion(), b2.getNombre());
        fachada.agregarPropietarioBonificacion(propietario.getCedula(), puesto2.getDireccion(), b4.getNombre());

        fachada.emularTransito(puesto.getDireccion(), v1.getMatricula(), LocalDateTime.now().minusDays(10));
        fachada.emularTransito(puesto.getDireccion(), v1.getMatricula(), LocalDateTime.now().minusDays(5));

        System.out.println("--- PRECARGA FINALIZADA ---");
    }
}
