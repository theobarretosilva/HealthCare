package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterMedicamento extends RecyclerView.Adapter<AdapterMedicamento.MyViewHolder> {

    private List<Medicamento> medicamentoList;

    public AdapterMedicamento(List<Medicamento> medicamentoList) {
        this.medicamentoList = medicamentoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Medicamento medicamento = medicamentoList.get(position);

        holder.nomeMedicamento.setText(medicamento.getNomeMedicamento());
        holder.qtdMedicamento.setText(medicamento.getQtdMedicamento() + " Uni.");
        holder.mgDosagemMedicamento.setText(medicamento.getMgDosagemMedicamento() + " mg ou ml");
        holder.horarioMedicamento.setText(medicamento.getHorarioMedicamento());
    }

    @Override
    public int getItemCount() {
        return medicamentoList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeMedicamento, qtdMedicamento, mgDosagemMedicamento, horarioMedicamento;
        Button btnIngerido;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeMedicamento = itemView.findViewById(R.id.nomeMedi);
            qtdMedicamento = itemView.findViewById(R.id.qtdMedi);
            mgDosagemMedicamento = itemView.findViewById(R.id.mgDosagemMedi);
            horarioMedicamento = itemView.findViewById(R.id.horaMedi);
            btnIngerido = itemView.findViewById(R.id.ingeridoMedica);
        }
    }
}