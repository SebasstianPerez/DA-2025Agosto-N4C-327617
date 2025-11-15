package edu.ort.da.obligatorio.Modelo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Administrador extends Usuario {

    public Administrador( String cedulaDeIdentidad, String contrasena, String nombre, String apellido){
        super(cedulaDeIdentidad, contrasena, nombre, apellido);
    }
}
