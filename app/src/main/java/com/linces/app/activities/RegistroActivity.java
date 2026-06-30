package com.linces.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.linces.app.R;
import com.linces.app.database.AuthService;
import com.linces.app.database.DatabaseHelper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RegistroActivity extends AppCompatActivity {

    private TextInputEditText etCorreo, etContrasena, etConfirmar, etPregunta, etRespuesta;
    private MaterialButton btnRegistrar;
    private ProgressBar progressBar;

    private AuthService authService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Crear cuenta");
        }

        authService = new AuthService(DatabaseHelper.getInstance(this));

        etCorreo    = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etConfirmar  = findViewById(R.id.etConfirmar);
        etPregunta   = findViewById(R.id.etPregunta);
        etRespuesta  = findViewById(R.id.etRespuesta);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        progressBar  = findViewById(R.id.progressBar);

        btnRegistrar.setOnClickListener(v -> handleRegistrar());
    }

    private void handleRegistrar() {
        String correo     = etCorreo.getText() != null ? etCorreo.getText().toString().trim() : "";
        String contrasena = etContrasena.getText() != null ? etContrasena.getText().toString() : "";
        String confirmar  = etConfirmar.getText() != null ? etConfirmar.getText().toString() : "";
        String pregunta   = etPregunta.getText() != null ? etPregunta.getText().toString().trim() : "";
        String respuesta  = etRespuesta.getText() != null ? etRespuesta.getText().toString().trim() : "";

        if (correo.isEmpty()) {
            mostrarAlerta("Campo requerido", "Ingresa tu correo electrónico.");
            return;
        }
        if (contrasena.length() < 6) {
            mostrarAlerta("Contraseña débil", "La contraseña debe tener al menos 6 caracteres.");
            return;
        }
        if (!contrasena.equals(confirmar)) {
            mostrarAlerta("Error", "Las contraseñas no coinciden.");
            return;
        }
        if (pregunta.isEmpty() || respuesta.isEmpty()) {
            mostrarAlerta("Campo requerido", "Completa la pregunta y respuesta de seguridad.");
            return;
        }

        setLoading(true);

        final String correofinal    = correo;
        final String contrasenafinal = contrasena;
        final String preguntafinal   = pregunta;
        final String respuestafinal  = respuesta;

        executor.execute(() -> {
            boolean ok;
            try {
                ok = authService.registrarEntrenador(correofinal, contrasenafinal,
                                                     preguntafinal, respuestafinal);
            } catch (Exception e) {
                ok = false;
            }
            final boolean resultado = ok;
            mainHandler.post(() -> {
                setLoading(false);
                if (resultado) {
                    Intent intent = new Intent(RegistroActivity.this, PrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    mostrarAlerta("Error", "No se pudo crear la cuenta. Intenta de nuevo.");
                }
            });
        });
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        new AlertDialog.Builder(this)
            .setTitle(titulo)
            .setMessage(mensaje)
            .setPositiveButton("Aceptar", null)
            .show();
    }

    private void setLoading(boolean guardando) {
        btnRegistrar.setEnabled(!guardando);
        btnRegistrar.setText(guardando ? "Guardando…" : "Crear cuenta");
        progressBar.setVisibility(guardando ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
