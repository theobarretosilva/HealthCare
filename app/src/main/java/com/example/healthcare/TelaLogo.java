package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class TelaLogo extends AppCompatActivity implements Runnable{

    int teste = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_logo);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(this, 1400);
    }

    @Override
    public void run() {
//        if (FirebaseHelper.getFirebaseUser().getUid() != null) {
//            DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
//                    .collection("Usuarios")
//                    .document(FirebaseHelper.getUIDUsuario())
//                    .collection("Informações pessoais")
//                    .document("Informações de cadastro");
//
//            documentReference.addSnapshotListener((documentSnapshot, error) -> {
//                if (documentSnapshot.exists()){
//                    Boolean teste = documentSnapshot.getBoolean("Premium");
//                    System.out.println(teste);
//                    System.out.println(!teste);
//                    if (teste){
//                        Intent iP = new Intent(TelaLogo.this, TelaConteudos_Premium.class);
//                        startActivity(iP);
//                        System.out.println("Calma que entrou");
//                    } else if(!teste){
//                        Intent i = new Intent(TelaLogo.this, TelaConteudos.class);
//                        startActivity(i);
//                        System.out.println("Calmaria");
//                    }
//                } else {
//                    startActivity(new Intent(TelaLogo.this, TelaInicial.class));
//                }
//            });
//        }
        startActivity(new Intent(TelaLogo.this, TelaInicial.class));
    }
}