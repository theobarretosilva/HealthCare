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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
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
    private FirebaseAuth mAuth;
    AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_login);
        getWindow().setStatusBarColor(Color.rgb(12,92,100));
        getSupportActionBar().hide();

        ad = new AlertDialog.Builder(this);
        mAuth = FirebaseAuth.getInstance();

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

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, (OnCompleteListener<AuthResult>) task -> {
                    if (task.isSuccessful()) {
                        Intent i = new Intent(TelaLogin.this, TelaConteudos.class);
                        startActivity(i);
                    } else {
                        ad.setTitle("Falha de Autenticação!");
                        ad.setMessage("Usuário ou Senha Incorreta");
                        ad.show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
//                ad.setTitle("Falha de Login!");
//                ad.setMessage("Algo inesperado aconteceu!");
//                ad.show();
            }
        }
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
                    if (documentSnapshot.exists()){
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
        startActivity(new Intent(TelaLogin.this, TelaCadastro.class));
    }

    public void irTelaAhQuePena(View a){
        startActivity(new Intent(TelaLogin.this, TelaAhQuePena.class));
    }
}