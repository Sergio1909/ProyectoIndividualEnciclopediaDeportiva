package com.example.enciclopediadeportiva.Entidades;

public class DeporteDto {
    private String apiKey;
    private String tipo;
    private String nombre;
    private String descripcion;
    private String foto;
    private String foto1;
    private String foto2;
    private NoticiaDto[] listaNoticias;

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public NoticiaDto[] getListaNoticias() {
        return listaNoticias;
    }

    public void setListaNoticias(NoticiaDto[] listaNoticias) {
        this.listaNoticias = listaNoticias;
    }
}
