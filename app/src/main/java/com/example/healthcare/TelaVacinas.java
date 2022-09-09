package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaVacinas extends AppCompatActivity {

    TextView olaNomeUsu;
    CircleImageView fotoUsu;

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
    }

    public void irTelaPerfil(View f){
        Intent irTelaPerfil = new Intent(this, TelaPerfil.class);
        startActivity(irTelaPerfil);
    }

    public void setarImagemPerfil(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        storageRef.child("imagens/Fotos de perfil/" + usuarioID + "/" + usuarioID + ".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(fotoUsu);
            }
        });
    }
}