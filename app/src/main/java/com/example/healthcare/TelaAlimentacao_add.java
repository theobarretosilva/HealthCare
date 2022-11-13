package com.example.healthcare;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

    public void mandarAlimentoBD(View t){
        String alimento = nomeAlimento.getText().toString();
        String kcal = nomeAlimento.getText().toString();
        int g = parseInt(gAlimento.getText().toString());

        try {
            Alimentacao alimentacao = new Alimentacao();
            alimentacao.setAlimento(alimento);
            alimentacao.setKcal(kcal);
            alimentacao.setGramas(g);

            if (TelaAlimentacao.tipoAlimentacao.equals("Café da manhã")) {
                alimentacao.salvarCafeDaManha();
            }else if (TelaAlimentacao.tipoAlimentacao.equals("Almoço")) {
                alimentacao.salvarAlmoco();
            }else if (TelaAlimentacao.tipoAlimentacao.equals("Jantar")) {
                alimentacao.salvarJantar();
            }else if (TelaAlimentacao.tipoAlimentacao.equals("Lanches")) {
                alimentacao.salvarLanches();
            }

            Toast.makeText(this, "Sucesso ao adicionar a refeição!", Toast.LENGTH_SHORT).show();
            Intent irTelaAlimentacao2 = new Intent(this, TelaAlimentacao2.class);
            startActivity(irTelaAlimentacao2);
        }catch (Exception e){
            Toast.makeText(this, "Não foi possível adicionar o exame!", Toast.LENGTH_SHORT).show();
        }
    }
}