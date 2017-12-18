package com.ufsj.ies.controlfarma.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufsj.ies.controlfarma.R;
import com.ufsj.ies.controlfarma.config.ConfiguracaoFirebase;
import com.ufsj.ies.controlfarma.model.Cliente;

public class CadastroClienteFragment extends Fragment {
    private EditText nome;
    private EditText cpf;
    private EditText email;
    private Button cadastrar;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public CadastroClienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        //databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        //databaseReference.removeEventListener(valueEventListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cadastro_cliente, container, false);
        nome = view.findViewById(R.id.nomeUsuarioEditText);
        cpf = view.findViewById(R.id.cpfUsuarioEditText);
        email = view.findViewById(R.id.emailUsuarioEditText);
        cadastrar = view.findViewById(R.id.usuarioButton);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeStr, cpfStr, emailStr;
                nomeStr = nome.getText().toString();
                cpfStr = cpf.getText().toString();
                emailStr = email.getText().toString();

                if(!nomeStr.isEmpty() && !cpfStr.isEmpty() && !emailStr.isEmpty()){
                    if(cpfStr.length() == 11){
                        Cliente cliente = new Cliente();
                        cliente.setNome(nomeStr);
                        cliente.setCpf(cpfStr);
                        cliente.setEmail(emailStr);
                        verificarDadosRepetidos(cpfStr,cliente);
                    }else{
                        Toast.makeText(getActivity(),"Erro: CPF inválido.",Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(getActivity(),"Erro: Um dos campos está vazio.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void verificarDadosRepetidos(final String emailStr, final Cliente cliente){
        databaseReference = ConfiguracaoFirebase.getFirebase().child("clientes");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean resultado = true;
                for(DataSnapshot dados:dataSnapshot.getChildren()){
                    Cliente c = dados.getValue(Cliente.class);
                    if(c.getCpf().equals(emailStr)){
                        Toast.makeText(getActivity(),"Erro: Esse CPF já está cadastrado.",Toast.LENGTH_SHORT).show();
                        resultado = false;
                        break;
                    }
                }

                if(resultado){
                    databaseReference.child(emailStr).setValue(cliente);
                    Toast.makeText(getActivity(),"Cliente cadastrado com sucesso.",Toast.LENGTH_SHORT).show();
                    nome.setText("");
                    email.setText("");
                    cpf.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
