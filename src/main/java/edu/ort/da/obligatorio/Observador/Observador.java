package edu.ort.da.obligatorio.Observador;

import edu.ort.da.obligatorio.Excepciones.PeajeException;

public interface Observador {
        public void actualizar(Object evento, ObservableAbstracto origen) throws PeajeException;
}
