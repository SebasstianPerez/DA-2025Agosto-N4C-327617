package edu.ort.da.obligatorio.Modelo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
public abstract class Usuario {
    
    @Setter(AccessLevel.NONE)
    private Long id;

    private String CedulaDeIdentidad;

    @Getter(AccessLevel.NONE)
    private String Contrasena;

    private String Nombre;

    private String Apellido;

    public Usuario(String cedulaDeIdentidad, String contrasena, String nombre, String apellido) {
        this.CedulaDeIdentidad = cedulaDeIdentidad;
        this.Contrasena = contrasena;
        this.Nombre = nombre;
        this.Apellido = apellido;
    }

    public String getNombre() {
        return this.Nombre;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return this.id;
    }

    public String getCedula(){
        return this.CedulaDeIdentidad;
    }

    public boolean coincideContrasenia(String pwd) {
        return this.Contrasena != null && this.Contrasena.equals(pwd);
    }
}
