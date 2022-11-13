package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TelaExames extends AppCompatActivity {

    private AdapterExame adapterExame;
    private List<Exame> exameList = new ArrayList<>();

    private RecyclerView rvExames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exames);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        rvExames = findViewById(R.id.rv);

        recuperaExames();
        configReciclerView();
    }

    public void irTelaAddExame(View g){
        Intent irTelaAddExame = new Intent(this, TelaExames_add.class);
        startActivity(irTelaAddExame);
    }

    public void voltarTelaConteudos(View h){
        Intent voltarTelaConteudos = new Intent(this, TelaConteudos_Premium.class);
        startActivity(voltarTelaConteudos);
    }

    private void configReciclerView(){
        rvExames.setLayoutManager(new LinearLayoutManager(this));
        rvExames.setHasFixedSize(true);
        adapterExame = new AdapterExame(exameList);
        rvExames.setAdapter(adapterExame);
    }

    public void recuperaExames(){
        DatabaseReference examesRef = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Exames");
        examesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Exame exame = snap.getValue(Exame.class);
                    exameList.add(exame);
                }
                adapterExame.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}