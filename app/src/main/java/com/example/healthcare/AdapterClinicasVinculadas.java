package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClinicasVinculadas extends RecyclerView.Adapter<AdapterClinicasVinculadas.MyViewHolder> {

    private List<VincularClinicas> vincularClinicasList;

    public AdapterClinicasVinculadas(List<VincularClinicas> vincularClinicasList) {
        this.vincularClinicasList = vincularClinicasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinica, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VincularClinicas vincularClinicas = vincularClinicasList.get(position);

        holder.nomeClinica
    }
}
