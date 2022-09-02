package com.example.healthcare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TelaCadastro extends AppCompatActivity {

    String usuarioID;

    EditText primeiroNome, sobrenome, dataNascCadastro, telefoneCadastro, enderecoCadastro, cpfCadastro, emailCadastro, senhaCadastro;
    Button cadastrarUsu;
    CheckBox mostrarSenhaCadastro, receberNewsLetter;

    String[] sexo = new String []{"Sexo", "Feminino", "Masculino"};
    Spinner spinnerSexo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_cadastro);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

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
        receberNewsLetter = findViewById(R.id.receberNews);


        spinnerSexo = findViewById(R.id.sexoCadastro);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spi_elemento, sexo);
        adapter3.setDropDownViewResource(R.layout.spi_dropdown_elemento);
        spinnerSexo.setAdapter(adapter3);
        spinnerSexo.setSelection(0, false);


        cadastrarUsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sexo = spinnerSexo.getSelectedItem().toString();

                if(primeiroNome.getText().length() <= 2){
                    primeiroNome.setError("Insira seu primeiro nome corretamente");
                }
                else if(sobrenome.getText().length() <= 2){
                    sobrenome.setError("Insira seu sobrenome corretamente");
                }
                else if (emailCadastro.getText().length() < 5){
                    emailCadastro.setError("Insira um email válido!");
                }
                else if (senhaCadastro.getText().length() < 8){
                    senhaCadastro.setError("A sua deve ter pelo menos 8 caracteres!");
                }
                else if(sexo == "Sexo"){
                    TextView errorText = (TextView)spinnerSexo.getSelectedView();
                    errorText.setError("anything here, just to add the icon");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Selecione um sexo válido");
                }
                else{
                    CadastrarUsuario(v);
                }
            }
        });
    }

    public void CadastrarUsuario(View v){

        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();

        Intent irTelaCadastroComplementar = new Intent(this, TelaCadastroComplementar.class);

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    salvarDadosCadastro();

                    startActivity(irTelaCadastroComplementar);
                }else{
                    String erro;
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erro = "Digite uma senha com no mínimo 6 caracteres!";
                    }catch (FirebaseAuthUserCollisionException e){
                        erro = "Esta conta de email já está cadastrada!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        erro = "Email inválido";
                    }catch (Exception e){
                        erro = "Erro ao cadastrar o usuário";
                    }

                    Snackbar snackbar = Snackbar.make(v,erro,Snackbar.LENGTH_LONG);
                    snackbar.setBackgroundTint(Color.WHITE);
                    snackbar.setTextColor(Color.BLACK);
                    snackbar.show();
                }
            }
        });
    }

    public void salvarDadosCadastro(){

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Intent irTelaCadastroComplementar = new Intent(this, TelaCadastroComplementar.class);

        String primeiroN = primeiroNome.getText().toString();
        String sobrenomE = sobrenome.getText().toString();
        String dataNasc = dataNascCadastro.getText().toString();
        String telefone = telefoneCadastro.getText().toString();
        String endereco = enderecoCadastro.getText().toString();
        String cpf = cpfCadastro.getText().toString();
        String sexo = spinnerSexo.getSelectedItem().toString();
        String email = emailCadastro.getText().toString();
        String senha = senhaCadastro.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> cadastroUsuario = new HashMap<>();
        cadastroUsuario.put("Nome completo", primeiroN + " " + sobrenomE);
        cadastroUsuario.put("Primeiro nome", primeiroN);
        cadastroUsuario.put("Sobrenome", sobrenomE);
        cadastroUsuario.put("Data de nascimento", dataNasc);
        cadastroUsuario.put("Telefone", telefone);
        cadastroUsuario.put("Endereço", endereco);
        cadastroUsuario.put("CPF", cpf);
        cadastroUsuario.put("Sexo", sexo);
        cadastroUsuario.put("Email", email);
        cadastroUsuario.put("Senha", senha);

        try {
            DocumentReference ns = db.collection("Usuarios").document(usuarioID).collection("Informações pessoais").document("Informações de cadastro");
            ns.set(cadastroUsuario);
        } catch (Exception e){
            startActivity(irTelaCadastroComplementar);
        }
    }

    public void mostrarSenha(View m) {
        if (mostrarSenhaCadastro.isChecked()){
            senhaCadastro.setInputType(InputType.TYPE_CLASS_TEXT);
        }else{
            senhaCadastro.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void receberNewsLetter(View r){
        if(receberNewsLetter.isChecked()){
            Toast.makeText(this, "A partir de agora você receberá nossas NewsLetter", Toast.LENGTH_LONG).show();
        }
    }

}