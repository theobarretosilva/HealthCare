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

import java.util.ArrayList;
import java.util.List;

public class TelaMedicamentos extends AppCompatActivity {

    private AdapterMedicamento adapterMedicamento;
    private List<Medicamento> medicamentoList = new ArrayList<>();
    private RecyclerView rvMedicamentos;

    ImageView maisMedicamento, addMedicamento;
    TextView nenhumMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_medicamentos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        configRecyclerView();
        recuperaMedicamentos();
    }

    public void iniciarComponentes(){
        rvMedicamentos = findViewById(R.id.rvMedicamentos);
        maisMedicamento = findViewById(R.id.maisMedicamento);
        addMedicamento = findViewById(R.id.addMedicamento);
        nenhumMedicamento = findViewById(R.id.nenhumMedicamento);
    }

    public void voltarTela(View h){
        if (TelaLogin.premium) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaMedicamentos.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
        } else {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaMedicamentos.this, new Intent(this, TelaConteudos.class), activityOptionsCompat.toBundle());
        }
    }

    public void irAddMedicamentos(View r){
        startActivity(new Intent(TelaMedicamentos.this, TelaMedicamentos_add.class));
    }

    private void configRecyclerView(){
        rvMedicamentos.setLayoutManager(new LinearLayoutManager(this));
        rvMedicamentos.setHasFixedSize(true);
        adapterMedicamento = new AdapterMedicamento(medicamentoList);
        rvMedicamentos.setAdapter(adapterMedicamento);
    }

    public void recuperaMedicamentos(){
        DatabaseReference medicamentosRef = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Medicamentos");
        medicamentosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Medicamento medicamento = snap.getValue(Medicamento.class);
                        medicamentoList.add(medicamento);
                    }
                    adapterMedicamento.notifyDataSetChanged();
                } else {
                    rvMedicamentos.setVisibility(View.INVISIBLE);
                    maisMedicamento.setVisibility(View.INVISIBLE);
                    addMedicamento.setVisibility(View.VISIBLE);
                    nenhumMedicamento.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}