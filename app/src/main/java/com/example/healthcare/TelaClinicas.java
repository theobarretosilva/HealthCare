package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TelaClinicas extends AppCompatActivity {

    ImageView clinicaSaoLucas, hospitalBaiaSul, hospitalUnimed, clinicaUniOdonto;
    VincularClinicas vincularClinicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_clinicas);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        clinicaSaoLucas = findViewById(R.id.clinicaSaoLucas);
        hospitalBaiaSul = findViewById(R.id.hospitalBaiaSul);
        hospitalUnimed = findViewById(R.id.hospitalUnimed);
        clinicaUniOdonto = findViewById(R.id.clinicaUniOdonto);

    }

    public void setarSaoLucas(View s){
            VincularClinicas vincularClinicas = new VincularClinicas();
            vincularClinicas.setNomeClinica("Clínica São Lucas");
            vincularClinicas.setEnderecoClinica("\uD83D\uDCCD Av. Barão do Rio Branco, 461 - Centro,\nPalhoça - SC, 88130-101");
            vincularClinicas.setTelefoneClinica("\uD83D\uDCDE (48) 3242-7788");
            vincularClinicas.setServicosClinica("• Laboratório para realização de diversos exames\n• Raio - x\n• Tomografia\n• Ressonância\nEspecialista da área:\n• Cardiologia;\n• Ortopedia;\n• Pscicologia;\n• Reumatologia;\n• Fisoterapia;\n• Clínico Geral;\n• Pediatria;\n• Ginecologista;\n• Urologista;\n• Dermatologia;");
            vincularClinicas.setFotoClinica(clinicaSaoLucas.getDrawable());
            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);

    }

    public void setarBaiaSul(View b){
        try {
            VincularClinicas vincularClinicas = new VincularClinicas();
            vincularClinicas.setNomeClinica("Hospital Baia-Sul");
            vincularClinicas.setEnderecoClinica("\uD83D\uDCCD Rua Menino Deus, 63 Centro,\nFlorianópolis - SC");
            vincularClinicas.setTelefoneClinica("\uD83D\uDCDE (48) 3229-7777");
            vincularClinicas.setServicosClinica("• Laboratório para realização de diversos exames\n• Exames por imagem\n• Diagnóstico\n• Cirurgia Plástica\nEspecialista da área:\n• Cardiologia;\n• Ortopedia;\n• Pscicologia;\n• Reumatologia;\n• Fisoterapia;\n• Clínico Geral;\n• Pediatria;\n• Ginecologista;\n• Urologista;\n• Oncologista;\n• Oftalmologia");
            vincularClinicas.setFotoClinica(hospitalBaiaSul.getDrawable());
            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }

    }

    public void setarUnimed(View u){
        try {
            VincularClinicas vincularClinicas = new VincularClinicas();
            vincularClinicas.setNomeClinica("Hospital Unimed");
            vincularClinicas.setEnderecoClinica("\uD83D\uDCCD R. Manoel Loureiro, 1909 - Barreiros,\nSão José - SC, 88117-331");
            vincularClinicas.setTelefoneClinica("\uD83D\uDCDE (48) 3288-4100");
            vincularClinicas.setServicosClinica("• Laboratório para realização de diversos exames\n• Exames por imagem\n• Aura\n• Participação no clube de benefícios\nEspecialista da área:\n•Cardiologia;\n• Ortopedia;\n• Pscicologia;\n• Reumatologia;\n• Fisoterapia;\n• Clínico Geral;\n• Pediatria;\n• Oftalmologia;\n• Nutricionista;");
            vincularClinicas.setFotoClinica(hospitalUnimed.getDrawable());
            Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }
    }

    public void setarUniOdonto(View o){
        try {
            VincularClinicas vincularClinicas = new VincularClinicas();
            vincularClinicas.setNomeClinica("Clínica UniOdonto");
            vincularClinicas.setEnderecoClinica("\uD83D\uDCCD R. Irmãos Vieira, 967 - sala 208 -\nCampinas, São José - SC, 88101-290");
            vincularClinicas.setTelefoneClinica("\uD83D\uDCDE (48) 3247-1717");
            vincularClinicas.setServicosClinica("• Exames por imagem\n• Prótese\n• Atividade educativa\n• Restauração\n• Manutenção aparelho\n• Colocação aparelho\n• Limpeza bucal\n• Extração de dentes");
            vincularClinicas.setFotoClinica(clinicaUniOdonto.getDrawable());Intent irTelaVinculacao = new Intent(this, TelaVinculacaoClinicas.class);
            startActivity(irTelaVinculacao);
        } catch (Exception e) {
            Toast.makeText(this, "Não foi possível vincular a clínica", Toast.LENGTH_SHORT).show();
        }
    }
}