package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import java.util.ArrayList;

public class TelaClinicas extends AppCompatActivity {

    ImageView clinicaSaoLucas, hospitalBaiaSul, hospitalUnimed, clinicaUniOdonto;
    static ArrayList<VincularClinicas> lClinicas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_clinicas);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        clinicaSaoLucas = findViewById(R.id.clinicaSaoLucas);
        hospitalBaiaSul = findViewById(R.id.hospitalBaiaSul);
        hospitalUnimed = findViewById(R.id.hospitalUnimed);
        clinicaUniOdonto = findViewById(R.id.clinicaUniOdonto);
    }

    public void setarSaoLucas(View s){
        try {
            VincularClinicas saoLucas = new VincularClinicas(
                    "Clínica São Lucas",
                    "\uD83D\uDCCD Av. Barão do Rio Branco, 461 - Centro,\nPalhoça - SC, 88130-101",
                    "\uD83D\uDCDE (48) 3242-7788",
                    "• Laboratório para realização de diversos exames\n• Raio - x\n• Tomografia\n• Ressonância\nEspecialista da área:\n• Cardiologia;\n• Ortopedia;\n• Pscicologia;\n• Reumatologia;\n• Fisoterapia;\n• Clínico Geral;\n• Pediatria;\n• Ginecologista;\n• Urologista;\n• Dermatologia;",
                    clinicaSaoLucas.getDrawable()
            );
            lClinicas.add(saoLucas);

            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }
    }

    public void setarBaiaSul(View b){
        try {
            VincularClinicas baiaSul = new VincularClinicas(
                    "Hospital Baia-Sul",
                    "\uD83D\uDCCD Rua Menino Deus, 63 Centro,\nFlorianópolis - SC",
                    "\uD83D\uDCDE (48) 3229-7777",
                    "• Laboratório para realização de diversos exames\n• Exames por imagem\n• Diagnóstico\n• Cirurgia Plástica\nEspecialista da área:\n• Cardiologia;\n• Ortopedia;\n• Pscicologia;\n• Reumatologia;\n• Fisoterapia;\n• Clínico Geral;\n• Pediatria;\n• Ginecologista;\n• Urologista;\n• Oncologista;\n• Oftalmologia",
                    hospitalBaiaSul.getDrawable()
            );
            lClinicas.add(baiaSul);

            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }
    }

    public void setarUnimed(View u){
        try {
            VincularClinicas unimed = new VincularClinicas(
                    "Hospital Unimed",
                    "\uD83D\uDCCD R. Manoel Loureiro, 1909 - Barreiros,\nSão José - SC, 88117-331",
                    "\uD83D\uDCDE (48) 3288-4100",
                    "• Laboratório para realização de diversos exames\n• Exames por imagem\n• Aura\n• Participação no clube de benefícios\nEspecialista da área:\n•Cardiologia;\n• Ortopedia;\n• Pscicologia;\n• Reumatologia;\n• Fisoterapia;\n• Clínico Geral;\n• Pediatria;\n• Oftalmologia;\n• Nutricionista;",
                    hospitalUnimed.getDrawable()
            );
            lClinicas.add(unimed);

            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }
    }

    public void setarUniOdonto(View o){
        try {
            VincularClinicas uniOdonto = new VincularClinicas(
                    "Clínica UniOdonto",
                    "\uD83D\uDCCD R. Irmãos Vieira, 967 - sala 208 -\nCampinas, São José - SC, 88101-290",
                    "\uD83D\uDCDE (48) 3247-1717",
                    "• Exames por imagem\n• Prótese\n• Atividade educativa\n• Restauração\n• Manutenção aparelho\n• Colocação aparelho\n• Limpeza bucal\n• Extração de dentes",
                    clinicaUniOdonto.getDrawable()
            );
            lClinicas.add(uniOdonto);

            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }
    }

    public void voltarTelaConteudosP(View l){
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeCustomAnimation(
                        getApplicationContext(), R.anim.fade_in, R.anim.mover_direita
                );
        ActivityCompat.startActivity(TelaClinicas.this, new Intent(this, TelaConteudos_Premium.class), activityOptionsCompat.toBundle());
    }
}