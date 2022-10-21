package com.example.healthcare;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TelaAlimentacao_add extends AppCompatActivity {

    EditText nomeAlimento, kcalAlimento, gAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao_add);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        nomeAlimento = findViewById(R.id.nomeAlimento);
        kcalAlimento = findViewById(R.id.kcalAlimento);
        gAlimento = findViewById(R.id.gAlimento);
    }

    private void mandarAlimentoBD(View g){

    }
}