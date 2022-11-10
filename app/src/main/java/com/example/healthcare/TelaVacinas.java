package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaVacinas extends AppCompatActivity {

    TextView olaNomeUsu, tituloVacinas, subtituloVacinas;
    CircleImageView fotoUsu;
    ArrayList<Vacinas> lsVacinas = new ArrayList<>();

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_vacinas);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarImagemPerfil();
    }

    @Override
    protected void onStart() {
        super.onStart();

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Informações de cadastro");
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (documentSnapshot != null){
                    String nomeUsu = documentSnapshot.getString("Primeiro nome");
                    String olaUsuCompleto = "Olá, " + nomeUsu + "!";
                    olaNomeUsu.setText(olaUsuCompleto);
                }
            }
        });
    }

    public void iniciarComponentes(){
        olaNomeUsu = findViewById(R.id.olaNomeUsu);
        fotoUsu = findViewById(R.id.fotoUsuVacinas);
        tituloVacinas = findViewById(R.id.tituloVacinas);
        subtituloVacinas = findViewById(R.id.subtituloVacinas);
    }

    public void irTelaPerfil(View f){
        Intent irTelaPerfil = new Intent(this, TelaPerfil.class);
        startActivity(irTelaPerfil);
    }

    public void setarImagemPerfil(){
        storageRef.child("imagens/Fotos de perfil/" + FirebaseHelper.getUIDUsuario() + "/" + FirebaseHelper.getUIDUsuario() + ".jpeg")
                .getDownloadUrl()
                .addOnSuccessListener(uri ->
                        Picasso.get().load(uri).into(fotoUsu)
                );
    }

    public void setaCrianca(View crianca){
        tituloVacinas.setText("Vacinas crianças");
        subtituloVacinas.setText("Para vacinar, basta levar a criança a um\nposto ou Unidade Básica de Saúde com o\ncartão de vacinação");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);

        Vacinas vacinas = new Vacinas("BCG (Bacilo Calmette-Guerin)", "Dose única");
        lsVacinas.add(vacinas);
        Vacinas vacinas1 = new Vacinas("Hepatite B", "Dose única");
        lsVacinas.add(vacinas1);
        Vacinas vacinas2 = new Vacinas("Pentavalente (DTP/HB/Hib)", "1°, 2° e 3° dose");
        lsVacinas.add(vacinas2);
        Vacinas vacinas3 = new Vacinas("VIP (Poliomielite inativada)", "1°, 2° e 3° dose");
        lsVacinas.add(vacinas3);
        Vacinas vacinas4 = new Vacinas("Pneumocócica 10V", "1° e 2°  dose");
        lsVacinas.add(vacinas4);
        Vacinas vacinas5 = new Vacinas("Vacina rotavírus humanos G1P1", "1° e 2°  dose");
        lsVacinas.add(vacinas5);
        Vacinas vacinas6 = new Vacinas("Meningocócica C conjugada", "1° e 2°  dose");
        lsVacinas.add(vacinas6);
        Vacinas vacinas7 = new Vacinas("Febre amarela", "Dose única");
        lsVacinas.add(vacinas7);
        Vacinas vacinas8 = new Vacinas("Pneumocócica 10 Valente", "Reforço");
        lsVacinas.add(vacinas8);
        Vacinas vacinas9 = new Vacinas("Tríplice Viral", "1° dose");
        lsVacinas.add(vacinas9);
        Vacinas vacinas10 = new Vacinas("Hepatite A", "Dose única");
        lsVacinas.add(vacinas10);
        Vacinas vacinas11 = new Vacinas("Poliomelite oral VOP", "1° e 2° reforço");
        lsVacinas.add(vacinas11);
        Vacinas vacinas12 = new Vacinas("DTP", "1° e 2° reforço");
        lsVacinas.add(vacinas12);
        Vacinas vacinas13 = new Vacinas("Vacirela atenuada", "");
        lsVacinas.add(vacinas13);


    }

    public void setaJovem(View jovem){
        tituloVacinas.setText("Vacinas jovens");
        subtituloVacinas.setText("A cardeneta de vacinação deve ser\nfrequentemente atualizada, pois algumas\ndelas só são administradas na adolescência.");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaAdulto(View adulto){
        tituloVacinas.setText("Vacinas adultos");
        subtituloVacinas.setText("A vacina também evita a transmissão para\noutras pessoas que não podem ser\nvacinadas.");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaGestante(View gestante){
        tituloVacinas.setText("Vacinas gestantes");
        subtituloVacinas.setText("A vacina para mulheres grávidas é essencial\npara previnir doenças para si e para o bebê");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaIdoso(View idoso){
        tituloVacinas.setText("Vacinas idosos");
        subtituloVacinas.setText("São três as vacinas disponíveis para pessoas\nacima de 60 anos, além da vacinação contra\ngripe.");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }
}