package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
                pesoAtual.setText(peso+" kg");

                setarNaTelaIMCHI(res);

            }
        });

    }

    public void setarNaTelaIMCHI(float valor){
        DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Informações de cadastro");
        dr.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                String sexo = documentSnapshot.getString("Sexo");
                String dataNasc = documentSnapshot.getString("Data de nascimento");

                LocalDate dataNascFormatada = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataHoje = LocalDate.now();

                int idade = dataHoje.getYear() - dataNascFormatada.getYear();

                idade = 62;

                if (sexo.equals("Masculino") && idade >= 60){
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
        });
    }

    public void setarPeso(View s){
        if (editPeso.isChecked()){ // <- essa condição ta dando erro, tem que rever
            System.out.println("ta checkado");
        }
        String pesoTela = pesoAtual.getText().toString();
        DocumentReference dr = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Cadastro complementar");
        dr.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                String peso = documentSnapshot.getString("Peso (kg)");
                if (!peso.equals(pesoTela)){

                }
            }
        });
    }
}