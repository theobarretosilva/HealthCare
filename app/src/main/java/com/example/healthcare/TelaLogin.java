package com.example.healthcare;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;

public class TelaLogin extends AppCompatActivity {

    EditText email_login, senha_login;
    TextView esqueceu_senha;
    CheckBox ver_senha;
    Button logar;

    String erro;
    public static Boolean premium;

    SignInButton btnGoogle;
    private GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        iniciarComponentes();
        btnGoogle.setOnClickListener(view -> {
            signIn();
        });
        requisita();
    }

    public void iniciarComponentes(){
        btnGoogle = findViewById(R.id.btnGoogle);
        email_login = findViewById(R.id.email_login);
        senha_login = findViewById(R.id.senha_login);
        esqueceu_senha = findViewById(R.id.esqueceu_senha);
        ver_senha = findViewById(R.id.ver_senha);
        logar = findViewById(R.id.logar);
    }

    private void requisita() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void autenticarUsuario(View a){
        String email = email_login.getText().toString();
        String senha = senha_login.getText().toString();

        FirebaseHelper.getFirebaseAuth().signInWithEmailAndPassword(email, senha).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                DocumentReference documentReference = FirebaseHelper.getFirebaseFirestore()
                        .collection("Usuarios")
                        .document(FirebaseHelper.getUIDUsuario())
                        .collection("Informações pessoais")
                        .document("Informações de cadastro");

                documentReference.addSnapshotListener((documentSnapshot, error) -> {
                    if (documentSnapshot != null){
                        premium = documentSnapshot.getBoolean("Premium");
                        if (premium){
                            Intent iP = new Intent(this, TelaConteudos_Premium.class);
                            startActivity(iP);
                        }else{
                            Intent i = new Intent(this, TelaConteudos.class);
                            startActivity(i);
                        }
                    }
                });
            } else {
                try {
                    throw task.getException();
                }catch (Exception e){
                    erro = "Erro ao logar o usuário";
                }
                Snackbar snackbar = Snackbar.make(a,erro,Snackbar.LENGTH_LONG);
                snackbar.setBackgroundTint(Color.WHITE);
                snackbar.setTextColor(Color.BLACK);
                snackbar.show();
            }
        });
    }

    public void mostrarSenha(View m) {
        if (ver_senha.isChecked()){
            senha_login.setInputType(InputType.TYPE_CLASS_TEXT);
        }else{
            senha_login.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }

    public void recuperarSenha(View r){

        String email = email_login.getText().toString();

        if (email.isEmpty()){
            email_login.setError("Você precisa inserir o seu email para recuperar a sua senha");
        }else{
            enviarEmail(email);
        }
    }

    private void enviarEmail(String email){
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnSuccessListener(unused ->
                        Toast.makeText(getBaseContext(), "Enviamos uma mensagem para o seu email com um link para redefinir", Toast.LENGTH_LONG).show()
                )
                .addOnFailureListener(e ->
                        Toast.makeText(getBaseContext(), "Erro ao enviar o email", Toast.LENGTH_LONG).show()
                );
    }

    public void irTelaCadastro(View i){
        Intent irTelaCadastro = new Intent(this, TelaCadastro.class);
        startActivity(irTelaCadastro);
    }
}