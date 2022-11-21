package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TelaAlimentacao extends AppCompatActivity {

    TextView dataAtual;

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    Date data = new Date();
    String dataHoje = sdf.format(data);

    public static String tipoAlimentacao;

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
        if (TelaLogin.premium) {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaAlimentacao.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
        } else {
            ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
            ActivityCompat.startActivity(TelaAlimentacao.this, new Intent(this, TelaConteudos.class), activityOptionsCompat.toBundle());
        }
    }

    public void setarCafeDaManha(View c){
        tipoAlimentacao = "Café da manhã";

        Intent irTelaAlimentacao2 = new Intent(this, TelaAlimentacao2.class);
        startActivity(irTelaAlimentacao2);
    }

    public void setarAlmoco(View a){
        tipoAlimentacao = "Almoço";

        Intent irTelaAlimentacao2 = new Intent(this, TelaAlimentacao2.class);
        startActivity(irTelaAlimentacao2);
    }

    public void setarJantar(View j){
        tipoAlimentacao = "Jantar";

        Intent irTelaAlimentacao2 = new Intent(this, TelaAlimentacao2.class);
        startActivity(irTelaAlimentacao2);
    }

    public void setarLanches(View l){
        tipoAlimentacao = "Lanches";

        Intent irTelaAlimentacao2 = new Intent(this, TelaAlimentacao2.class);
        startActivity(irTelaAlimentacao2);
    }
}