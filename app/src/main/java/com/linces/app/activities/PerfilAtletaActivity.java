package com.linces.app.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.linces.app.R;
import com.linces.app.database.DatabaseHelper;
import com.linces.app.models.Atleta;
import com.linces.app.repositories.AtletaRepository;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PerfilAtletaActivity extends AppCompatActivity {

    public static final String EXTRA_ATLETA_ID = "atletaId";

    private TextInputEditText etNombre, etApellido, etFechaNacimiento, etGrupo;
    private Spinner spinnerCategoria, spinnerDisciplina;
    private MaterialButton btnGuardar;

    private AtletaRepository repository;
    private int atletaId = -1;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_atleta);

        repository = new AtletaRepository(DatabaseHelper.getInstance(this));

        etNombre          = findViewById(R.id.etNombre);
        etApellido        = findViewById(R.id.etApellido);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        etGrupo           = findViewById(R.id.etGrupo);
        spinnerCategoria  = findViewById(R.id.spinnerCategoria);
        spinnerDisciplina = findViewById(R.id.spinnerDisciplina);
        btnGuardar        = findViewById(R.id.btnGuardar);

        configurarSpinners();

        atletaId = getIntent().getIntExtra(EXTRA_ATLETA_ID, -1);
        if (atletaId != -1) {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Editar atleta");
            cargarAtleta(atletaId);
        } else {
            if (getSupportActionBar() != null) getSupportActionBar().setTitle("Nuevo atleta");
        }

        btnGuardar.setOnClickListener(v -> guardar());
    }

    private void configurarSpinners() {
        ArrayAdapter<CharSequence> adapterCat = ArrayAdapter.createFromResource(
            this, R.array.categorias_atleta, android.R.layout.simple_spinner_item);
        adapterCat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapterCat);

        ArrayAdapter<CharSequence> adapterDis = ArrayAdapter.createFromResource(
            this, R.array.disciplinas_atleta, android.R.layout.simple_spinner_item);
        adapterDis.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDisciplina.setAdapter(adapterDis);
    }

    private void cargarAtleta(int id) {
        executor.execute(() -> {
            Atleta atleta = repository.obtenerPorId(id);
            mainHandler.post(() -> {
                if (atleta == null) return;
                etNombre.setText(atleta.getNombre());
                etApellido.setText(atleta.getApellido());
                etFechaNacimiento.setText(atleta.getFechaNacimiento());
                etGrupo.setText(atleta.getGrupo());
                seleccionarEnSpinner(spinnerCategoria, atleta.getCategoria());
                seleccionarEnSpinner(spinnerDisciplina, atleta.getDisciplina());
            });
        });
    }

    private void seleccionarEnSpinner(Spinner spinner, String valor) {
        if (valor == null) return;
        ArrayAdapter<?> adapter = (ArrayAdapter<?>) spinner.getAdapter();
        for (int i = 0; i < adapter.getCount(); i++) {
            if (valor.equals(adapter.getItem(i).toString())) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    private void guardar() {
        String nombre = etNombre.getText() != null ? etNombre.getText().toString().trim() : "";
        String apellido = etApellido.getText() != null ? etApellido.getText().toString().trim() : "";
        String fecha = etFechaNacimiento.getText() != null ? etFechaNacimiento.getText().toString().trim() : "";
        String grupo = etGrupo.getText() != null ? etGrupo.getText().toString().trim() : "";
        String categoria = spinnerCategoria.getSelectedItem().toString();
        String disciplina = spinnerDisciplina.getSelectedItem().toString();

        if (nombre.isEmpty() || apellido.isEmpty() || fecha.isEmpty()) {
            Toast.makeText(this, "Nombre, apellido y fecha son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        Atleta atleta = new Atleta();
        atleta.setNombre(nombre);
        atleta.setApellido(apellido);
        atleta.setFechaNacimiento(fecha);
        atleta.setCategoria(categoria);
        atleta.setDisciplina(disciplina);
        atleta.setGrupo(grupo.isEmpty() ? null : grupo);
        atleta.setActivo(true);

        executor.execute(() -> {
            if (atletaId == -1) {
                repository.insertar(atleta);
            } else {
                atleta.setId(atletaId);
                repository.actualizar(atleta);
            }
            mainHandler.post(() -> {
                Toast.makeText(this, getString(R.string.msg_guardado_exitoso), Toast.LENGTH_SHORT).show();
                finish();
            });
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
