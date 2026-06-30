package com.linces.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.linces.app.R;
import com.linces.app.database.AuthService;
import com.linces.app.database.DatabaseHelper;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etCorreo, etContrasena;
    private TextView tvError;
    private MaterialButton btnIngresar;
    private ProgressBar progressBar;

    private AuthService authService;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Ingresar");
        }

        authService = new AuthService(DatabaseHelper.getInstance(this));

        etCorreo     = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        tvError      = findViewById(R.id.tvError);
        btnIngresar  = findViewById(R.id.btnIngresar);
        progressBar  = findViewById(R.id.progressBar);

        btnIngresar.setOnClickListener(v -> handleIngresar());
    }

    private void handleIngresar() {
        String correo    = etCorreo.getText() != null ? etCorreo.getText().toString().trim() : "";
        String contrasena = etContrasena.getText() != null ? etContrasena.getText().toString() : "";

        tvError.setVisibility(View.GONE);

        if (correo.isEmpty() || contrasena.isEmpty()) {
            mostrarError("Completa todos los campos.");
            return;
        }

        setLoading(true);

        executor.execute(() -> {
            boolean ok;
            try {
                ok = authService.iniciarSesion(correo, contrasena);
            } catch (Exception e) {
                ok = false;
            }
            final boolean resultado = ok;
            mainHandler.post(() -> {
                setLoading(false);
                if (resultado) {
                    Intent intent = new Intent(LoginActivity.this, PrincipalActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    mostrarError("Correo o contraseña incorrectos.");
                }
            });
        });
    }

    private void mostrarError(String mensaje) {
        tvError.setText(mensaje);
        tvError.setVisibility(View.VISIBLE);
    }

    private void setLoading(boolean cargando) {
        btnIngresar.setEnabled(!cargando);
        btnIngresar.setText(cargando ? "Verificando…" : "Ingresar");
        progressBar.setVisibility(cargando ? View.VISIBLE : View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
