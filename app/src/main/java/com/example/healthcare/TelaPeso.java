package com.example.healthcare;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TelaPeso extends AppCompatActivity {

    TextView magreza, normal,sobrepeso,obesidade;
    TextView resIMC;
    TextView imcMagreza, pesoMagreza;
    TextView imcNormal, pesoNormal;
    TextView imcSobrepeso, pesoSobrepeso;
    TextView imcObesidade, pesoObesidade;
    TextView fundoMagreza, fundoNormal, fundoSobrepeso, fundoObesidade;
    EditText pesoAtual;
    CheckBox editPeso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        calcularIMCAtual();
    }

    public void iniciarComponentes(){
        pesoAtual = findViewById(R.id.pesoAtual);
        resIMC = findViewById(R.id.resultadoImc);
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
        editPeso = findViewById(R.id.editPesoT);
    }

    public void voltarTelaConteudos(View v){
        if (TelaLogin.premium) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaPeso.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
        } else {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaPeso.this, new Intent(this, TelaConteudos.class), activityOptionsCompat.toBundle());
        }
    }

    public void irTelaPesoIMC(View m){
        Intent irTelaPesoIMC = new Intent(this, TelaPeso_IMC.class);
        startActivity(irTelaPesoIMC);
    }

    public void calcularIMCAtual(){
        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Cadastro complementar");
        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if(documentSnapshot != null){
                int peso = Math.toIntExact((Long) documentSnapshot.getData().get("Peso (kg)"));
                int altura = Math.toIntExact((Long) documentSnapshot.getData().get("Altura (cm)"));

                float alturaFinal = (altura/100)^2;
                float imc =(float) peso/alturaFinal;
                BigDecimal bd = new BigDecimal(imc);
                float res = bd.setScale(1, RoundingMode.FLOOR).floatValue();
                resIMC.setText(res+" kg/m²");
                pesoAtual.setText(peso + "");

                setarNaTelaIMC(res);
            }
        });

        DatabaseReference pesoReference = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Peso");
        pesoReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()){
                    Peso peso = snap.getValue(Peso.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setarNaTelaIMC(float valor){
        DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Informações de cadastro");
        dr.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                String dataNasc = documentSnapshot.getString("Data de nascimento");
                String sexo = documentSnapshot.getString("Sexo");

                LocalDate dataNascFormatada = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataHoje = LocalDate.now();

                int idade = dataHoje.getYear() - dataNascFormatada.getYear();

                if (idade >= 65){
                    imcMagreza.setText("< 20.0");
                    imcNormal.setText("20.0 a 30.0");
                    imcSobrepeso.setText("30.0 a 35.0");
                    imcObesidade.setText("> 35.0");
                    pesoMagreza.setText("< 54.5 Kg");
                    pesoNormal.setText("54.5 a 81.7 Kg");
                    pesoSobrepeso.setText("81.7 a 95.3 Kg");
                    pesoObesidade.setText("< 95.3 Kg");

                    if(valor < 20.0){
                        fundoMagreza.setVisibility(View.VISIBLE);
                        magreza.setTypeface(Typeface.DEFAULT_BOLD);
                        magreza.setTextColor(getResources().getColor(R.color.white));
                        imcMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                        imcMagreza.setTextColor(getResources().getColor(R.color.white));
                        pesoMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoMagreza.setTextColor(getResources().getColor(R.color.white));
                    }else if(valor>20.0 && valor<=30.0){
                        fundoNormal.setVisibility(View.VISIBLE);
                        normal.setTypeface(Typeface.DEFAULT_BOLD);
                        normal.setTextColor(getResources().getColor(R.color.white));
                        imcNormal.setTypeface(Typeface.DEFAULT_BOLD);
                        imcNormal.setTextColor(getResources().getColor(R.color.white));
                        pesoNormal.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoNormal.setTextColor(getResources().getColor(R.color.white));
                    }else if(valor>30.0  && valor<=35.0){
                        fundoSobrepeso.setVisibility(View.VISIBLE);
                        sobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                        sobrepeso.setTextColor(getResources().getColor(R.color.white));
                        imcSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                        imcSobrepeso.setTextColor(getResources().getColor(R.color.white));
                        pesoSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoSobrepeso.setTextColor(getResources().getColor(R.color.white));
                    }else if(valor>35.0){
                        fundoObesidade.setVisibility(View.VISIBLE);
                        obesidade.setTypeface(Typeface.DEFAULT_BOLD);
                        obesidade.setTextColor(getResources().getColor(R.color.white));
                        imcObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                        imcObesidade.setTextColor(getResources().getColor(R.color.white));
                        pesoObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoObesidade.setTextColor(getResources().getColor(R.color.white));
                    }
                } else if(idade < 65){
                    imcMagreza.setText("< 18.5");
                    imcNormal.setText("18.5 a 24.9");
                    imcSobrepeso.setText("24.9 a 30.0");
                    imcObesidade.setText("> 30.0");
                    pesoMagreza.setText("< 50.4 Kg");
                    pesoNormal.setText("50.4 a 67.8 Kg");
                    pesoSobrepeso.setText("67.8 a 81.7 Kg");
                    pesoObesidade.setText("> 81.7 Kg");

                    if (valor<18.5){
                        fundoMagreza.setVisibility(View.VISIBLE);
                        magreza.setTypeface(Typeface.DEFAULT_BOLD);
                        magreza.setTextColor(getResources().getColor(R.color.white));
                        imcMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                        imcMagreza.setTextColor(getResources().getColor(R.color.white));
                        pesoMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoMagreza.setTextColor(getResources().getColor(R.color.white));
                    } else if(valor>18.5 && valor<=24.9){
                        fundoNormal.setVisibility(View.VISIBLE);
                        normal.setTypeface(Typeface.DEFAULT_BOLD);
                        normal.setTextColor(getResources().getColor(R.color.white));
                        imcNormal.setTypeface(Typeface.DEFAULT_BOLD);
                        imcNormal.setTextColor(getResources().getColor(R.color.white));
                        pesoNormal.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoNormal.setTextColor(getResources().getColor(R.color.white));
                    } else if (valor>24.9 && valor<=30.0){
                        fundoSobrepeso.setVisibility(View.VISIBLE);
                        sobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                        sobrepeso.setTextColor(getResources().getColor(R.color.white));
                        imcSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                        imcSobrepeso.setTextColor(getResources().getColor(R.color.white));
                        pesoSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoSobrepeso.setTextColor(getResources().getColor(R.color.white));
                    } else if (valor>30.0){
                        fundoObesidade.setVisibility(View.VISIBLE);
                        obesidade.setTypeface(Typeface.DEFAULT_BOLD);
                        obesidade.setTextColor(getResources().getColor(R.color.white));
                        imcObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                        imcObesidade.setTextColor(getResources().getColor(R.color.white));
                        pesoObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                        pesoObesidade.setTextColor(getResources().getColor(R.color.white));
                    }
                } else if(idade <= 18){
                    if(sexo.equals("Masculino")){
                        imcMagreza.setText("< 17.3");
                        imcNormal.setText("17.3 a 25.5");
                        imcSobrepeso.setText("25.5 a 29.7");
                        imcObesidade.setText("> 29.7");
                        pesoMagreza.setText("< 47.1 Kg");
                        pesoNormal.setText("47.1 a 69.4 Kg");
                        pesoSobrepeso.setText("69.4 a 80.9 Kg");
                        pesoObesidade.setText("> 80.9 Kg");

                        if(valor<17.3){
                            fundoMagreza.setVisibility(View.VISIBLE);
                            magreza.setTypeface(Typeface.DEFAULT_BOLD);
                            magreza.setTextColor(getResources().getColor(R.color.white));
                            imcMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                            imcMagreza.setTextColor(getResources().getColor(R.color.white));
                            pesoMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoMagreza.setTextColor(getResources().getColor(R.color.white));
                        } else if (valor>17.3 && valor<=25.5){
                            fundoNormal.setVisibility(View.VISIBLE);
                            normal.setTypeface(Typeface.DEFAULT_BOLD);
                            normal.setTextColor(getResources().getColor(R.color.white));
                            imcNormal.setTypeface(Typeface.DEFAULT_BOLD);
                            imcNormal.setTextColor(getResources().getColor(R.color.white));
                            pesoNormal.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoNormal.setTextColor(getResources().getColor(R.color.white));
                        } else if (valor>25.5 && valor<=29.7){
                            fundoSobrepeso.setVisibility(View.VISIBLE);
                            sobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                            sobrepeso.setTextColor(getResources().getColor(R.color.white));
                            imcSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                            imcSobrepeso.setTextColor(getResources().getColor(R.color.white));
                            pesoSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoSobrepeso.setTextColor(getResources().getColor(R.color.white));
                        } else if (valor>29.7){
                            fundoObesidade.setVisibility(View.VISIBLE);
                            obesidade.setTypeface(Typeface.DEFAULT_BOLD);
                            obesidade.setTextColor(getResources().getColor(R.color.white));
                            imcObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                            imcObesidade.setTextColor(getResources().getColor(R.color.white));
                            pesoObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoObesidade.setTextColor(getResources().getColor(R.color.white));
                        }
                    } else {
                        imcMagreza.setText("< 16.4");
                        imcNormal.setText("16.4 a 25.1");
                        imcSobrepeso.setText("25.1 a 29.7");
                        imcObesidade.setText("> 29.7");
                        pesoMagreza.setText("< 44.6 Kg");
                        pesoNormal.setText("44.6 a 68.3 Kg");
                        pesoSobrepeso.setText("68.3 a 80.9 Kg");
                        pesoObesidade.setText("> 80.9 Kg");

                        if (valor<16.4){
                            fundoMagreza.setVisibility(View.VISIBLE);
                            magreza.setTypeface(Typeface.DEFAULT_BOLD);
                            magreza.setTextColor(getResources().getColor(R.color.white));
                            imcMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                            imcMagreza.setTextColor(getResources().getColor(R.color.white));
                            pesoMagreza.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoMagreza.setTextColor(getResources().getColor(R.color.white));
                        } else if(valor>16.4 && valor<=25.1){
                            fundoNormal.setVisibility(View.VISIBLE);
                            normal.setTypeface(Typeface.DEFAULT_BOLD);
                            normal.setTextColor(getResources().getColor(R.color.white));
                            imcNormal.setTypeface(Typeface.DEFAULT_BOLD);
                            imcNormal.setTextColor(getResources().getColor(R.color.white));
                            pesoNormal.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoNormal.setTextColor(getResources().getColor(R.color.white));
                        } else if (valor>25.1 && valor<=29.7){
                            fundoSobrepeso.setVisibility(View.VISIBLE);
                            sobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                            sobrepeso.setTextColor(getResources().getColor(R.color.white));
                            imcSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                            imcSobrepeso.setTextColor(getResources().getColor(R.color.white));
                            pesoSobrepeso.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoSobrepeso.setTextColor(getResources().getColor(R.color.white));
                        } else if(valor>29.7){
                            fundoObesidade.setVisibility(View.VISIBLE);
                            obesidade.setTypeface(Typeface.DEFAULT_BOLD);
                            obesidade.setTextColor(getResources().getColor(R.color.white));
                            imcObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                            imcObesidade.setTextColor(getResources().getColor(R.color.white));
                            pesoObesidade.setTypeface(Typeface.DEFAULT_BOLD);
                            pesoObesidade.setTextColor(getResources().getColor(R.color.white));
                        }
                    }
                }
            }
        });
    }

    public void setarPeso(View s){
        if (editPeso.isChecked()){
            pesoAtual.setEnabled(true);
            pesoAtual.requestFocus();
        } else {
            pesoAtual.setEnabled(false);
            int pesoTela = parseInt(pesoAtual.getText().toString());
            DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                    .collection("Usuarios")
                    .document(FirebaseHelper.getUIDUsuario())
                    .collection("Informações pessoais")
                    .document("Cadastro complementar");
            dr.addSnapshotListener((documentSnapshot, error) -> {
                if (documentSnapshot != null){
                    Long peso = documentSnapshot.getLong("Peso (kg)");
                    if (!(peso == pesoTela)){

                    }
                }
            });
        }

    }
}