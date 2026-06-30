package com.linces.app.models;

public class CompetenciaAtleta {
    private int id;
    private int competenciaId;
    private int atletaId;
    private int posicion;           // 0 si no aplica
    private double marcaObtenida;   // 0 si no aplica

    public CompetenciaAtleta() {}

    public CompetenciaAtleta(int competenciaId, int atletaId) {
        this.competenciaId = competenciaId;
        this.atletaId = atletaId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getCompetenciaId() { return competenciaId; }
    public void setCompetenciaId(int competenciaId) { this.competenciaId = competenciaId; }

    public int getAtletaId() { return atletaId; }
    public void setAtletaId(int atletaId) { this.atletaId = atletaId; }

    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    public double getMarcaObtenida() { return marcaObtenida; }
    public void setMarcaObtenida(double marcaObtenida) { this.marcaObtenida = marcaObtenida; }
}
