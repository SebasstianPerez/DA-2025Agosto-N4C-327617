package edu.ort.da.obligatorio.Observador;

import java.util.ArrayList;
import java.util.List;

public class ObservableAbstracto implements Observable {
    private List<Observador> observadores = new ArrayList<>();

    @Override
    public void agregarObservador(Observador obs) {
        if (!observadores.contains(obs)) {
            observadores.add(obs);
        }
    }

    @Override
    public void quitarObservador(Observador obs) {
        observadores.remove(obs);
    }

    public void avisar(Object evento) {
        List<Observador> copia = new ArrayList<>(observadores);
        for (Observador obs : copia) {
            obs.actualizar(evento, this);
            System.out.println("Notificado observador: " + obs);
        }
    }

}