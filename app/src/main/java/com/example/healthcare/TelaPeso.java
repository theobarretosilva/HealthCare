package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TelaPeso extends AppCompatActivity {

    TextView pesoAtual, resIMC;
    TextView imcMagreza, pesoMagreza;
    TextView imcNormal, pesoNormal;
    TextView imcSobrepeso, pesoSobrepeso;
    TextView imcObesidade, pesoObesidade;
    String usuarioID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        pesoAtual = findViewById(R.id.pesoAtual);
        resIMC = findViewById(R.id.resIMC);
        imcMagreza = findViewById(R.id.imcMagreza);
        pesoMagreza = findViewById(R.id.pesoMagreza);
        imcNormal = findViewById(R.id.imcNormal);
        pesoNormal = findViewById(R.id.pesoNormal);
        imcSobrepeso = findViewById(R.id.imcSobrepeso);
        pesoSobrepeso = findViewById(R.id.pesoSobrepeso);
        imcObesidade = findViewById(R.id.imcObesidade);
        pesoObesidade = findViewById(R.id.pesoObesidade);

        calcularIMCAtual();
    }

    public void voltarTelaConteudos(View v){
        Intent irTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(irTelaConteudos);
    }

    public void irTelaPesoIMC(View m){
        Intent irTelaPesoIMC = new Intent(this, TelaPeso_IMC.class);
        startActivity(irTelaPesoIMC);
    }

//    public void pesoAtual(){
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//        usuarioUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//    }

    public void calcularIMCAtual(){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Cadastro complementar");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    int peso = Math.toIntExact((Long) documentSnapshot.getData().get("Peso (kg)"));
                    int altura = Math.toIntExact((Long) documentSnapshot.getData().get("Altura (cm)"));

                    float alturaFinal = (altura/100)^2;
                    float imc =(float) peso/alturaFinal;
                    BigDecimal bd = new BigDecimal(imc);
                    float res = bd.setScale(1, RoundingMode.FLOOR).floatValue();
                    resIMC.setText(res+" kg/m²");
                    pesoAtual.setText(peso+" kg");

//                    Toast.makeText(TelaPeso.this, "Seu resultado: "+res, Toast.LENGTH_SHORT).show();

                    setarNaTelaIMCHI(res);

                }
            }
        });

    }

    public void setarNaTelaIMCHI(float valor){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String usuarioUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference dr = db.collection("Usuarios").document(usuarioUID).collection("Informações pessoais").document("Informações de cadastro");
        dr.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    String sexo = documentSnapshot.getString("Sexo");
                    String dataNasc = documentSnapshot.getString("Data de nascimento");

                    LocalDate dataNascFormatada = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate dataHoje = LocalDate.now();

                    int idade = dataHoje.getYear() - dataNascFormatada.getYear();
                    String idadeString = String.valueOf(idade);

                    if (sexo == "Masculino" && idade >= 60){
                        imcMagreza.setText("<21.9");
                        imcNormal.setText("22.0 a 27.0");
                        imcSobrepeso.setText("27.1 a 30.0");
                        imcObesidade.setText(">30.1");
                        if(valor < 21.9){

                        }else if(valor>22.0  && valor<=27.0 ){

                        }else if(valor>27.1  && valor<=30.0){

                        }else if(valor>30.1){

                        }
                    }
                }
            }
        });
    }
}