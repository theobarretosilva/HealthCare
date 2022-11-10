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
    ArrayList<Vacina> lVacinas= new ArrayList<>();

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
        Vacina vacinas = new Vacina("Vacinas Crianças",
                "Para vacinar, basta levar a criança a um\nposto ou Unidade Básica de Saúde com o\ncartão de vacinação"
        );
        lVacinas.add(vacinas);

        for (Vacina c : lVacinas){
            tituloVacinas.setText(c.getTitulo());
            subtituloVacinas.setText(c.getSubTitulo());
        }

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaJovem(View jovem){
        Vacina vacinas = new Vacina("Vacinas Jovens", "A cardeneta de vacinação deve ser\nfrequentemente atualizada, pois algumas\ndelas só são administradas na adolescência.");
        lVacinas.add(vacinas);

        for (Vacina c : lVacinas){
            tituloVacinas.setText(c.getTitulo());
            subtituloVacinas.setText(c.getSubTitulo());
        }

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaAdulto(View adulto){
        Vacina vacinas = new Vacina("Vacinas Adultos", "A vacina também evita a transmissão para\noutras pessoas que não podem ser\nvacinadas.");
        lVacinas.add(vacinas);

        for (Vacina c : lVacinas){
            tituloVacinas.setText(c.getTitulo());
            subtituloVacinas.setText(c.getSubTitulo());
        }

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaGestante(View gestante){
        Vacina vacinas = new Vacina("Vacinas Gestante", "A vacina para mulheres grávidas é essencial\npara previnir doenças para si e para o bebê");
        lVacinas.add(vacinas);

        for (Vacina c : lVacinas){
            tituloVacinas.setText(c.getTitulo());
            subtituloVacinas.setText(c.getSubTitulo());
        }

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }

    public void setaIdoso(View idoso){
        Vacina vacinas = new Vacina("Vacinas Idoso", "São três as vacinas disponíveis para pessoas\nacima de 60 anos, além da vacinação contra\ngripe.");
        lVacinas.add(vacinas);

        for (Vacina c : lVacinas){
            tituloVacinas.setText(c.getTitulo());
            subtituloVacinas.setText(c.getSubTitulo());
        }

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);
    }
}