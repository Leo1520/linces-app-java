package com.linces.app.models;

public class Atleta {
    private int id;
    private String nombre;
    private String apellido;
    private String fechaNacimiento; // formato: YYYY-MM-DD
    private String disciplina;
    private String categoria;       // 'Infantil' | 'Juvenil'
    private String grupo;
    private String fotoUri;
    private boolean activo;

    public Atleta() {}

    public Atleta(String nombre, String apellido, String fechaNacimiento,
                  String disciplina, String categoria, String grupo) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.disciplina = disciplina;
        this.categoria = categoria;
        this.grupo = grupo;
        this.activo = true;
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public String getFotoUri() { return fotoUri; }
    public void setFotoUri(String fotoUri) { this.fotoUri = fotoUri; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
