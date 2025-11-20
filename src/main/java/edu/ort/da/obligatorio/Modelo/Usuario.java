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
    private NombreCompleto nombreCompleto;

    @Getter(AccessLevel.NONE)
    private String Contrasena;

    public Usuario(String cedulaDeIdentidad, String contrasena, String nombre, String apellido) {
        this.CedulaDeIdentidad = cedulaDeIdentidad;
        this.Contrasena = contrasena;
        this.nombreCompleto = new NombreCompleto();
        this.nombreCompleto.setNombre(nombre);
        this.nombreCompleto.setApellido(apellido);
    }

    public String getNombreCompleto() {
        return this.nombreCompleto.getNombre() + " " + this.nombreCompleto.getApellido();
    }

    public String getNombre() {
        return this.nombreCompleto.getNombre();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public String getCedula() {
        return this.CedulaDeIdentidad;
    }

    public boolean coincideContrasenia(String pwd) {
        return this.Contrasena != null && this.Contrasena.equals(pwd);
    }
}
