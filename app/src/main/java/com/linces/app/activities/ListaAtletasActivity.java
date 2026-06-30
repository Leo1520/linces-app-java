package com.linces.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.linces.app.R;
import com.linces.app.adapters.AtletaAdapter;
import com.linces.app.database.DatabaseHelper;
import com.linces.app.models.Atleta;
import com.linces.app.repositories.AtletaRepository;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ListaAtletasActivity extends AppCompatActivity implements AtletaAdapter.OnAtletaClickListener {

    private RecyclerView recyclerAtletas;
    private View tvVacio;
    private AtletaAdapter adapter;
    private AtletaRepository repository;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_atletas);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.title_atletas));
        }

        repository = new AtletaRepository(DatabaseHelper.getInstance(this));

        recyclerAtletas = findViewById(R.id.recyclerAtletas);
        tvVacio = findViewById(R.id.tvVacio);
        FloatingActionButton fabAgregar = findViewById(R.id.fabAgregar);

        adapter = new AtletaAdapter(this);
        recyclerAtletas.setLayoutManager(new LinearLayoutManager(this));
        recyclerAtletas.setAdapter(adapter);

        fabAgregar.setOnClickListener(v ->
            startActivity(new Intent(this, PerfilAtletaActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarAtletas();
    }

    private void cargarAtletas() {
        executor.execute(() -> {
            List<Atleta> lista = repository.listarActivos();
            mainHandler.post(() -> {
                adapter.actualizarLista(lista);
                tvVacio.setVisibility(lista.isEmpty() ? View.VISIBLE : View.GONE);
            });
        });
    }

    @Override
    public void onAtletaClick(Atleta atleta) {
        Intent intent = new Intent(this, PerfilAtletaActivity.class);
        intent.putExtra("atletaId", atleta.getId());
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}
