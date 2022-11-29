package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaConteudos_Premium extends AppCompatActivity {

  TextView olaUsu_Premium;
  CircleImageView fotoUsu;
  DrawerLayout drawerLayout;

  ImageView user, menu;
  TextView nome_user, email_user;

  ImageView inicio, agua, alimento, peso, vacinas, sono, passos, exercicio, medicamento, exames, clinicas, sair;
  TextView textIncio, textAgua, textAlimento, textPeso, textVacinas, textSono, textPassos, textExercicio, textMedicamentos, textExames, textClinicas, textSair;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tela_conteudos__premium);
    getWindow().setStatusBarColor(Color.rgb(12,92,100));
    getSupportActionBar().hide();

    drawerLayout = findViewById(R.id.drawerPremium);

    iniciarComponentes();
    setarImagemPerfil();
    menu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    navigationMenu();
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
        String olaUsuCompleto = "Olá " + primeiroN + "! ⭐";
        olaUsu_Premium.setText(olaUsuCompleto);
      }
    });
  }

  public void navigationMenu(){
    // DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

    DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
            .collection("Usuarios")
            .document(FirebaseHelper.getUIDUsuario())
            .collection("Informações pessoais")
            .document("Informações de cadastro");
    documentReference.addSnapshotListener((snapshot, error) -> {
      if(snapshot.exists()){
        String nome =  snapshot.getString("Primeiro nome");
        String email =  snapshot.getString("Email");

        nome_user.setText(nome);
        if(email.length() >= 25){
          email_user.setText(email.substring(0,23)+"...");
        }else{
          email_user.setText(email);
        }

      }
    });

    user.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPerfil_Premium.class);
      startActivity(irTela);
    });
    nome_user.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPerfil_Premium.class);
      startActivity(irTela);
    });
    email_user.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPerfil_Premium.class);
      startActivity(irTela);
    });



    inicio.setOnClickListener(v -> {
      if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START);
      }      });
    textIncio.setOnClickListener(v -> {
      if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
        drawerLayout.closeDrawer(GravityCompat.START);
      }
    });

    sair.setOnClickListener(v -> {
      FirebaseAuth.getInstance().signOut();
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaInicial.class);
      startActivity(irTela);
    });
    textSair.setOnClickListener(v -> {
      FirebaseAuth.getInstance().signOut();
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaInicial.class);
      startActivity(irTela);
    });

    agua.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaAgua.class);
      startActivity(irTela);
    });
    textAgua.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaAgua.class);
      startActivity(irTela);
    });

    alimento.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaAlimentacao.class);
      startActivity(irTela);
    });
    textAlimento.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaAlimentacao.class);
      startActivity(irTela);
    });

    peso.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPeso.class);
      startActivity(irTela);
    });
    textPeso.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPeso.class);
      startActivity(irTela);
    });

    vacinas.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaVacinas.class);
      startActivity(irTela);
    });
    textVacinas.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaVacinas.class);
      startActivity(irTela);
    });

    sono.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaSono.class);
      startActivity(irTela);
    });
    textSono.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaSono.class);
      startActivity(irTela);
    });

    passos.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPassos.class);
      startActivity(irTela);
    });
    textPassos.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaPassos.class);
      startActivity(irTela);
    });

    exercicio.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaExercicios.class);
      startActivity(irTela);
    });
    textExercicio.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaExercicios.class);
      startActivity(irTela);
    });

    medicamento.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaMedicamentos.class);
      startActivity(irTela);
    });
    textMedicamentos.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaMedicamentos.class);
      startActivity(irTela);
    });

    exames.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaExames.class);
      startActivity(irTela);
    });
    textExames.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaExames.class);
      startActivity(irTela);
    });

    clinicas.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaClinicas.class);
      startActivity(irTela);
    });
    textClinicas.setOnClickListener(v -> {
      Intent irTela = new Intent(TelaConteudos_Premium.this, TelaClinicas.class);
      startActivity(irTela);
    });
  }

  public void iniciarComponentes(){
    olaUsu_Premium = findViewById(R.id.olaUsu_Premium);
    fotoUsu = findViewById(R.id.fotoUsu_Premium);
    menu =findViewById(R.id.menuPremium);

    //Menu
    inicio = drawerLayout.findViewById(R.id.inicio_img_premium);
    textIncio = drawerLayout.findViewById(R.id.text_inicio_premium);

    agua = drawerLayout.findViewById(R.id.agua_img_premium);
    textAgua = drawerLayout.findViewById(R.id.text_agua_premium);

    alimento = drawerLayout.findViewById(R.id.alimento_img_premium);
    textAlimento = drawerLayout.findViewById(R.id.alimento_premium);

    peso = drawerLayout.findViewById(R.id.peso_img_premium);
    textPeso = drawerLayout.findViewById(R.id.peso_premium);

    vacinas = drawerLayout.findViewById(R.id.vacinas_img_premium);
    textVacinas = drawerLayout.findViewById(R.id.vacinas_text_premium);

    sono = drawerLayout.findViewById(R.id.sono_img_premium);
    textSono = drawerLayout.findViewById(R.id.sono_premium);

    passos = drawerLayout.findViewById(R.id.passos_img_premium);
    textPassos = drawerLayout.findViewById(R.id.passos_text_premium);

    exercicio = drawerLayout.findViewById(R.id.exercicios_img_premium);
    textExercicio = drawerLayout.findViewById(R.id.exercicios_text_premium);

    medicamento = drawerLayout.findViewById(R.id.medicamentos_img_premium);
    textMedicamentos = drawerLayout.findViewById(R.id.medicamentos_text_premium);

    exames = drawerLayout.findViewById(R.id.exames_img_premium);
    textExames = drawerLayout.findViewById(R.id.text_exames_premium);

    clinicas = drawerLayout.findViewById(R.id.clinicas_img_premium);
    textClinicas = drawerLayout.findViewById(R.id.clinica_text_premium);

    sair = drawerLayout.findViewById(R.id.sair_premium);
    textSair = drawerLayout.findViewById(R.id.sair_text_premium);

    user = drawerLayout.findViewById(R.id.imgPessoaPremium);
    nome_user = drawerLayout.findViewById(R.id.nameUserPremium);
    email_user = drawerLayout.findViewById(R.id.emailUserPremium);
  }

  public void irTelaPerfil_Premium(View t){
    Intent irTelaPerfil_Premium = new Intent (this, TelaPerfil_Premium.class);
    startActivity(irTelaPerfil_Premium);
  }

  public void irTelaAgua(View a){
    Intent irTelaAgua = new Intent(this, TelaAgua.class);
    startActivity(irTelaAgua);
  }

  public void irTelaAlimentacao(View l){
    Intent irTelaAlimentacao = new Intent(this, TelaAlimentacao.class);
    startActivity(irTelaAlimentacao);
  }

  public void irTelaPassos(View o){
    Intent irTelaPassos = new Intent(this, TelaPassos.class);
    startActivity(irTelaPassos);
  }

  public void irTelaSono(View s){
    Intent irTelaSono = new Intent(this, TelaSono.class);
    startActivity(irTelaSono);
  }

  public void irTelaMedicamentos(View m){
    Intent irTelaMedicamentos = new Intent(this, TelaMedicamentos.class);
    startActivity(irTelaMedicamentos);
  }

  public void irTelaExercicios(View e){
    Intent irTelaExercicios = new Intent(this, TelaExercicios.class);
    startActivity(irTelaExercicios);
  }

  public void irTelaPeso(View p){
    Intent irTelaPeso = new Intent(this, TelaPeso.class);
    startActivity(irTelaPeso);
  }

  public void irTelaVacinas(View v){
    Intent irTelaVacinas = new Intent(this, TelaVacinas.class);
    startActivity(irTelaVacinas);
  }

  public void irTelaExames(View j){
    Intent irTelaExames = new Intent(TelaConteudos_Premium.this, TelaExames.class);
    startActivity(irTelaExames);
  }

  public void irTelaClinicas(View c){
    Intent irTelaClinicas = new Intent(this, TelaClinicas.class);
    startActivity(irTelaClinicas);
  }

  public void setarImagemPerfil(){
    FirebaseHelper.getStorageReference()
            .child("imagens/Fotos de perfil/" + FirebaseHelper.getUIDUsuario() + "/" + FirebaseHelper.getUIDUsuario() + ".jpeg")
            .getDownloadUrl()
            .addOnSuccessListener(uri ->
                    Picasso.get().load(uri).into(fotoUsu)
            )
            .addOnSuccessListener(uri ->
                    Picasso.get().load(uri).into(user)
            );


  }

}