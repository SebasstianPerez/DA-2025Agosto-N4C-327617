package edu.ort.da.obligatorio.Modelo;

public class Habilitado extends EstadoPropietarioImpl {

    private static final Habilitado instancia = new Habilitado();
    
    private Habilitado() {}

    public static Habilitado getInstancia() {
        return instancia;
    }

    @Override
    protected EstadoPropietario getDestinoPenalizado() {
        return Penalizado.getInstancia();
    }

    @Override
    protected EstadoPropietario getDestinoSuspendido() {
        return Suspendido.getInstancia();
    }
    
    @Override
    protected EstadoPropietario getDestinoDeshabilitado() {
        return Deshabilitado.getInstancia();
    }

    @Override
    public void manejarEntrada(Propietario propietario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'manejarEntrada'");
    }


}
