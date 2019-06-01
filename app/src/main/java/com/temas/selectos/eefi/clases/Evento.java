package com.temas.selectos.eefi.clases;

public class Evento {

    private String descripcion;
    private String nombre;
    private int idPoster;
    private String fecha;
    private String hora;
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

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



    public static class Cedes {

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
}
