package com.linces.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.linces.app.R;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_principal));
        }

        MaterialButton btnAtletas = findViewById(R.id.btnAtletas);
        MaterialButton btnAsistencia = findViewById(R.id.btnAsistencia);

        btnAtletas.setOnClickListener(v ->
            startActivity(new Intent(this, ListaAtletasActivity.class)));

        btnAsistencia.setOnClickListener(v ->
            Toast.makeText(this, "Próximamente", Toast.LENGTH_SHORT).show());
    }
}
