package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

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

            switch (TelaAlimentacao.tipoAlimentacao){
                case "Café da manhã": alimentacao.salvarCafeDaManha();
                break;

                case "Almoço": alimentacao.salvarAlmoco();
                break;

                case "Jantar": alimentacao.salvarJantar();
                break;

                case "Lanches": alimentacao.salvarLanches();
                break;
            }

            Toast.makeText(this, "Sucesso ao adicionar a refeição!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TelaAlimentacao_add.this, TelaAlimentacao2.class));
        }catch (Exception e){
            Toast.makeText(this, "Não foi possível adicionar a refeição!", Toast.LENGTH_SHORT).show();
        }
    }

    public void voltarTelaAlimentacao2(View a){
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(TelaAlimentacao_add.this, new Intent(TelaAlimentacao_add.this, TelaAlimentacao2.class), activityOptionsCompat.toBundle());
    }
}