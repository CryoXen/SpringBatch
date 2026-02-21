package com.academia.batch.model;

public class Cancion {

    // Campos que vienen del CSV
    private String titulo;
    private String artista;
    private String genero;
    private int    duracion_seg;
    private long   streams;

    // Campos calculados en el Processor (no vienen del CSV)
    private double pago_estimado;
    private String popularidad;

    public Cancion() {}

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getArtista() { return artista; }
    public void setArtista(String artista) { this.artista = artista; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public int getDuracion_seg() { return duracion_seg; }
    public void setDuracion_seg(int duracion_seg) { this.duracion_seg = duracion_seg; }

    public long getStreams() { return streams; }
    public void setStreams(long streams) { this.streams = streams; }

    public double getPago_estimado() { return pago_estimado; }
    public void setPago_estimado(double pago_estimado) { this.pago_estimado = pago_estimado; }

    public String getPopularidad() { return popularidad; }
    public void setPopularidad(String popularidad) { this.popularidad = popularidad; }

    @Override
    public String toString() {
        return titulo + " | " + artista + " | Streams: " + streams
                + " | Pago: $" + pago_estimado + " | " + popularidad;
    }
}
