package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class TelaPeso_IMC extends AppCompatActivity {

    TextView btnHomem, btnMulher, btnMenos, btnMais, idade;
    TextView altura, peso;
    String genero;
    Button calcular;

    Integer idadePessoa;

    Dialog dialog;

    private void atualizaContador() {
        idade = findViewById(R.id.idade);
        idade.setText(idadePessoa.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso_imc);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        btnHomem = findViewById(R.id.btnHomem);
        btnMulher = findViewById(R.id.btnMulher);
        btnMenos = findViewById(R.id.btnMenos);
        btnMais = findViewById(R.id.btnMais);
        altura = findViewById(R.id.alturaImc);
        peso = findViewById(R.id.pesoImc);

        dialog = new Dialog(this);

        idadePessoa = 0;
        calcular = findViewById(R.id.calcular);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculaImc();
            }
        });

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(idadePessoa != 0){
                    idadePessoa -= 1;
                    atualizaContador();
                }
            }
        });
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idadePessoa += 1;
                atualizaContador();

            }
        });

    }

    public void mudarSexoHomem(View h){
        btnHomem.setBackgroundResource(R.drawable.container_sexo2_imc);
        btnMulher.setBackgroundResource(R.drawable.container_sexo1_imc);
        genero = "masculino";
    }

    public void mudarSexoMulher(View m){
        btnHomem.setBackgroundResource(R.drawable.container_sexo1_imc);
        btnMulher.setBackgroundResource(R.drawable.container_sexo2_imc);
        genero = "feminino";
    }

    public void calculaImc(){
        int alturaImc = Integer.parseInt(altura.getText().toString());
        int pesoImc=   Integer.parseInt(peso.getText().toString());

        float alturaFinal = (alturaImc/100)^2;
        System.out.println("altura"+alturaFinal);
        System.out.println("altura");
        float imc =(float) pesoImc/alturaFinal;


        BigDecimal bd = new BigDecimal(imc);
        float res = bd.setScale(1, RoundingMode.FLOOR).floatValue();
        System.out.println(imc);
        Toast.makeText(this, "Seu resultado: "+res, Toast.LENGTH_SHORT).show();

        if(idadePessoa> 1 && idadePessoa<18){
//            Magreza: <17,3 (< 47.1 Kg)
//            Normal: 17.3 a 25.5 (47.1 a 69.4 Kg)
//            Sobrepeso: 25.5 a 29.7  (69.4 a 80.9 Kg)
//            Obesidade: > 29.7 (> 80.9 Kg)

            if(res < 17.3){
                String magreza = "";
            }else if(res>17.3  && res<25.5 ){
                String normal = "";
            }else if(res>25.5  && res<60){
                String sobrepeso = "";
            }else if(res>29.7){
                String obesidade = "";
            }else{
                Toast.makeText(this, "Reveja suas informações!", Toast.LENGTH_SHORT).show();
            }

        }

        if(idadePessoa> 18 && idadePessoa<60){
            String magreza = "";
            String normal = "";
            String sobrepeso = "";
            String obesidade = "";
        }

        if(idadePessoa> 60 && idadePessoa<101 &&  genero.equals("feminino")){

        }
        if(idadePessoa> 60 && idadePessoa<101 &&  genero.equals("masculino")){

        }

        openModalImc(res);
    }

    public void openModalImc(float valor){
        dialog.setContentView(R.layout.imc_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        TextView resIMC = dialog.findViewById(R.id.resultadoImc);
        TextView tipo = dialog.findViewById(R.id.tipoIMC);
        Button btnClose = dialog.findViewById(R.id.btnOK);

        resIMC.setText(""+valor);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Toast.makeText(TelaPeso_IMC.this, "Fechando", Toast.LENGTH_SHORT).show();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        resIMC.setText(""+valor);

        if(idadePessoa> 1 && idadePessoa<18){
            if(valor < 17.3){
               tipo.setText("Magreza");
            }else if(valor>17.3  && valor<25.5 ){
                tipo.setText("Normal");
            }else if(valor>25.5  && valor<60){
                tipo.setText("Sobrepeso");
            }else if(valor>29.7){
                tipo.setText("Obesidade");
            }else{
                Toast.makeText(this, "Reveja suas informações!", Toast.LENGTH_SHORT).show();
            }

        }

        if(idadePessoa> 18 && idadePessoa<60){
            String magreza = "";
            String normal = "";
            String sobrepeso = "";
            String obesidade = "";
        }

        if(idadePessoa> 60 && idadePessoa<101 &&  genero.equals("feminino")){

        }
        if(idadePessoa> 60 && idadePessoa<101 &&  genero.equals("masculino")){

        }

        dialog.show();
    }

    public  void retornaTela(View v){
        Intent i = new Intent(this, TelaPeso.class);
        startActivity(i);
    }
}