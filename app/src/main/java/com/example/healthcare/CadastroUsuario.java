package com.example.healthcare;

import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;

public class CadastroUsuario {
    String primeiroNome, sobrenome, dataNascimento, telefone, endereco, cpf, sexo, email, senha;

    public void cadastrarUsuario(){
        Map<String, Object> cadastroUsuario = new HashMap<>();
        cadastroUsuario.put("Nome completo", primeiroNome + " " + sobrenome);
        cadastroUsuario.put("Primeiro nome", primeiroNome);
        cadastroUsuario.put("Sobrenome", sobrenome);
        cadastroUsuario.put("Data de nascimento", dataNascimento);
        cadastroUsuario.put("Telefone", telefone);
        cadastroUsuario.put("Endereço", endereco);
        cadastroUsuario.put("CPF", cpf);
        cadastroUsuario.put("Sexo", sexo);
        cadastroUsuario.put("Email", email);
        cadastroUsuario.put("Senha", senha);
        cadastroUsuario.put("Premium", false);

        DocumentReference ns = FirebaseHelper.getFirebaseFirestore()
                .collection("Usuarios")
                .document(FirebaseHelper.getUIDUsuario())
                .collection("Informações pessoais")
                .document("Informações de cadastro");
        ns.set(cadastroUsuario);


    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }
    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobrenome() {
        return sobrenome;
    }
    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }
    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getSexo() {
        return sexo;
    }
    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}
