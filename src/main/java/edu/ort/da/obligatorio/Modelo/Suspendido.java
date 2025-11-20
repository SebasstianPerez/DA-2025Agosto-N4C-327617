package edu.ort.da.obligatorio.Modelo;

public class Suspendido extends EstadoPropietarioImpl {

    private static final Suspendido instancia = new Suspendido();

    private Suspendido() {
    }

    public static Suspendido getInstancia() {
        return instancia;
    }

    @Override
    protected EstadoPropietario getDestinoHabilitado() {
        return Habilitado.getInstancia();
    }
    
    @Override
    protected EstadoPropietario getDestinoDeshabilitado() {
        return Deshabilitado.getInstancia();
    }

    @Override
    public boolean puedeRealizarTransitos() {
        return false;
    }

    @Override
    public void manejarEntrada(Propietario propietario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'manejarEntrada'");
    }
}
