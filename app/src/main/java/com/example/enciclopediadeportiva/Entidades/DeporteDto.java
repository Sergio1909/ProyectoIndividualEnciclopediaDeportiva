package com.example.enciclopediadeportiva.Entidades;

public class DeporteDto {
    private String apiKey;
    private String tipo;
    private String nombre;
    private String descripcion;
    private String foto;
    private NoticiaDto[] listaNoticias;

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
