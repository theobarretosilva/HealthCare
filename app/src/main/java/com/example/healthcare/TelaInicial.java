package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class TelaInicial extends AppCompatActivity {

    Button login, cadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

//        Intent irDireto = new Intent (this, TelaExames_ad.class);
//        startActivity(irDireto);

        login = findViewById(R.id.login);
        cadastrar = findViewById(R.id.cadastrar);

        login.setOnClickListener(v -> irTelaLogin());
        cadastrar.setOnClickListener(v -> irTelaCadastro());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference documentReference = db.collection("Usuarios").document(FirebaseHelper.getUIDUsuario()).collection("Informações pessoais").document("Informações de cadastro");
            documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                    Boolean premium = documentSnapshot.getBoolean("Premium");
                    if (premium) {
                        Intent iPremium = new Intent(TelaInicial.this, TelaConteudos_Premium.class);
                        startActivity(iPremium);
                    } else {
                        Intent i = new Intent(TelaInicial.this, TelaConteudos.class);
                        startActivity(i);
                    }
                }
            });
        }
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