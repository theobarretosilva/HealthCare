package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPeso_IMC extends AppCompatActivity {

    TextView btnHomem, btnMulher, btnMenos, btnMais, idade;

    int idadePessoa = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso_imc);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        btnHomem = findViewById(R.id.btnHomem);
        btnMulher = findViewById(R.id.btnMulher);
        btnMenos = findViewById(R.id.menos);
        btnMais = findViewById(R.id.mais);
        idade = findViewById(R.id.idade);

    }

    public void mudarSexoHomem(View h){
        btnHomem.setBackgroundResource(R.drawable.container_sexo2_imc);
        btnMulher.setBackgroundResource(R.drawable.container_sexo1_imc);
    }

    public void mudarSexoMulher(View m){
        btnHomem.setBackgroundResource(R.drawable.container_sexo1_imc);
        btnMulher.setBackgroundResource(R.drawable.container_sexo2_imc);
    }

    public void aumentarIdade(View a){
        idadePessoa++;

        idade.setText(idadePessoa);
    }

}