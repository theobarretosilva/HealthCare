package com.example.healthcare;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterLembrete extends RecyclerView.Adapter<AdapterLembrete.MyViewHolder> {

    private List<Lembrete> lembreteList;

    public AdapterLembrete(List<Lembrete> lembreteList) {
        this.lembreteList = lembreteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lembrete, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Lembrete lembrete = lembreteList.get(position);

        holder.nomeLembrete.setText(lembrete.getNomeLembrete() + ";");
    }

    @Override
    public int getItemCount() {
        return lembreteList.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView nomeLembrete;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeLembrete = itemView.findViewById(R.id.lembreDescri);
        }
    }
}
