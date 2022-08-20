package com.example.healthcare;

import static java.lang.Integer.parseInt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaPerfil extends AppCompatActivity {

    TextView nomeUsu, idadeUsu, telefoneUsu, emailUsu, pesoUsu, alturaUsu, biotipoUsu;
    Button btnVoltar, btnLogout;
    ImageView fotoUsuPerfil;

    private Uri uri_imagem;
    private FirebaseStorage storage;

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
        fotoUsuPerfil = findViewById(R.id.fotoUsuPerfil);

        storage = FirebaseStorage.getInstance();

        btnVoltar.setOnClickListener(view -> {
            Intent voltarTelaConteudos = new Intent(TelaPerfil.this, TelaConteudos.class);
            startActivity(voltarTelaConteudos);
        });
        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(TelaPerfil.this, TelaInicial.class);
            startActivity(logout);
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

        setarInfoCadastro();
        setarInfoCadasComple();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result: grantResults){
            if (result == PackageManager.PERMISSION_DENIED){
                Toast.makeText(this, "Aceite as permissões para o aplicativo funcionar corretamente", Toast.LENGTH_SHORT).show();
                finish();

                break;
            }
        }
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

    public void obterImagemGaleria(View m){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);

        startActivityForResult(Intent.createChooser(intent, "Escolha uma imagem"), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){

            if (requestCode == 0){

                if (data != null){
                    uri_imagem = data.getData();
                    Glide.with(getBaseContext()).asBitmap().load(uri_imagem).listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            Toast.makeText(getBaseContext(), "Falha ao selecionar imagem", Toast.LENGTH_LONG).show();
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    }).into(fotoUsuPerfil);
                }else{
                    Toast.makeText(getBaseContext(), "Falha ao selecionar imagem", Toast.LENGTH_LONG).show();
                }
//                uploadImagem();
            }
        }
    }

//    private void uploadImagem(){
//        String uidUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        StorageReference reference = storage.getReference().child("Imagens de perfil dos usuários");
//        StorageReference nomeImagem = reference.child(uidUsuario + ".jpg");
//
//        BitmapDrawable drawable = (BitmapDrawable) fotoUsuPerfil.getDrawable();
//        Bitmap bitmap = drawable.getBitmap();
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//
//        UploadTask uploadTask = nomeImagem.putBytes(bytes.toByteArray());
//
//        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//
//
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()){
//                    Toast.makeText(getBaseContext(), "Sucesso ao realizar upload", Toast.LENGTH_LONG).show();
//                }else{
//                    Toast.makeText(getBaseContext(), "Erro ao realizar upload", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == TAKE_IMAGE_CODE){
//            switch (resultCode){
//                case RESULT_OK:
//                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
//                    fotoUsu.setImageBitmap(bitmap);
//                    handleUpload(bitmap);
//            }
//        }
//    }
//
//    private void handleUpload(Bitmap bitmap){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//
//        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        StorageReference reference = FirebaseStorage.getInstance().getReference()
//                .child("profileImages")
//                .child(uid + ".jpeg");
//
//        reference.putBytes(baos.toByteArray())
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        getDownloadUrl(reference);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.e(TAG, "onFailure: " + e.getCause());
//                    }
//                });
//    }
}