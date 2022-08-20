package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class TelaPerfil extends AppCompatActivity {

    Button btnVoltar, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        btnVoltar = findViewById(R.id.btnVoltar);
        btnLogout = findViewById(R.id.btnLogout);

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaConteudos = new Intent(TelaPerfil.this, TelaConteudos.class);
                startActivity(voltarTelaConteudos);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent logout = new Intent(TelaPerfil.this, TelaInicial.class);
                startActivity(logout);
            }
        });
    }
}