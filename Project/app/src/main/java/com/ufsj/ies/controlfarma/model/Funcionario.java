package com.ufsj.ies.controlfarma.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.ufsj.ies.controlfarma.config.ConfiguracaoFirebase;

/**
 * Created by JV on 18/09/2017.
 */
public class Funcionario {
    private String id;
    private String email;
    private String nome;
    private String senha;
    private String sobrenome;
    private String sexo;

    private DatabaseReference databaseReference;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void salvarDados(){
        databaseReference = ConfiguracaoFirebase.getFirebase();
        databaseReference.child("funcionarios").child(getId()).setValue(this);
    }
}
