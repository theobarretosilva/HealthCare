package com.example.healthcare;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaConteudos extends AppCompatActivity {

    BottomSheetDialog dialog;
    Dialog dialogMenu;

    TextView olaUsu, examesBox, clinicasBox;
    CircleImageView fotoUsu;
    ImageView examesLogo, clinicasLogo, cadeado, menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conteudos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarImagemPerfil();

        menu.setOnClickListener(v -> {
            dialogMenu.show();
            openMenuModal();
        });

        dialog = new BottomSheetDialog(this);
        mostrarCardPremium();
        dialogMenu = new Dialog(this);

        examesBox.setOnClickListener(view -> dialog.show());
        examesLogo.setOnClickListener(view -> dialog.show());
        clinicasBox.setOnClickListener(v -> dialog.show());
        clinicasLogo.setOnClickListener(v -> dialog.show());
    }

    @Override
    protected void onStart() {
        super.onStart();

        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Informações de cadastro");

        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                String primeiroN = documentSnapshot.getString("Primeiro nome");
                String olaUsuCompleto = "Olá " + primeiroN + "!";
                olaUsu.setText(olaUsuCompleto);
            }
        });
    }

    public void iniciarComponentes(){
        olaUsu = findViewById(R.id.olaUsu);
        fotoUsu = findViewById(R.id.fotoUsu);
        examesBox = findViewById(R.id.examesBox);
        examesLogo = findViewById(R.id.examesLogo);
        clinicasBox = findViewById(R.id.clinicasBox);
        clinicasLogo = findViewById(R.id.clinicasLogo);
        cadeado = findViewById(R.id.cadeado);
        menu = findViewById(R.id.menu);
    }

    public void irTelaPerfil(View t){
        Intent irTelaPerfil = new Intent (this, TelaPerfil.class);
        startActivity(irTelaPerfil);
    }

    public void irTelaAgua(View a){
        Intent irTelaAgua = new Intent(this, TelaAgua.class);
        startActivity(irTelaAgua);
    }

    public void irTelaAlimentacao(View l){
        Intent irTelaAlimentacao = new Intent(this, TelaAlimentacao.class);
        startActivity(irTelaAlimentacao);
    }

    public void irTelaPeso(View p){
        Intent irTelaPeso = new Intent(this, TelaPeso.class);
        startActivity(irTelaPeso);
    }

    public void irTelaVacinas(View v){
        Intent irTelaVacinas = new Intent(this, TelaVacinas.class);
        startActivity(irTelaVacinas);
    }

    public void irTelaSono(View s){
        Intent irTelaSono = new Intent(this, TelaSono.class);
        startActivity(irTelaSono);
    }

    public void irTelaPassos(View o){
        Intent irTelaPassos = new Intent(this, TelaPassos.class);
        startActivity(irTelaPassos);
    }

    public void irTelaExercicios(View e){
        Intent irTelaExercicios = new Intent(this, TelaExercicios.class);
        startActivity(irTelaExercicios);
    }

    public void irTelaMedicamentos(View m){
        Intent irTelaMedicamentos = new Intent(this, TelaMedicamentos.class);
        startActivity(irTelaMedicamentos);
    }

    public void irTelaPremium(View g){
        Intent irTelaPremium = new Intent(this, TelaPremium.class);
        startActivity(irTelaPremium);
    }

    public void mostrarCardPremium(){
        View view = getLayoutInflater().inflate(R.layout.card_premium, null, false);

        Button irTelaPremium = view.findViewById(R.id.saberMais);
        irTelaPremium.setOnClickListener(view1 -> irTelaPremium(view1));

        dialog.setContentView(view);
    }

    public void setarImagemPerfil(){

        FirebaseHelper.getStorageReference()
                .child("imagens/Fotos de perfil/" + FirebaseHelper.getUIDUsuario() + "/" + FirebaseHelper.getUIDUsuario() + ".jpeg")
                .getDownloadUrl()
                .addOnSuccessListener(uri ->
                        Picasso.get().load(uri).into(fotoUsu)
                );
    }

    public void openMenuModal(){
        dialogMenu.setContentView(R.layout.menu_dialog);
        dialogMenu.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}