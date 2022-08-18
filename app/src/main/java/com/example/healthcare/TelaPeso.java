package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TelaPeso extends AppCompatActivity {

    TextView pesoAtual;
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        pesoAtual = findViewById(R.id.pesoAtual);
    }

    public void voltarTelaConteudos(View v){
        Intent irTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(irTelaConteudos);
    }

    public void irTelaPesoIMC(View m){
        Intent irTelaPesoIMC = new Intent(this, TelaPeso_IMC.class);
        startActivity(irTelaPesoIMC);
    }

    public void pesoAtual(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuarioUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public void calcularIMCAtual(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Cadastro complementar");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

            }
        });

    }
}