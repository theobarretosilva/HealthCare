package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAlimentacao2 extends AppCompatActivity {

    TextView dataAlimentacao2, tipoAlimentacao;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_alimentacao2);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarTipoAlimentacao();
    }

    private void iniciarComponentes(){
        dataAlimentacao2 = findViewById(R.id.dataAlimentacao2);
        dataAlimentacao2.setText(dataHoje);
        tipoAlimentacao = findViewById(R.id.tipoAlimentacao);
    }

    public void voltarTelaAlimentacao(View ir){
        Intent voltarTela = new Intent(this, TelaAlimentacao.class);
        startActivity(voltarTela);
    }

    public void setarTipoAlimentacao(){
        tipoAlimentacao.setText(TelaAlimentacao.tipoAlimentacao);
    }

    public void irTelaAddAlimentacao(View a){
        Intent irTelaAddAlimentacao = new Intent(this, TelaAlimentacao_add.class);
        startActivity(irTelaAddAlimentacao);
    }
}