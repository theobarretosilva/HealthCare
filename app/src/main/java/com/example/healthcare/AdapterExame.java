package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterExame extends RecyclerView.Adapter<AdapterExame.MyViewHolder> {

    private List<Exame> exameList;

    public AdapterExame(List<Exame> exameList) {
        this.exameList = exameList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exame, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Exame exame = exameList.get(position);

        holder.dataExame.setText(exame.getData());
        holder.localExame.setText(exame.getClinica());
        holder.tipoExame.setText(exame.getTipo());
        holder.horarioExame.setText(exame.getHorario());
    }

    @Override
    public int getItemCount() {
        return exameList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView dataExame, localExame, tipoExame, horarioExame;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dataExame = itemView.findViewById(R.id.dataExame);
            localExame = itemView.findViewById(R.id.localExame);
            tipoExame = itemView.findViewById(R.id.tipoExame);
            horarioExame = itemView.findViewById(R.id.horarioExame);
        }
    }
}