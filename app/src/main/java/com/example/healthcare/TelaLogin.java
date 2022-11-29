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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
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
    GoogleSignInClient mGoogleSignInClient;
    public static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    AlertDialog.Builder ad;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private static final int REQ_ONE_TAP = 1234;
    private boolean showOneTapUI = true;

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
//            switch (view.getId()) {
//                case R.id.btnGoogle:
                    signIn();
//                    break;
//            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("120097756940-sics663t7a1of4p3hgt0n5euedgkhnn4.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void iniciarComponentes(){
        btnGoogle = findViewById(R.id.btnGoogle);
        email_login = findViewById(R.id.email_login);
        senha_login = findViewById(R.id.senha_login);
        esqueceu_senha = findViewById(R.id.esqueceu_senha);
        ver_senha = findViewById(R.id.ver_senha);
        logar = findViewById(R.id.logar);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            System.out.println("Aqui foi");
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            System.out.println(account.getEmail());
            System.out.println("porra aqui não foi");

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            System.out.println(e.getStatusCode());

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