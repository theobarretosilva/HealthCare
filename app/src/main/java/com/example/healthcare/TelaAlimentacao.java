package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAlimentacao extends AppCompatActivity {

    TextView dataAtual;

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
    }

    @Override
    protected void onStart() {
        super.onStart();

        dataAtual.setText("\uD83D\uDE0B  " + dataHoje);
    }

    public void voltarTelaConteudos(View m){
        Intent irTelaConteudos = new Intent(this, TelaConteudos.class);
        startActivity(irTelaConteudos);
    }

    public void irCafeDaManha(View g){
        Intent irCafeDaManha = new Intent(this, TelaAlimentacao_adCafedamanha.class);
        startActivity(irCafeDaManha);
    }
}