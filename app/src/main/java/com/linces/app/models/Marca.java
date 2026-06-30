package com.linces.app.models;

public class Marca {
    private int id;
    private int atletaId;
    private int sesionId;       // 0 si no pertenece a ninguna sesión
    private String tipo;        // ej: '100m planos', 'salto largo'
    private double valor;
    private String unidad;      // ej: 'segundos', 'metros'
    private String fecha;       // YYYY-MM-DD
    private String notas;
    private boolean esMarcaPersonal;

    public Marca() {}

    public Marca(int atletaId, String tipo, double valor, String unidad, String fecha) {
        this.atletaId = atletaId;
        this.tipo = tipo;
        this.valor = valor;
        this.unidad = unidad;
        this.fecha = fecha;
        this.esMarcaPersonal = false;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAtletaId() { return atletaId; }
    public void setAtletaId(int atletaId) { this.atletaId = atletaId; }

    public int getSesionId() { return sesionId; }
    public void setSesionId(int sesionId) { this.sesionId = sesionId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public String getUnidad() { return unidad; }
    public void setUnidad(String unidad) { this.unidad = unidad; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }

    public boolean isEsMarcaPersonal() { return esMarcaPersonal; }
    public void setEsMarcaPersonal(boolean esMarcaPersonal) { this.esMarcaPersonal = esMarcaPersonal; }
}
