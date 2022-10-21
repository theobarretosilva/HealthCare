package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TelaAlimentacao extends AppCompatActivity {

    TextView dataAtual;
    static ArrayList<Alimentacao> lAlimentacao = new ArrayList<>();

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        dataAtual = findViewById(R.id.dataAtual);
        dataAtual.setText("\uD83D\uDE0B  " + dataHoje);
    }

    public void voltarTelaConteudos(View m){
        Intent irTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(irTelaConteudos);
    }

    public void setarCafeDaManha(View c){
        Alimentacao alimentacao = new Alimentacao("Café da manhã");
        lAlimentacao.add(alimentacao);

        Intent irTelaAlimentacao2 = new Intent(this, TelaAlimentacao2.class);
        startActivity(irTelaAlimentacao2);
    }
}