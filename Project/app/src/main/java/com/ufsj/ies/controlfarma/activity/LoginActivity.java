package com.ufsj.ies.controlfarma.activity;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.database.DatabaseReference;
import com.ufsj.ies.controlfarma.R;
import com.ufsj.ies.controlfarma.config.ConfiguracaoFirebase;
import com.ufsj.ies.controlfarma.helper.Base64Custom;
import com.ufsj.ies.controlfarma.helper.Permissao;
import com.ufsj.ies.controlfarma.helper.Preferencias;
import com.ufsj.ies.controlfarma.model.Usuario;


public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button cadastro;
    private Button botao;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private Usuario usuario;
    private String[] permissoesNecessarias = new String[]{
            android.Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        verificarUsuarioLogado();
        Permissao.validarPermissoes(1,this,permissoesNecessarias);
        usuario = new Usuario();
        email = (EditText) findViewById(R.id.emailLoginEditText);
        senha = (EditText) findViewById(R.id.senhaLoginEditText);
        cadastro = (Button) findViewById(R.id.loginCadastroButton);
        botao = (Button) findViewById(R.id.buttonLogin);
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()){
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    try{
                        validarUsuario();
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this,"Erro ao fazer login.",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(LoginActivity.this,"Favor preencher os campos email e senha.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        cadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,CadastroUsuarioActivity.class));
            }
        });
    }

    private void abrirMenuPrincipal(){
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
        finish();
    }

    private void verificarUsuarioLogado(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if(firebaseAuth.getCurrentUser() != null){
            abrirMenuPrincipal();
        }
    }

    public void validarUsuario(){
        databaseReference = ConfiguracaoFirebase.getFirebase();
        firebaseAuth.signInWithEmailAndPassword(usuario.getEmail().toString(),usuario.getSenha().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Preferencias preferencias = new Preferencias(LoginActivity.this);
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    preferencias.salvarDados(idUsuario);
                    abrirMenuPrincipal();
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        Toast.makeText(LoginActivity.this, "Erro ao fazer Login: Email não cadastrado ou inexistente.",Toast.LENGTH_SHORT).show();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(LoginActivity.this, "Erro ao fazer Login: Senha incorreta.",Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(LoginActivity.this, "Erro ao fazer Login.",Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}

