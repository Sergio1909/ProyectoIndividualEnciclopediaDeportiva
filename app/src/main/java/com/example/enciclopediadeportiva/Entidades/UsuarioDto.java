package com.example.enciclopediadeportiva.Entidades;

public class UsuarioDto {

    private String nombre;
    private String correo;
    private String contrasena;
    private String rol;

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    private DeporteDto[] listaDeportes;

    public UsuarioDto() {
    }

    public UsuarioDto(String nombre, String correo,  String rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.rol=rol;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public DeporteDto[] getListaDeportes() {
        return listaDeportes;
    }

    public void setListaDeportes(DeporteDto[] listaDeportes) {
        this.listaDeportes = listaDeportes;
    }
}
