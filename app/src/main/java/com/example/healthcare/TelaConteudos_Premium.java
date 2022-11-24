package com.example.healthcare;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class TelaConteudos_Premium extends AppCompatActivity {

  TextView olaUsu_Premium;
  CircleImageView fotoUsu;
  DrawerLayout drawerLayout;
  
  ImageView inicio, agua, alimento, peso, vacinas, sono, passos, exercicio, medicamento, exames, clinicas, sair;
  TextView textIncio, textAgua, textAlimento, textPeso, textVacinas, textSono, textPassos, textExercicio, textMedicamentos, textExames, textClinicas, textSair;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tela_conteudos__premium);
    getWindow().setStatusBarColor(Color.rgb(12,92,100));
    getSupportActionBar().hide();

    drawerLayout = findViewById(R.id.drawerLayout);

    iniciarComponentes();
    setarImagemPerfil();
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

  public void iniciarComponentes(){
    olaUsu_Premium = findViewById(R.id.olaUsu_Premium);
    fotoUsu = findViewById(R.id.fotoUsu_Premium);

    //Menu
    inicio = drawerLayout.findViewById(R.id.inicio_img);
    textIncio = drawerLayout.findViewById(R.id.text_inicio);

    agua = drawerLayout.findViewById(R.id.agua_img);
    textAgua = drawerLayout.findViewById(R.id.text_agua);

    alimento = drawerLayout.findViewById(R.id.alimento_img);
    textAlimento = drawerLayout.findViewById(R.id.alimento);

    peso = drawerLayout.findViewById(R.id.peso_img);
    textPeso = drawerLayout.findViewById(R.id.peso);

    vacinas = drawerLayout.findViewById(R.id.vacinas_img);
    textVacinas = drawerLayout.findViewById(R.id.vacinas_text);

    sono = drawerLayout.findViewById(R.id.sono_img);
    textSono = drawerLayout.findViewById(R.id.sono);

    passos = drawerLayout.findViewById(R.id.passos_img);
    textPassos = drawerLayout.findViewById(R.id.passos_text);

    exercicio = drawerLayout.findViewById(R.id.exercicios_img);
    textExercicio = drawerLayout.findViewById(R.id.exercicios_text);

    medicamento = drawerLayout.findViewById(R.id.medicamentos_img);
    textMedicamentos = drawerLayout.findViewById(R.id.medicamentos_text);

    exames = drawerLayout.findViewById(R.id.exames_img);
    textExames = drawerLayout.findViewById(R.id.text_exames);

    clinicas = drawerLayout.findViewById(R.id.clinicas_img);
    textClinicas = drawerLayout.findViewById(R.id.clinica_text);

    sair = drawerLayout.findViewById(R.id.sair);
    textSair = drawerLayout.findViewById(R.id.sair_text);

    user = drawerLayout.findViewById(R.id.imgPessoa);
    nome_user = drawerLayout.findViewById(R.id.nameUser);
    email_user = drawerLayout.findViewById(R.id.emailUser);
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
            );
  }

}