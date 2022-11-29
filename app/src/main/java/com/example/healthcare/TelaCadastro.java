package com.example.healthcare;

import static java.lang.Integer.parseInt;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Calendar;

public class TelaCadastro extends AppCompatActivity {

    EditText primeiroNome, sobrenome, dataNascCadastro, telefoneCadastro, enderecoCadastro, cpfCadastro, emailCadastro, senhaCadastro;
    Button cadastrarUsu;
    CheckBox mostrarSenhaCadastro, aceitarDireitos;

    String[] sexo = new String []{"Sexo", "Feminino", "Masculino"};
    Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
    }

    public void iniciarComponentes(){
        primeiroNome = findViewById(R.id.primeiroNome);
        sobrenome = findViewById(R.id.sobrenome);
        dataNascCadastro = findViewById(R.id.dataNascCadasatro);
        telefoneCadastro = findViewById(R.id.telefoneCadastro);
        enderecoCadastro = findViewById(R.id.enderecoCadastro);
        cpfCadastro = findViewById(R.id.cpfCadastro);
        emailCadastro = findViewById(R.id.emailCadastro);
        senhaCadastro = findViewById(R.id.senhaCadastro);
        cadastrarUsu = findViewById(R.id.cadastrarUsu);
        mostrarSenhaCadastro = findViewById(R.id.mostrarSenhaCadastro);
        aceitarDireitos = findViewById(R.id.aceitarDireitos);

        spinnerSexo = findViewById(R.id.sexoCadastro);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spi_elemento, sexo);
        adapter3.setDropDownViewResource(R.layout.spi_dropdown_elemento);
        spinnerSexo.setAdapter(adapter3);
        spinnerSexo.setSelection(0, false);
    }

    public void verificaPreenchimento(View k){
        String sexo = spinnerSexo.getSelectedItem().toString();

        String[] dataNascimento = dataNascCadastro.getText().toString().split("/");
        int diaNasc = parseInt(dataNascimento[0]);
        int mesNasc = parseInt(dataNascimento[1]);
        int anoNasc = parseInt(dataNascimento[2]);
        Calendar cal = Calendar.getInstance();
        int anoAtual = cal.get(Calendar.YEAR);

        if(primeiroNome.getText().length() <= 2){
            primeiroNome.setError("Insira seu primeiro nome corretamente");
        } else if(sobrenome.getText().length() <= 2){
            sobrenome.setError("Insira seu sobrenome corretamente");
        } else if(dataNascCadastro.getText().length() == 0){
            dataNascCadastro.setError("Preencha a sua data de nascimento");
        } else if(diaNasc < 1){
            dataNascCadastro.setError("Insira um dia de nascimento válido");
        } else if(diaNasc > 31){
            dataNascCadastro.setError("Insira um dia de nascimento válido");
        } else if(mesNasc < 1){
            dataNascCadastro.setError("Insira um mês de nascimento válido");
        } else if(mesNasc > 12){
            dataNascCadastro.setError("Insira um mês de nascimento válido");
        } else if(anoNasc < 1920){
            dataNascCadastro.setError("Insira um ano de nascimento válido");
        } else if(anoNasc >= anoAtual){
            dataNascCadastro.setError("Insira um ano de nascimento válido");
        } else if(dataNascCadastro.getText().toString().contains("_")){
            dataNascCadastro.setError("Insira uma data de nascimento válida");
        } else if(mesNasc == 2 && diaNasc > 29){
            dataNascCadastro.setError("Insira um dia de nascimento válido");
        } else if(telefoneCadastro.getText().toString().contains("_")){
            telefoneCadastro.setError("Insira um telefone válido!");
        } else if(telefoneCadastro.getText().length() > 15){
            telefoneCadastro.setError("Insira um telefone válido!");
        } else if(enderecoCadastro.getText().length() < 5){
            enderecoCadastro.setError("Insira seu endereço corretamente");
        } else if(cpfCadastro.getText().length() == 0){
            cpfCadastro.setError("Preencha o seu CPF");
        } else if(cpfCadastro.getText().toString().contains("_")){
            cpfCadastro.setError("Insira um CPF válido!");
        } else if(sexo == "Sexo"){
            TextView errorText = (TextView)spinnerSexo.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);
            errorText.setText("Selecione um sexo válido");
        } else if(emailCadastro.getText().length() < 5){
            emailCadastro.setError("Insira um email válido!");
        } else if(senhaCadastro.getText().length() < 8){
            senhaCadastro.setError("A sua deve ter pelo menos 8 caracteres!");
        } else if(!aceitarDireitos.isChecked()){
            aceitarDireitos.setError("Você deve aceitar o uso dos seus dados!");
        } else {
            salvarDadosCadastro();
        }
    }

    public void CadastrarUsuario(){
        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha)
            .addOnSuccessListener(authResult -> {
                salvarDadosCadastro();
                startActivity(new Intent(TelaCadastro.this, TelaCadastroComplementar.class));
            })
            .addOnFailureListener(e -> {
                try {
                    throw e.getCause();
                }catch (FirebaseAuthUserCollisionException exception){
                    emailCadastro.setError("Esta conta de email já está cadastrada!");
                }catch (FirebaseAuthInvalidCredentialsException exception){
                    emailCadastro.setError("Email inválido!");
                }catch (Exception exception){
                    Toast.makeText(this, "Erro ao cadastrar o usuário", Toast.LENGTH_LONG).show();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
    }

    public void salvarDadosCadastro(){
        try {
            CadastroUsuario cadastroUsuario = new CadastroUsuario();
            cadastroUsuario.setPrimeiroNome(primeiroNome.getText().toString());
            cadastroUsuario.setSobrenome(sobrenome.getText().toString());
            cadastroUsuario.setDataNascimento(dataNascCadastro.getText().toString());
            cadastroUsuario.setTelefone(telefoneCadastro.getText().toString());
            cadastroUsuario.setEndereco(enderecoCadastro.getText().toString());
            cadastroUsuario.setCpf(cpfCadastro.getText().toString());
            cadastroUsuario.setSexo(spinnerSexo.getSelectedItem().toString());
            cadastroUsuario.setEmail(emailCadastro.getText().toString());
            cadastroUsuario.setSenha(senhaCadastro.getText().toString());
        }catch (Exception e){
            Toast.makeText(this, "Não foi possível fazer o seu cadastro. Tente novamente mais tarde!", Toast.LENGTH_LONG).show();
        }
    }

    public void mostrarSenha(View m) {
        if (mostrarSenhaCadastro.isChecked()){
            senhaCadastro.setInputType(InputType.TYPE_CLASS_TEXT);
        }else{
            senhaCadastro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void irTelaAhQuePena(View a){
        startActivity(new Intent(TelaCadastro.this, TelaAhQuePena.class));
    }

}