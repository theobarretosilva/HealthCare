package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
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

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TelaPeso extends AppCompatActivity {

    TextView magreza, normal,sobrepeso,obesidade;
    TextView pesoAtual, resIMC;
    TextView imcMagreza, pesoMagreza;
    TextView imcNormal, pesoNormal;
    TextView imcSobrepeso, pesoSobrepeso;
    TextView imcObesidade, pesoObesidade;
    TextView fundoMagreza, fundoNormal, fundoSobrepeso, fundoObesidade;
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
        fundoMagreza = findViewById(R.id.fundoMagreza);
        fundoNormal = findViewById(R.id.fundoNormal);
        fundoSobrepeso = findViewById(R.id.fundoSobrepeso);
        fundoObesidade = findViewById(R.id.fundoObesidade);
        magreza = findViewById(R.id.magreza);
        normal = findViewById(R.id.normal);
        sobrepeso = findViewById(R.id.sobrepeso);
        obesidade = findViewById(R.id.obesidade);

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
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference dr = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Informações de cadastro");
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

                    String calma = "vai";
                    String calma2 = "vai";

                    String sexoMasculino = "Masculino";

                    idade = 62;

                    if (sexo.equals(sexoMasculino) && idade >= 60){
                        imcMagreza.setText("<21.9");
                        imcNormal.setText("22.0 a 27.0");
                        imcSobrepeso.setText("27.1 a 30.0");
                        imcObesidade.setText(">30.1");


                            fundoMagreza.setVisibility(View.VISIBLE);
                            fundoNormal.setVisibility(View.INVISIBLE);
                            magreza.setTypeface(Typeface.DEFAULT_BOLD);
                            imcMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                            magreza.setTextColor(getResources().getColor(R.color.white));
                            imcMagreza.setTextColor(getResources().getColor(R.color.white));
                            pesoMagreza.setTextColor(getResources().getColor(R.color.white));
                            normal.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                            normal.setTextColor(getResources().getColor(R.color.azul_escuro));
                            imcNormal.setTypeface(imcNormal.getTypeface(), Typeface.NORMAL);
                            imcNormal.setTextColor(getResources().getColor(R.color.azul_escuro));
                            pesoNormal.setTypeface(pesoNormal.getTypeface(), Typeface.NORMAL);
                            pesoNormal.setTextColor(getResources().getColor(R.color.azul_escuro));
                        if(valor>22.0  && valor<=27.0 ){

                        }else if(valor>27.1  && valor<=30.0){

                        }else if(valor>30.1){

                        }
                    }
                }
            }
        });
    }
}