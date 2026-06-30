package com.linces.app.database;

import com.linces.app.models.Entrenador;
import com.linces.app.repositories.EntrenadorRepository;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {

    private final EntrenadorRepository repo;

    public AuthService(DatabaseHelper dbHelper) {
        this.repo = new EntrenadorRepository(dbHelper);
    }

    public boolean hayEntrenadorRegistrado() {
        return repo.existeEntrenador();
    }

    public boolean registrarEntrenador(String correo, String contrasena,
                                       String preguntaSeguridad, String respuestaSeguridad) {
        String contrasenaHash = sha256(contrasena);
        String respuestaHash  = sha256(respuestaSeguridad.trim().toLowerCase());
        long id = repo.registrar(correo.trim().toLowerCase(), contrasenaHash,
                                 preguntaSeguridad, respuestaHash);
        return id != -1;
    }

    public boolean iniciarSesion(String correo, String contrasena) {
        Entrenador entrenador = repo.obtenerPorCorreo(correo.trim().toLowerCase());
        if (entrenador == null) return false;
        String contrasenaHash = sha256(contrasena);
        return contrasenaHash.equals(entrenador.getContrasena());
    }

    public boolean verificarRespuestaSeguridad(String correo, String respuesta) {
        Entrenador entrenador = repo.obtenerPorCorreo(correo.trim().toLowerCase());
        if (entrenador == null || entrenador.getRespuestaSeguridad() == null) return false;
        String respuestaHash = sha256(respuesta.trim().toLowerCase());
        return respuestaHash.equals(entrenador.getRespuestaSeguridad());
    }

    private static String sha256(String texto) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(texto.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 no disponible", e);
        }
    }
}
