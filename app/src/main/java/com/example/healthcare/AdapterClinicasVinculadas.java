package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterClinicasVinculadas extends RecyclerView.Adapter<AdapterClinicasVinculadas.MyViewHolder> {

    private List<Clinica> clinicasList;

    public AdapterClinicasVinculadas(List<Clinica> clinicasVinculadasList) {
        this.clinicasList = clinicasVinculadasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_clinica, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Clinica clinica = clinicasList.get(position);

        holder.nomeClinica.setText(clinica.getNomeClinica());
    }

    @Override
    public int getItemCount() {
        return clinicasList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeClinica;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeClinica = itemView.findViewById(R.id.clinicaNome);
        }
    }
}
