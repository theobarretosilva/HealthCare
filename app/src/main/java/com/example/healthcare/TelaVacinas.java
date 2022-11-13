package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TelaVacinas extends AppCompatActivity {

    TextView olaNomeUsu, tituloVacinas, subtituloVacinas;
    CircleImageView fotoUsu;
    RecyclerView rvVacinas;
    private AdapterVacina adapterVacina;
    private List<Vacinas> lsVacinas = new ArrayList<>();

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

        DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Informações de cadastro");

        documentReference.addSnapshotListener((documentSnapshot, error) -> {
            if (documentSnapshot != null){
                String nomeUsu = documentSnapshot.getString("Primeiro nome");
                String olaUsuCompleto = "Olá, " + nomeUsu + "!";
                olaNomeUsu.setText(olaUsuCompleto);
            }
        });
    }

    public void iniciarComponentes(){
        olaNomeUsu = findViewById(R.id.olaNomeUsu);
        fotoUsu = findViewById(R.id.fotoUsuVacinas);
        tituloVacinas = findViewById(R.id.tituloVacinas);
        subtituloVacinas = findViewById(R.id.nomeAlimentoCard);
        rvVacinas = findViewById(R.id.rvVacinas);
    }

    public void voltarTelaConteudos(View d){
        Intent voltarTelaConteudos = new Intent(this, TelaConteudos.class);
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeCustomAnimation(getApplicationContext(), R.anim.fade_in, R.anim.mover_direita);
        ActivityCompat.startActivity(TelaVacinas.this, voltarTelaConteudos, activityOptionsCompat.toBundle());
    }

    public void irTelaPerfil(View f){
        Intent irTelaPerfil = new Intent(this, TelaPerfil.class);
        startActivity(irTelaPerfil);
    }

    public void setarImagemPerfil(){
        FirebaseHelper.getStorageReference()
                .child("imagens/Fotos de perfil/" + FirebaseHelper.getUIDUsuario() + "/" + FirebaseHelper.getUIDUsuario() + ".jpeg")
                .getDownloadUrl()
                .addOnSuccessListener(uri ->
                        Picasso.get().load(uri).into(fotoUsu)
                );
    }

    public void setaCrianca(View crianca){
        lsVacinas.clear();

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

        rvVacinas.setLayoutManager(new LinearLayoutManager(this));
        rvVacinas.setHasFixedSize(true);
        adapterVacina = new AdapterVacina(lsVacinas);
        rvVacinas.setAdapter(adapterVacina);
        adapterVacina.notifyDataSetChanged();
    }

    public void setaJovem(View jovem){
        lsVacinas.clear();

        tituloVacinas.setText("Vacinas jovens");
        subtituloVacinas.setText("A cardeneta de vacinação deve ser\nfrequentemente atualizada, pois algumas\ndelas só são administradas na adolescência.");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);

        Vacinas vacinas1 = new Vacinas("Meninas HPV", "2 doses");
        lsVacinas.add(vacinas1);
        Vacinas vacinas2 = new Vacinas("HPV", "2 doses");
        lsVacinas.add(vacinas2);
        Vacinas vacinas3 = new Vacinas("Meningocócica C", "");
        lsVacinas.add(vacinas3);
        Vacinas vacinas4 = new Vacinas("Hepatite B", "3 doses");
        lsVacinas.add(vacinas4);
        Vacinas vacinas5 = new Vacinas("Febre Amarela", "1 dose, se nunca vacinado");
        lsVacinas.add(vacinas5);
        Vacinas vacinas6 = new Vacinas("Dupla Adulto", "Reforço a cada 10 anos");
        lsVacinas.add(vacinas6);
        Vacinas vacinas7 = new Vacinas("Tríplice Viral", "2 doses, a depender da situação vacinal");
        lsVacinas.add(vacinas7);
        Vacinas vacinas8 = new Vacinas("Pneumocócia 23 Valente", "1 dose, a depender da situação vacinal");
        lsVacinas.add(vacinas8);

        rvVacinas.setLayoutManager(new LinearLayoutManager(this));
        rvVacinas.setHasFixedSize(true);
        adapterVacina = new AdapterVacina(lsVacinas);
        rvVacinas.setAdapter(adapterVacina);
        adapterVacina.notifyDataSetChanged();
    }

    public void setaAdulto(View adulto){
        lsVacinas.clear();

        tituloVacinas.setText("Vacinas adultos");
        subtituloVacinas.setText("A vacina também evita a transmissão para\noutras pessoas que não podem ser\nvacinadas.");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);

        Vacinas vacinas1 = new Vacinas("Hepatite B", "3 doses, de acordo com a situação vacinal");
        lsVacinas.add(vacinas1);
        Vacinas vacinas2 = new Vacinas("Febre Amarela", "Dose única, se nunca vacinado");
        lsVacinas.add(vacinas2);
        Vacinas vacinas3 = new Vacinas("Dupla Adulto (DT)", "Reforço, a cada 10 anos");
        lsVacinas.add(vacinas3);
        Vacinas vacinas4 = new Vacinas("Tríplice Viral", "2 doses, a depender da situação vacinal");
        lsVacinas.add(vacinas4);
        Vacinas vacinas5 = new Vacinas("Pneumocócia 23 Valente", "1 dose, a depender da situação vacinal");
        lsVacinas.add(vacinas5);

        rvVacinas.setLayoutManager(new LinearLayoutManager(this));
        rvVacinas.setHasFixedSize(true);
        adapterVacina = new AdapterVacina(lsVacinas);
        rvVacinas.setAdapter(adapterVacina);
        adapterVacina.notifyDataSetChanged();
    }

    public void setaGestante(View gestante){
        lsVacinas.clear();

        tituloVacinas.setText("Vacinas gestantes");
        subtituloVacinas.setText("A vacina para mulheres grávidas é essencial\npara previnir doenças para si e para o bebê");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);

        Vacinas vacinas1 = new Vacinas("Hepatite B", "3 doses, de acordo com a situação vacinal");
        lsVacinas.add(vacinas1);
        Vacinas vacinas2 = new Vacinas("dTPa (Tríplece bactriana acelular\ndo tipo adulto)", "1 dose a partir da 20° semana de gestação");
        lsVacinas.add(vacinas2);
        Vacinas vacinas3 = new Vacinas("Dupla Adulto (DT)", "Reforço, a cada 10 anos");
        lsVacinas.add(vacinas3);

        rvVacinas.setLayoutManager(new LinearLayoutManager(this));
        rvVacinas.setHasFixedSize(true);
        adapterVacina = new AdapterVacina(lsVacinas);
        rvVacinas.setAdapter(adapterVacina);
        adapterVacina.notifyDataSetChanged();
    }

    public void setaIdoso(View idoso){
        lsVacinas.clear();

        tituloVacinas.setText("Vacinas idosos");
        subtituloVacinas.setText("São três as vacinas disponíveis para pessoas\nacima de 60 anos, além da vacinação contra\ngripe.");

        tituloVacinas.setVisibility(View.VISIBLE);
        subtituloVacinas.setVisibility(View.VISIBLE);

        Vacinas vacinas1 = new Vacinas("Hepatite B", "3 doses, de acordo com a situação vacinal");
        lsVacinas.add(vacinas1);
        Vacinas vacinas2 = new Vacinas("Febre Amarela", "Dose única, se nunca vacinado");
        lsVacinas.add(vacinas2);
        Vacinas vacinas3 = new Vacinas("Dupla Adulto (DT)", "Reforço, a cada 10 anos");
        lsVacinas.add(vacinas3);
        Vacinas vacinas4 = new Vacinas("Tríplice Viral", "2 doses, a depender da situação vacinal");
        lsVacinas.add(vacinas4);
        Vacinas vacinas5 = new Vacinas("Pneumocócia 23 Valente", "1 dose, a depender da situação vacinal");
        lsVacinas.add(vacinas5);

        rvVacinas.setLayoutManager(new LinearLayoutManager(this));
        rvVacinas.setHasFixedSize(true);
        adapterVacina = new AdapterVacina(lsVacinas);
        rvVacinas.setAdapter(adapterVacina);
        adapterVacina.notifyDataSetChanged();
    }
}