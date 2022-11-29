package com.example.healthcare;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;
import java.util.GregorianCalendar;

public class TelaCadastroComplementar extends AppCompatActivity{
    EditText peso, altura;
    Spinner biotipo;
    Button btContinuar;

    String[] biotipoCorporal = new String[]{"Biotipo corporal", "Ectomorfo", "Endomorfo", "Mesomorfo"};

    GregorianCalendar calendar = new GregorianCalendar();
    int dia = calendar.get(GregorianCalendar.DAY_OF_MONTH);
    int mes = calendar.get(GregorianCalendar.MONTH);

    Date dataAtual = new Date();
    int horaAtual = dataAtual.getHours();
    int minutosAtual = dataAtual.getMinutes();

    CadastroUsuario cadastroUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro_complementar);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        peso = findViewById(R.id.peso);
        altura = findViewById(R.id.altura);
        btContinuar = findViewById(R.id.btContinuar);
        biotipo = findViewById(R.id.biotipo);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spi_elemento, biotipoCorporal);
        adapter.setDropDownViewResource(R.layout.spi_dropdown_elemento);
        biotipo.setAdapter(adapter);
        biotipo.setSelection(0, false);
    }

    public void verificaPreenchimentoComplementar(View f){
        if (biotipo.getSelectedItem().toString().equals("Biotipo corporal")) {
            TextView errorText = (TextView)biotipo.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Selecione um biotipo corporal");
        } else if(peso.getText().length() == 0) {
            peso.setError("Você precisa preencher este campo");
        } else if (altura.getText().length() == 0) {
            altura.setError("Você precisa preencher este campo");
        } else {
            mandarCadastroCompleBD();
        }
    }

    public void mandarCadastroCompleBD(){
        try {
            cadastroUsuario.cadastrarUsuario();
            CadastroComplementarUsuario cadastroComplementarUsuario = new CadastroComplementarUsuario();
            cadastroComplementarUsuario.setPeso(parseInt(peso.getText().toString()));
            cadastroComplementarUsuario.setAltura(parseInt(altura.getText().toString()));
            cadastroComplementarUsuario.setBiotipo(biotipo.getSelectedItem().toString());
            cadastroComplementarUsuario.cadastrarComplementoUsuario();

            startActivity(new Intent(this, TelaConteudos.class));
        }catch (Exception e){
            Toast.makeText(this, "Não foi possivel continuar o seu cadastro, tente novamente mais tarde!", Toast.LENGTH_LONG).show();
        }
    }

    public void irTiposBiotipoInternet(View j){
        String url = "https://health-care-site.vercel.app/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}