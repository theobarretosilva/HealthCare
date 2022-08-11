package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class TelaInicial extends AppCompatActivity {

    Button login, cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        Intent irDireto = new Intent (this, TelaAlimentacao.class);
        startActivity(irDireto);

        login = findViewById(R.id.login);
        cadastrar = findViewById(R.id.cadastrar);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irTelaLogin();
            }
        });
        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                irTelaCadastro();
            }
        });
    }

    public void irTelaLogin(){
        Intent irTelaLogin = new Intent(this, TelaLogin.class);
        startActivity(irTelaLogin);
    }
    public void irTelaCadastro(){
        Intent irTelaCadastro = new Intent(this, TelaCadastro.class);
        startActivity(irTelaCadastro);
    }
}