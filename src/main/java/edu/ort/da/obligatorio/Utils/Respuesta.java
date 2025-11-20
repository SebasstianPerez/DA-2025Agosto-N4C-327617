package edu.ort.da.obligatorio.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Respuesta {
  private String id;
    private Object parametro;

    public Respuesta(String id, Object parametro) {
        this.id = id;
        this.parametro = parametro;
    }

    public Respuesta() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getParametro() {
        return parametro;
    }

    public void setParametro(Object parametro) {
        this.parametro = parametro;
    }
    
    public static List<Respuesta> lista(Respuesta... respuestas){
         List<Respuesta> retorno = new ArrayList<>();
         retorno.addAll(Arrays.asList(respuestas));
         return retorno;
    }
}