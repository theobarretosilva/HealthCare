package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaConteudos_Premium extends AppCompatActivity {

    TextView olaUsu_Premium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conteudos__premium);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        olaUsu_Premium = findViewById(R.id.olaUsu_Premium);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Informações de cadastro");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    String primeiroN = documentSnapshot.getString("Primeiro nome");
                    String olaUsuCompleto = "Olá " + primeiroN + "! ⭐";
                    olaUsu_Premium.setText(olaUsuCompleto);
                }
            }
        });
    }

    public void irTelaPerfil_Premium(View t){
        Intent irTelaPerfil_Premium = new Intent (this, TelaPerfil_Premium.class);
        startActivity(irTelaPerfil_Premium);
    }

    public void irTelaAgua(View a){
        Intent irTelaAgua = new Intent(this, TelaAgua.class);
        startActivity(irTelaAgua);
    }

    public void irTelaAlimentacao(View l){
        Intent irTelaAlimentacao = new Intent(this, TelaAlimentacao.class);
        startActivity(irTelaAlimentacao);
    }

    public void irTelaPassos(View o){
        Intent irTelaPassos = new Intent(this, TelaPassos.class);
        startActivity(irTelaPassos);
    }

    public void irTelaSono(View s){
        Intent irTelaSono = new Intent(this, TelaSono.class);
        startActivity(irTelaSono);
    }

    public void irTelaMedicamentos(View m){
        Intent irTelaMedicamentos = new Intent(this, TelaMedicamentos.class);
        startActivity(irTelaMedicamentos);
    }

    public void irTelaExercicios(View e){
        Intent irTelaExercicios = new Intent(this, TelaExercicios.class);
        startActivity(irTelaExercicios);
    }

    public void irTelaPeso(View p){
        Intent irTelaPeso = new Intent(this, TelaPeso.class);
        startActivity(irTelaPeso);
    }

    public void irTelaVacinas(View v){
        Intent irTelaVacinas = new Intent(this, TelaVacinas.class);
        startActivity(irTelaVacinas);
    }


}