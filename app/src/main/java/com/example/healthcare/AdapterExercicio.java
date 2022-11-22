package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterExercicio extends RecyclerView.Adapter<AdapterExercicio.MyViewHolder> {

    private List<Exercicio> exercicioList;

    public AdapterExercicio(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
    }

    @NonNull
    @Override
    public AdapterExercicio.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercicio, parent, false);
        return new AdapterExercicio.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterExercicio.MyViewHolder holder, int position) {
        Exercicio exercicio = exercicioList.get(position);

        holder.nomeExercicio.setText(exercicio.getNomeExercicio());
        holder.tempoExercicio.setText(exercicio.getTempoExercicio());
    }

    @Override
    public int getItemCount() {
        return exercicioList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeExercicio, tempoExercicio;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeExercicio = itemView.findViewById(R.id.nameExercicio);
            tempoExercicio = itemView.findViewById(R.id.timeExercicio);
        }
    }
}
