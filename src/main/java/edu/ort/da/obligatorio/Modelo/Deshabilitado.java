package edu.ort.da.obligatorio.Modelo;

public class Deshabilitado extends EstadoPropietarioImpl {

    private static final Deshabilitado instancia = new Deshabilitado();

    private Deshabilitado() {
    }

    @Override
    protected EstadoPropietario getDestinoHabilitado() {
        return Habilitado.getInstancia();
    }

    public static Deshabilitado getInstancia() {
        return instancia;
    }

    @Override
    public boolean puedeIngresarAlSistema() {
        return false;
    }

    @Override
    public boolean puedeRealizarTransitos() {
        return false;
    }

    @Override
    public boolean puedeRecibirBonificaciones() {
        return false;
    }

    public void manejarEntrada(Propietario propietario) {
        // Lógica específica al entrar en el estado Deshabilitado
        System.out.println("El propietario " + propietario.getNombre() + " ha sido deshabilitado y no puede ingresar al sistema.");
    }

}
