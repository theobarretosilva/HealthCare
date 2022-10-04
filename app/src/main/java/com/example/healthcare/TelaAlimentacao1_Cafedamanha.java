package com.example.healthcare;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAlimentacao1_Cafedamanha extends AppCompatActivity {

    TextView data;

    Date dataHoje = new Date();
    SimpleDateFormat sdfBarra = new SimpleDateFormat("dd/MM/yyyy");
    String dataHojeBarra = sdfBarra.format(dataHoje);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao1_cafedamanha);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciaComponentes();
        setaDataAtual();
    }

    public void iniciaComponentes(){
        data = findViewById(R.id.dataAlimentacao1CafeManha);
    }

    public void setaDataAtual(){
        data.setText(dataHojeBarra);
    }
}