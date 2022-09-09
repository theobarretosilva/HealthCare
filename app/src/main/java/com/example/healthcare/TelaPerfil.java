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
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaPerfil extends AppCompatActivity {

    TextView nomeUsu, idadeUsu, telefoneUsu, emailUsu, pesoUsu, alturaUsu, biotipoUsu;
    Button btnVoltar, btnLogout;
    private CircleImageView fotoUsuPerfil;

    private static final int REQUEST_GALERIA = 100;
    private String caminhoImagem;
    private Bitmap imagem;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageRef = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();

        btnVoltar.setOnClickListener(view -> {
            Intent voltarTelaConteudos = new Intent(TelaPerfil.this, TelaConteudos.class);
            startActivity(voltarTelaConteudos);
        });
        btnLogout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(TelaPerfil.this, TelaInicial.class);
            startActivity(logout);
        });

        setarInfoCadastro();
        setarInfoCadasComple();
        setarImagemPerfil();
    }

    public void verificaPermissaoGaleria(View view){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(TelaPerfil.this, "Permissão negada.", Toast.LENGTH_SHORT).show();
            }
        };

        showDialogPermissao(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    }

    private void abrirGaleria(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    }

    private void showDialogPermissao(PermissionListener listener, String[] permissoes){
        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissões")
                .setDeniedMessage("Você negou a permissão para acessar a galeria do dispositivo, deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes)
                .check();
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

                    String dataNasc = documentSnapshot.getString("Data de nascimento");

                    LocalDate dataNascFormatada = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate dataHoje = LocalDate.now();

                    int idade = dataHoje.getYear() - dataNascFormatada.getYear();
                    String idadeString = String.valueOf(idade);
                    idadeUsu.setText("\uD83C\uDF82 "+ idadeString+" anos");

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

    private void salvarImagemUsu(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference reference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("Fotos de perfil")
                .child(usuarioID)
                .child(usuarioID + ".jpeg");

        UploadTask uploadTask = reference.putFile(Uri.parse(caminhoImagem));
    }

    private void iniciarComponentes(){
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == REQUEST_GALERIA){
                Uri localImagemSelecionada = data.getData();
                caminhoImagem = localImagemSelecionada.toString();

                if (Build.VERSION.SDK_INT > 28){
                    try {
                        imagem = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(), localImagemSelecionada);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    ImageDecoder.Source source = ImageDecoder.createSource(getBaseContext().getContentResolver(), localImagemSelecionada);
                    try {
                        imagem = ImageDecoder.decodeBitmap(source);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                fotoUsuPerfil.setImageBitmap(imagem);
                salvarImagemUsu();
            }
        }
    }

    public void setarImagemPerfil(){
        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        storageRef.child("imagens/Fotos de perfil/" + usuarioID + "/" + usuarioID + ".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(fotoUsuPerfil);
            }
        });
    }
}