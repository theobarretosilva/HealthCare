package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_medicamentos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        rvMedicamentos = findViewById(R.id.rvMedicamentos);

        configRecyclerView();
        recuperaMedicamentos();
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
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    public void mandarNotificacao(){
//        String horario = new Date().getHours() + ":" + new Date().getMinutes();
//        DatabaseReference medicamentosRef = FirebaseHelper.getDatabaseReference()
//                .child("Registros")
//                .child(FirebaseHelper.getUIDUsuario())
//                .child("Medicamentos");
//        medicamentosRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//                    for (DataSnapshot snap : snapshot.getChildren()){
//                        Medicamento medicamento = snap.getValue(Medicamento.class);
//                        if (medicamento.getHorarioMedicamento() == horario){
//                            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, )
//                                    .setSmallIcon(R.drawable.logo2_saude)
//                                    .setContentTitle(medicamento.getNomeMedicamento())
//                                    .setContentText("Não esqueça de tomar o " + medicamento.getNomeMedicamento())
//                                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}