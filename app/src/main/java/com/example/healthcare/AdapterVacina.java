package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterVacina extends RecyclerView.Adapter<AdapterVacina.MyViewHolder> {

    private List<Vacinas> vacinasList;

    public AdapterVacina(List<Vacinas> vacinasList) {
        this.vacinasList = vacinasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vacina, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Vacinas vacinas = vacinasList.get(position);

        holder.titulo.setText(vacinas.getTitulo());
        holder.subtitulo.setText(vacinas.getSubtitulo());
    }

    @Override
    public int getItemCount() {
        return vacinasList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, subtitulo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.tituloCardVacina);
            subtitulo = itemView.findViewById(R.id.subtituloCardVacina);
        }
    }
}
