package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaConteudos extends AppCompatActivity {

    TextView olaUsu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conteudos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        olaUsu = findViewById(R.id.olaUsu);
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
                    String nomeUsu = documentSnapshot.getString("Nome");
                    String olaUsuCompleto = "Olá, " + nomeUsu + "!";
                    olaUsu.setText(olaUsuCompleto);
                }
            }
        });
    }

    public void irTelaAgua(View a){
        Intent irTelaAgua = new Intent(this, TelaAgua.class);
        startActivity(irTelaAgua);
    }

    public void irTelaAlimentacao(View l){
        Intent irTelaAlimentacao = new Intent(this, TelaAlimentacao.class);
        startActivity(irTelaAlimentacao);
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