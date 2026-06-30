package com.linces.app.models;

public class Sesion {
    private int id;
    private String fecha;           // YYYY-MM-DD
    private String horaInicio;      // HH:MM
    private String horaFin;         // HH:MM (opcional)
    private String descripcion;
    private String disciplina;
    private String lugar;
    private String grupo;
    private String estado;          // 'activa' | 'cancelada' | 'finalizada'
    private String motivoCancelacion;

    public Sesion() {}

    public Sesion(String fecha, String horaInicio, String descripcion, String disciplina) {
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.descripcion = descripcion;
        this.disciplina = disciplina;
        this.estado = "activa";
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getHoraInicio() { return horaInicio; }
    public void setHoraInicio(String horaInicio) { this.horaInicio = horaInicio; }

    public String getHoraFin() { return horaFin; }
    public void setHoraFin(String horaFin) { this.horaFin = horaFin; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public String getLugar() { return lugar; }
    public void setLugar(String lugar) { this.lugar = lugar; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getMotivoCancelacion() { return motivoCancelacion; }
    public void setMotivoCancelacion(String motivoCancelacion) { this.motivoCancelacion = motivoCancelacion; }
}
