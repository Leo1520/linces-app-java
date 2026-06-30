package com.linces.app.models;

public class Competencia {
    private int id;
    private String nombre;
    private String fecha;       // YYYY-MM-DD
    private String lugar;
    private String descripcion;

    public Competencia() {}

    public Competencia(String nombre, String fecha, String lugar, String descripcion) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.lugar = lugar;
        this.descripcion = descripcion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
