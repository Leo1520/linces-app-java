package com.linces.app.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.linces.app.R;
import com.linces.app.models.Atleta;
import java.util.ArrayList;
import java.util.List;

public class AtletaAdapter extends RecyclerView.Adapter<AtletaAdapter.ViewHolder> {

    public interface OnAtletaClickListener {
        void onAtletaClick(Atleta atleta);
    }

    private List<Atleta> atletas = new ArrayList<>();
    private final OnAtletaClickListener listener;

    public AtletaAdapter(OnAtletaClickListener listener) {
        this.listener = listener;
    }

    public void actualizarLista(List<Atleta> nuevaLista) {
        this.atletas = nuevaLista;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.item_atleta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Atleta atleta = atletas.get(position);
        holder.tvNombreCompleto.setText(atleta.getNombreCompleto());
        String detalle = atleta.getCategoria() + " · " + atleta.getDisciplina() +
            (atleta.getGrupo() != null && !atleta.getGrupo().isEmpty() ? " · " + atleta.getGrupo() : "");
        holder.tvDetalle.setText(detalle);
        holder.itemView.setOnClickListener(v -> listener.onAtletaClick(atleta));
    }

    @Override
    public int getItemCount() {
        return atletas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCompleto;
        TextView tvDetalle;

        ViewHolder(View itemView) {
            super(itemView);
            tvNombreCompleto = itemView.findViewById(R.id.tvNombreCompleto);
            tvDetalle = itemView.findViewById(R.id.tvDetalle);
        }
    }
}
