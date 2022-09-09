package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAlimentacao_Cafedamanha extends AppCompatActivity {

    TextView dataAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao_cafedamanha);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarData();
    }

    private void iniciarComponentes(){
        dataAtual = findViewById(R.id.dataAtualcafedamanha);
    }

    public void setarData(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = new Date();
        String dataHoje = sdf.format(data);

        dataAtual.setText(dataHoje);
    }

    public void irTelaADAlimentacaoCafedamanha(View ladygaga){
        Intent irTelaADAlimentacaoCafedamanha = new Intent(TelaAlimentacao_Cafedamanha.this, TelaAlimentacao_adCafedamanha.class);
        startActivity(irTelaADAlimentacaoCafedamanha);
    }
}