package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TelaPeso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();
    }

    public void voltarTelaConteudos(View v){
        Intent irTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(irTelaConteudos);
    }

    public void irTelaPesoIMC(View m){
        Intent irTelaPesoIMC = new Intent(this, TelaPeso_IMC.class);
        startActivity(irTelaPesoIMC);
    }
}