package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class TelaPerfil extends AppCompatActivity {

    TextView nomeUsu, idadeUsu, telefoneUsu, emailUsu, pesoUsu, alturaUsu, biotipoUsu;
    Button btnVoltar, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        nomeUsu = findViewById(R.id.nomeUsuPerfil);
        idadeUsu = findViewById(R.id.idadePerfil);
        telefoneUsu = findViewById(R.id.telefonePerfil);
        emailUsu = findViewById(R.id.emailPerfil);
        pesoUsu = findViewById(R.id.pesoPerfil);
        alturaUsu = findViewById(R.id.alturaPerfil);
        biotipoUsu = findViewById(R.id.biotipoPerfil);
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

        setarInfoCadastro();
        setarInfoCadasComple();
    }

    public void setarInfoCadastro(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy"); //Formata a data
        Date data = new Date(); // Pega a data atual
        int dataAtual = parseInt(sdf.format(data));

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Informações de cadastro");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    String nomeCompleto = documentSnapshot.getString("Nome completo");
                    nomeUsu.setText(nomeCompleto);

//                    String dataNasc = documentSnapshot.getString("Data de nascimento");
//                    LocalDate dataNascOf = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
//                    LocalDate dataAtual = LocalDate.now();
//                    int dataN = (dataAtual.getYear() - dataNascOf.getYear());
//                    idadeUsu.setText(dataN);

                    String telefone = documentSnapshot.getString("Telefone");
                    String telefoneE = "\uD83D\uDCDE " + telefone;
                    telefoneUsu.setText(telefoneE);

                    String email = documentSnapshot.getString("Email");
                    String emailL = "\uD83D\uDCE7 " + email;
                    emailUsu.setText(emailL);
                }
            }
        });
    }

    public void setarInfoCadasComple(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Cadastro complementar");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    int peso = Math.toIntExact((Long) documentSnapshot.getData().get("Peso (kg)"));
                    pesoUsu.setText(peso + " quilos");

                    int altura = Math.toIntExact((Long) documentSnapshot.getData().get("Altura (cm)"));
                    alturaUsu.setText(altura + " cm");

                    String biotipo = documentSnapshot.getString("Biotipo corporal");
                    biotipoUsu.setText(biotipo);
                }
            }
        });
    }
}