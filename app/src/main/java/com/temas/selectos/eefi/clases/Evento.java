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
        private String Mapa;

        public Cedes(String nombre, String Mapa) {
            this.nombre = nombre;
            this.Mapa = Mapa;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getMapa() {
            return Mapa;
        }

        public void setMapa(String Mapa) {
            this.Mapa = Mapa;
        }



    }
}
