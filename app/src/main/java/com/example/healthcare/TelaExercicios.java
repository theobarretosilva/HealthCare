package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
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

public class TelaExercicios extends AppCompatActivity {

    TextView dataAtual, textSemExercicio;
    ImageView maisExercicio, imgSemExercicio;

    private AdapterExercicio adapterExercicio;
    private List<Exercicio> exercicioList = new ArrayList<>();
    RecyclerView rvExercicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_exercicios);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarData();
        recuperaExames();
        configRecyclerView();
    }

    public void iniciarComponentes(){
        dataAtual = findViewById(R.id.dataAtualExercicios);
        rvExercicios = findViewById(R.id.rvExercicios);
        textSemExercicio = findViewById(R.id.textSemExercicio);
        maisExercicio = findViewById(R.id.maisExercicio);
        imgSemExercicio = findViewById(R.id.imgSemExercicio);
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseReference exerciciosRef = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Exercicios");
        exerciciosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()){
                    rvExercicios.setVisibility(View.INVISIBLE);
                    maisExercicio.setVisibility(View.INVISIBLE);
                    textSemExercicio.setVisibility(View.VISIBLE);
                    imgSemExercicio.setVisibility(View.VISIBLE);
                } else {
                    rvExercicios.setVisibility(View.VISIBLE);
                    maisExercicio.setVisibility(View.VISIBLE);
                    textSemExercicio.setVisibility(View.INVISIBLE);
                    imgSemExercicio.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void configRecyclerView(){
        rvExercicios.setLayoutManager(new LinearLayoutManager(this));
        rvExercicios.setHasFixedSize(true);
        adapterExercicio = new AdapterExercicio(exercicioList);
        rvExercicios.setAdapter(adapterExercicio);
    }

    public void recuperaExames(){
        DatabaseReference exerciciosRef = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Exercicios");
        exerciciosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Exercicio exercicio = snap.getValue(Exercicio.class);
                        exercicioList.add(exercicio);
                    }
                    adapterExercicio.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setarData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataHoje = sdf.format(data);

        String dataCEmoji = "\uD83C\uDFCB️\u200D♀️ " + dataHoje;

        dataAtual.setText(dataCEmoji);
    }

    public void irTelaAddExercicio(View f){
        startActivity(new Intent(this, TelaExercicios_add.class));
    }

    public void voltarTela(View q){
        if (TelaLogin.premium) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaExercicios.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
        } else {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaExercicios.this, new Intent(this, TelaConteudos.class), activityOptionsCompat.toBundle());
        }
    }
}