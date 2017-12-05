package com.ufsj.ies.controlfarma.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.ufsj.ies.controlfarma.R;
import com.ufsj.ies.controlfarma.config.ConfiguracaoFirebase;
import com.ufsj.ies.controlfarma.helper.Base64Custom;
import com.ufsj.ies.controlfarma.helper.Preferencias;
import com.ufsj.ies.controlfarma.model.Usuario;

public class CadastroUsuarioActivity extends AppCompatActivity {
    private EditText nome;
    private EditText email;
    private EditText senha;
    private EditText sobrenome;
    private EditText senha2;
    private Button botao;
    private Usuario usuario;
    private ImageView voltar;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private boolean cadastrou;
    private Spinner spinner;
    private String[] sexos = {"(Escolha seu sexo)","Masculino","Feminino","Outro"};
    //Comentario xddd
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);
        cadastrou=false;
        databaseReference = ConfiguracaoFirebase.getFirebase();
        nome = (EditText) findViewById(R.id.nomeCadastroEditText);
        email = (EditText) findViewById(R.id.emailCadastroEditText);
        senha = (EditText) findViewById(R.id.senhaCadastroEditText);
        botao = (Button) findViewById(R.id.buttonCadastrar);
        sobrenome = (EditText) findViewById(R.id.sobrenomeCadastroEditText);
        senha2 = (EditText) findViewById(R.id.reSenhaCadastroEditText);
        voltar = (ImageView) findViewById(R.id.setaCadastroImageView);
        spinner = (Spinner) findViewById(R.id.cadastroSpinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sexos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0:break;
                    case 1:break;
                    case 2:break;
                    case 3:break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        usuario = new Usuario();
        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!nome.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !senha.getText().toString().isEmpty()
                        && !senha2.getText().toString().isEmpty() && !sobrenome.getText().toString().isEmpty() && spinner.getSelectedItemPosition() != 0){
                    if(senha.getText().toString().equals(senha2.getText().toString())){
                        usuario.setNome(nome.getText().toString());
                        usuario.setEmail(email.getText().toString());
                        usuario.setSenha(senha.getText().toString());
                        usuario.setSexo(spinner.getSelectedItem().toString());
                        usuario.setSobrenome(sobrenome.getText().toString());
                        if(!cadastrou) {
                            cadastrarUsuario();
                        }
                    }else{
                        Toast.makeText(CadastroUsuarioActivity.this,"Erro: Senhas diferentes.",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this,"Erro: Existem campos não preenchidos.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirTelaLogin();
            }
        });
    }

    private void abrirTelaLogin(){
        startActivity(new Intent(CadastroUsuarioActivity.this,LoginActivity.class));
        finish();
    }
    private void cadastrarUsuario(){
        firebaseAuth = ConfiguracaoFirebase.getFirebaseAutenticacao();
        firebaseAuth.createUserWithEmailAndPassword(usuario.getEmail(),usuario.getSenha()).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    String idUsuario = Base64Custom.codificarBase64(usuario.getEmail());
                    usuario.setId(idUsuario);
                    usuario.salvarDados();

                    Preferencias preferencias = new Preferencias(CadastroUsuarioActivity.this);
                    preferencias.salvarDados(idUsuario);


                    Toast.makeText(CadastroUsuarioActivity.this,"Usuário cadastrado com sucesso!",Toast.LENGTH_LONG).show();
                    cadastrou=true;
                    abrirTelaLogin();
                }else{
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        Toast.makeText(CadastroUsuarioActivity.this,"Erro ao cadastrar usuário: Senha fraca.",Toast.LENGTH_LONG).show();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        Toast.makeText(CadastroUsuarioActivity.this,"Erro ao cadastrar usuário: Email inválido.",Toast.LENGTH_LONG).show();
                    }catch (FirebaseAuthUserCollisionException e){
                        Toast.makeText(CadastroUsuarioActivity.this,"Erro ao cadastrar usuário: Esse email já está cadastrado.",Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CadastroUsuarioActivity.this,"Erro ao cadastrar usuário.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

}
