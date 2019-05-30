package com.temas.selectos.eefi;

public class Cedes {

    private String nombre;
    private int capacitdad;
    private int idMapa;

    public Cedes(String nombre, int capacitdad, int idMapa) {
        this.nombre = nombre;
        this.capacitdad = capacitdad;
        this.idMapa = idMapa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCapacitdad() {
        return capacitdad;
    }

    public void setCapacitdad(int capacitdad) {
        this.capacitdad = capacitdad;
    }

    public int getIdMapa() {
        return idMapa;
    }

    public void setIdMapa(int idMapa) {
        this.idMapa = idMapa;
    }



}
