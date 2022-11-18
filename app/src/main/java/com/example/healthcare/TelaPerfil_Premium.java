package com.example.healthcare;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaPerfil_Premium extends AppCompatActivity {

    TextView nomeUsuP, idadeUsuP, telefoneUsuP, emailUsuP, pesoUsuP, alturaUsuP, biotipoUsuP, nenhumLembrete;
    Button btnVoltarP, btnSairP;
    CircleImageView fotoUsuP;

    private AdapterLembrete adapterLembrete;
    private List<Lembrete> lembreteList = new ArrayList<>();
    private RecyclerView rvLembretes;

    private static final int REQUEST_GALERIA = 100;
    private String caminhoImagem;
    private Bitmap imagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_perfil__premium);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarInfoCadastroP();
        setarInfoCadasCompP();
        setarImagemPerfilP();
        setarClinicas();
        configRecyclerClinicas();
        setarLembretes();
        configRecyclerLembretes();
    }

    public void iniciarComponentes(){
        nomeUsuP = findViewById(R.id.nomeUsuP);
        idadeUsuP = findViewById(R.id.idadeUsuP);
        telefoneUsuP = findViewById(R.id.telUsuP);
        emailUsuP = findViewById(R.id.emailUsuP);
        pesoUsuP = findViewById(R.id.pesoUsuP);
        alturaUsuP = findViewById(R.id.alturaUsuP);
        biotipoUsuP = findViewById(R.id.biotipoUsuP);
        btnVoltarP = findViewById(R.id.btnVoltarP);
        btnSairP = findViewById(R.id.btnSairP);
        fotoUsuP = findViewById(R.id.fotoUsuP);
        rvLembretes = findViewById(R.id.rvLembretes);
        nenhumLembrete = findViewById(R.id.nenhumLembrete);
    }

    private void configRecyclerClinicas(){

    }

    public void setarClinicas(){

    }

    private void configRecyclerLembretes(){
        rvLembretes.setLayoutManager(new LinearLayoutManager(this));
        rvLembretes.setHasFixedSize(true);
        adapterLembrete = new AdapterLembrete(lembreteList);
        rvLembretes.setAdapter(adapterLembrete);
    }

    public void setarLembretes(){
        DatabaseReference lembretesRef = FirebaseHelper.getDatabaseReference()
                .child("Registros")
                .child(FirebaseHelper.getUIDUsuario())
                .child("Lembretes");
        lembretesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot snap : snapshot.getChildren()){
                        Lembrete lembrete = snap.getValue(Lembrete.class);
                        lembreteList.add(lembrete);
                        nenhumLembrete.setVisibility(View.INVISIBLE);
                    }
                    adapterLembrete.notifyDataSetChanged();
                } else {
                    rvLembretes.setVisibility(View.INVISIBLE);
                    nenhumLembrete.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void voltarTelaConteudosPremium(View d){
        Intent voltarTelaConteudosPremium = new Intent(this, TelaConteudos_Premium.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(TelaPerfil_Premium.this, voltarTelaConteudosPremium, activityOptionsCompat.toBundle());
    }

    public void logoff(View a){
        FirebaseAuth.getInstance().signOut();
        Intent logout = new Intent(TelaPerfil_Premium.this, TelaInicial.class);
        startActivity(logout);
    }

    public void verificaPermissaoGaleriaP(View viewp){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleriaP();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(TelaPerfil_Premium.this, "Permissão negada.", Toast.LENGTH_SHORT).show();
            }
        };
        showDialogPermissaoP(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});
    };

    private void abrirGaleriaP(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    };

    private void showDialogPermissaoP(PermissionListener listener, String[] permissoes){
        TedPermission.create()
                .setPermissionListener(listener)
                .setDeniedTitle("Permissões")
                .setDeniedMessage("Você negou a permissão para acessar a galeria do dispositivo, deseja permitir?")
                .setDeniedCloseButtonText("Não")
                .setGotoSettingButtonText("Sim")
                .setPermissions(permissoes)
                .check();
    };

    public void setarInfoCadastroP(){
        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Informações de cadastro");

        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                String nomeCompleto = documentSnapshot.getString("Nome completo");
                nomeUsuP.setText(nomeCompleto);

                String dataNasc = documentSnapshot.getString("Data de nascimento");

                LocalDate dataNascFormatada = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDate dataHoje = LocalDate.now();

                int idade = dataHoje.getYear() - dataNascFormatada.getYear();
                String idadeString = String.valueOf(idade);
                idadeUsuP.setText("\uD83C\uDF82 "+ idadeString+" anos");

                String telefone = documentSnapshot.getString("Telefone");
                String telefoneE = "\uD83D\uDCDE " + telefone;
                telefoneUsuP.setText(telefoneE);

                String email = documentSnapshot.getString("Email");
                String emailL = "\uD83D\uDCE7 " + email;
                emailUsuP.setText(emailL);
            }
        });
    };

    public void setarInfoCadasCompP(){
        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Cadastro complementar");

        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                int peso = Math.toIntExact((Long) documentSnapshot.getData().get("Peso (kg)"));
                pesoUsuP.setText(peso + " quilos");

                int altura = Math.toIntExact((Long) documentSnapshot.getData().get("Altura (cm)"));
                alturaUsuP.setText(altura + " cm");

                String biotipo = documentSnapshot.getString("Biotipo corporal");
                biotipoUsuP.setText(biotipo);
            }
        });
    };

    private void salvarImagemUsuP(){
        StorageReference reference = FirebaseHelper.getStorageReference()
                .child("imagens")
                .child("Fotos de perfil")
                .child(FirebaseHelper.getUIDUsuario())
                .child(FirebaseHelper.getUIDUsuario() + ".jpeg");

        UploadTask uploadTask = reference.putFile(Uri.parse(caminhoImagem));
    };

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

                fotoUsuP.setImageBitmap(imagem);
                salvarImagemUsuP();
            }
        }
    };

    public void setarImagemPerfilP(){
        FirebaseHelper.getStorageReference()
                .child("imagens/Fotos de perfil/" + FirebaseHelper.getUIDUsuario() + "/" + FirebaseHelper.getUIDUsuario() + ".jpeg")
                .getDownloadUrl()
                .addOnSuccessListener(uri ->
                        Picasso.get().load(uri).into(fotoUsuP)
                );
    };

    public void irTelaAddLembretes(View f){
        Intent irTelaLembretes = new Intent(this, TelaLembretes_add.class);
        startActivity(irTelaLembretes);
    }
}