package edu.ort.da.obligatorio.Observador;

public interface Observable {
    public void agregarObservador(Observador obs);

    public void quitarObservador(Observador obs);
}