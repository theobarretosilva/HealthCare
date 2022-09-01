package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TelaPeso_IMC extends AppCompatActivity {

    TextView btnHomem, btnMulher, btnMenos, btnMais, idade;
    TextView altura, peso;
    String genero;
    Button calcular;

    Integer idadePessoa;

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
        // Acho que está tendo um problema com o tipo CharSequence
        int alturaImc = Integer.parseInt(altura.getText().toString());
        int pesoImc=   Integer.parseInt(peso.getText().toString());

        float alturaFinal = (alturaImc/100)^2;
        System.out.println("altura"+alturaFinal);
        System.out.println("altura");
        float imc =(float) pesoImc/alturaFinal;
        System.out.println(imc);

        if(idadePessoa> 1 && idadePessoa<18){
//            Magreza: <17,3 (< 47.1 Kg)
//            Normal: 17.3 a 25.5 (47.1 a 69.4 Kg)
//            Sobrepeso: 25.5 a 29.7  (69.4 a 80.9 Kg)
//            Obesidade: > 29.7 (> 80.9 Kg)

            if(imc < 17.3){
                String magreza = "";
            }else if(imc>17.3  && imc<25.5 ){
                String normal = "";
            }else if(imc>25.5  && imc<60){
                String sobrepeso = "";
            }else if(imc>29.7){
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
    }
}