package com.linces.app.models;

public class Asistencia {
    // Estado: 'P' = Presente, 'A' = Ausente, 'L' = Licencia
    public static final String PRESENTE = "P";
    public static final String AUSENTE   = "A";
    public static final String LICENCIA  = "L";

    private int id;
    private int atletaId;
    private int sesionId;
    private String estado;

    public Asistencia() {}

    public Asistencia(int atletaId, int sesionId, String estado) {
        this.atletaId = atletaId;
        this.sesionId = sesionId;
        this.estado = estado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getAtletaId() { return atletaId; }
    public void setAtletaId(int atletaId) { this.atletaId = atletaId; }

    public int getSesionId() { return sesionId; }
    public void setSesionId(int sesionId) { this.sesionId = sesionId; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
