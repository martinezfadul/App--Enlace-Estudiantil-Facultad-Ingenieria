package com.temas.selectos.eefi;

public class Evento {

    private String descripcion;
    private String nombre;
    private int idPoster;
    private String fecha;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdPoster() {
        return idPoster;
    }
    public void setIdPoster(int idPoster) {
        this.idPoster = idPoster;
    }

    public Evento(String descripcion, String nombre, int idPoster, String fecha) {
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.idPoster = idPoster;
        this.fecha = fecha;
    }
}
