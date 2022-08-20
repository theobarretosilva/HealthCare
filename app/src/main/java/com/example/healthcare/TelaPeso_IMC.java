package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TelaPeso_IMC extends AppCompatActivity {

    TextView btnHomem, btnMulher, btnMenos, btnMais, idade;
    Button btn;

    Integer idadePessoa;

    private void atualizaContador() {
        idade = findViewById(R.id.idade);
        idade.setText(idadePessoa.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_peso_imc);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        btnHomem = findViewById(R.id.btnHomem);
        btnMulher = findViewById(R.id.btnMulher);
        btnMenos = findViewById(R.id.btnMenos);
        btnMais = findViewById(R.id.btnMais);

        idadePessoa = 0;

        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idadePessoa -= 1;
                atualizaContador();
            }
        });
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                idadePessoa += 1;
                atualizaContador();

            }
        });

    }

    public void mudarSexoHomem(View h){
        btnHomem.setBackgroundResource(R.drawable.container_sexo2_imc);
        btnMulher.setBackgroundResource(R.drawable.container_sexo1_imc);
    }

    public void mudarSexoMulher(View m){
        btnHomem.setBackgroundResource(R.drawable.container_sexo1_imc);
        btnMulher.setBackgroundResource(R.drawable.container_sexo2_imc);
    }

}