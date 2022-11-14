package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterAlimento extends RecyclerView.Adapter<AdapterAlimento.MyViewHolder> {

    private List<Alimentacao> alimentacaoList;

    public AdapterAlimento(List<Alimentacao> alimentacaoList) {
        this.alimentacaoList = alimentacaoList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alimento, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Alimentacao alimentacao = alimentacaoList.get(position);

        try {
            holder.nomeAlimentoCard.setText(alimentacao.getAlimento());
            holder.gAlimentoCard.setText("" + alimentacao.getGramas() + " g");
            holder.kcalAlimentoCard.setText("" + alimentacao.getKcal() + " kcal");
        } catch (Exception e){
            System.out.println(e);
        }

    }

    @Override
    public int getItemCount() {
        return alimentacaoList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeAlimentoCard, gAlimentoCard, kcalAlimentoCard;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeAlimentoCard = itemView.findViewById(R.id.nomeAlimentoCard);
            gAlimentoCard = itemView.findViewById(R.id.gAlimentoCard);
            kcalAlimentoCard = itemView.findViewById(R.id.kcalAlimentoCard);
        }
    }
}