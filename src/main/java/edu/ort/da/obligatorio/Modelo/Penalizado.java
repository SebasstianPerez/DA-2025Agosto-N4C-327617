package edu.ort.da.obligatorio.Modelo;

public class Penalizado extends EstadoPropietarioImpl {

    private static final Penalizado instancia = new Penalizado();

    private Penalizado() {
    }

    public static Penalizado getInstancia() {
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
    public boolean aplicanBonificaciones() {
        return false;
    }

    @Override
    public boolean registranNotificaciones() {
        return false;
    }

    @Override
    public void manejarEntrada(Propietario propietario) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'manejarEntrada'");
    }
}
