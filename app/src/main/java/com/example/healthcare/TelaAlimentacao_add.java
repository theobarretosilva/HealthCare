package com.example.healthcare;

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

    public void verificarPreenchimento(View t){
        if (nomeAlimento.length() == 0){
            nomeAlimento.setError("Preencha este campo");
        } else if(kcalAlimento.length() == 0){
            kcalAlimento.setError("Preencha este campo");
        } else if(gAlimento.length() == 0){
            gAlimento.setError("Preencha este campo");
        } else {
            mandarAlimentoBD();
        }
    }

    public void mandarAlimentoBD(){
        String alimento = nomeAlimento.getText().toString();
        int kcal = Integer.parseInt(kcalAlimento.getText().toString());
        int g = Integer.parseInt(gAlimento.getText().toString());

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
            Toast.makeText(this, "Não foi possível adicionar a refeição!", Toast.LENGTH_SHORT).show();
        }
    }
}