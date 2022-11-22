package com.example.healthcare;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaConteudos extends AppCompatActivity {

    BottomSheetDialog dialog;
    Dialog dialogMenu;
    DrawerLayout drawerLayout;
    TextView olaUsu, examesBox, clinicasBox;
    CircleImageView fotoUsu;
    ImageView examesLogo, clinicasLogo, cadeado, menu;

    ImageView user;
    TextView nome_user, email_user;


//    ImageView inicio, agua, alimento, peso, vacinas, sono, passos, exercicio, medicamento, exames, clinicas, sair;
//    TextView textIncio, textAgua, textAlimento, textPeso, textVacinas, textSono, textPassos, textExercicio, textMedicamentos, textExames, textClinicas, textSair;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_conteudos);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        setarImagemPerfil();

        menu.setOnClickListener(v -> {
           drawerLayout.openDrawer(GravityCompat.START);
        });

        dialog = new BottomSheetDialog(this);
        mostrarCardPremium();

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
//        drawerLayout = findViewById(R.id.drawerLayout);

//        // componentes do menu lateral
//        inicio = drawerLayout.findViewById(R.id.inicio_img);
//        textIncio = drawerLayout.findViewById(R.id.text_inicio);
//
//        agua = drawerLayout.findViewById(R.id.agua_img);
//        textAgua = drawerLayout.findViewById(R.id.text_agua);
//
//        alimento = drawerLayout.findViewById(R.id.alimento_img);
//        textAlimento = drawerLayout.findViewById(R.id.alimento);
//
//        peso = drawerLayout.findViewById(R.id.peso_img);
//        textPeso = drawerLayout.findViewById(R.id.peso);
//
//        vacinas = drawerLayout.findViewById(R.id.vacinas_img);
//        textVacinas = drawerLayout.findViewById(R.id.vacinas_text);
//
//        sono = drawerLayout.findViewById(R.id.sono_img);
//        textSono = drawerLayout.findViewById(R.id.sono);
//
//        passos = drawerLayout.findViewById(R.id.passos_text);
//        exercicio = drawerLayout.findViewById(R.id.exercicios_text);
//        medicamento = drawerLayout.findViewById(R.id.medicamentos_text);
//        exames = drawerLayout.findViewById(R.id.text_exames);
//        clinicas = drawerLayout.findViewById(R.id.clinica_text);
//        sair = drawerLayout.findViewById(R.id.sair_text);
    }

//    public void navigationMenu(){
//        inicio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            }
//        });
//        textIncio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//            }
//        });
//
//        sair.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                drawerLayout.closeDrawer(GravityCompat.START);
//
//            }
//        });
//
//        agua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaAgua.class);
//                startActivity(irTela);
//            }
//        });
//        textAgua.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaAgua.class);
//                startActivity(irTela);
//            }
//        });
//
//        alimento.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaAlimentacao.class);
//                startActivity(irTela);
//            }
//        });
//
//        peso.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaPeso.class);
//                startActivity(irTela);
//            }
//        });
//
//        vacinas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaVacinas.class);
//                startActivity(irTela);
//            }
//        });
//
//        sono.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaSono.class);
//                startActivity(irTela);
//            }
//        });
//
//        passos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaPassos.class);
//                startActivity(irTela);
//            }
//        });
//
//        exercicio.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaExercicios.class);
//                startActivity(irTela);
//            }
//        });
//
//        medicamento.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaMedicamentos.class);
//                startActivity(irTela);
//            }
//        });
//
//        //Bloqueados
//
//        exames.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaPremium.class);
//                startActivity(irTela);
//            }
//        });
//
//        clinicas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent irTela = new Intent(TelaConteudos.this, TelaPremium.class);
//                startActivity(irTela);
//            }
//        });
//    }

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
}