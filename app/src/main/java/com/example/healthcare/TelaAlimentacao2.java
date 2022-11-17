package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TelaAlimentacao2 extends AppCompatActivity {

    TextView dataAlimentacao2, tipoAlimentacao, textSemAlimento;
    ImageView imgSemAlimento, maisAlimento;
    RecyclerView rvAlimentos;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    private List<Alimentacao> alimentacaoList = new ArrayList<>();
    private AdapterAlimento adapterAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao2);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        recuperarAlimentos();
        configReciclerView();
        tipoAlimentacao.setText(TelaAlimentacao.tipoAlimentacao);
    }

    private void iniciarComponentes(){
        dataAlimentacao2 = findViewById(R.id.dataAlimentacao2);
        dataAlimentacao2.setText(dataHoje);
        tipoAlimentacao = findViewById(R.id.tipoAlimentacao);
        rvAlimentos = findViewById(R.id.rvAlimentacao);
        textSemAlimento = findViewById(R.id.textSemAlimento);
        imgSemAlimento = findViewById(R.id.imgSemAlimento);
        maisAlimento = findViewById(R.id.maisAlimento);
    }

    @Override
    protected void onStart() {
        super.onStart();

            DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                    .child("Registros")
                    .child(FirebaseHelper.getUIDUsuario())
                    .child("Alimentação")
                    .child(TelaAlimentacao.tipoAlimentacao);
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (!snapshot.exists()){
                        rvAlimentos.setVisibility(View.GONE);
                        maisAlimento.setVisibility(View.GONE);
                        textSemAlimento.setVisibility(View.VISIBLE);
                        imgSemAlimento.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

    }

    public void configReciclerView(){
        rvAlimentos.setLayoutManager(new LinearLayoutManager(this));
        rvAlimentos.setHasFixedSize(true);
        adapterAlimento = new AdapterAlimento(alimentacaoList);
        rvAlimentos.setAdapter(adapterAlimento);
    }

    public void recuperarAlimentos(){
        DatabaseReference reference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Alimentação")
                .child(TelaAlimentacao.tipoAlimentacao);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        alimentacaoList.clear();
                        Alimentacao alimentacao = snap.getValue(Alimentacao.class);
                        alimentacaoList.add(alimentacao);
                    }
                    adapterAlimento.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void voltarTelaAlimentacao(View ir){
        Intent voltarTela = new Intent(this, TelaAlimentacao.class);
        startActivity(voltarTela);
    }

    public void irTelaAddAlimentacao(View a){
        Intent irTelaAddAlimentacao = new Intent(this, TelaAlimentacao_add.class);
        startActivity(irTelaAddAlimentacao);
    }
}