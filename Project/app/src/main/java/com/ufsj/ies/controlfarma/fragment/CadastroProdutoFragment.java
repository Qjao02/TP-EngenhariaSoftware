package com.ufsj.ies.controlfarma.fragment;

import android.content.Context;
import android.net.Uri;
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
import com.ufsj.ies.controlfarma.model.Medicamento;

public class CadastroProdutoFragment extends Fragment {
    private EditText nome;
    private EditText prinAtivo;
    private EditText specs;
    private Button cadastrar;
    private DatabaseReference databaseReference;

    public CadastroProdutoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_cadastro_produto, container, false);
        nome = view.findViewById(R.id.nomeMedicamentoEditText);
        prinAtivo = view.findViewById(R.id.prinAtivoEditText);
        specs = view.findViewById(R.id.specEditText);
        cadastrar = view.findViewById(R.id.medicamentoButton);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeStr, prinAtivoStr, specsStr;
                nomeStr = nome.getText().toString();
                prinAtivoStr = prinAtivo.getText().toString();
                specsStr = specs.getText().toString();

                if(!nomeStr.isEmpty() && !prinAtivoStr.isEmpty() && !specsStr.isEmpty()){
                    Medicamento medicamento = new Medicamento();
                    medicamento.setNome(nomeStr);
                    medicamento.setPrincipioAtivo(prinAtivoStr);
                    medicamento.setEspecificacoesArmazenamento(specsStr);
                    medicamento.setNumPedidos(0);
                    verificarDadosRepetidos(nomeStr,medicamento);
                }else{
                    Toast.makeText(getActivity(),"Erro: Um dos campos está vazio.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void verificarDadosRepetidos(final String nomeStr, final Medicamento medicamento){
        final String temp = nomeStr.toLowerCase();
        databaseReference = ConfiguracaoFirebase.getFirebase().child("medicamentos");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean resultado = true;
                for(DataSnapshot dados:dataSnapshot.getChildren()){
                    Medicamento m = dados.getValue(Medicamento.class);
                    if(m.getNome().equals(nomeStr)){
                        Toast.makeText(getActivity(),"Erro: Esse produto já está cadastrado.",Toast.LENGTH_SHORT).show();
                        resultado = false;
                        break;
                    }
                }

                if(resultado){
                    databaseReference.child(temp).setValue(medicamento);
                    Toast.makeText(getActivity(),"Produto cadastrado com sucesso.",Toast.LENGTH_SHORT).show();
                    nome.setText("");
                    prinAtivo.setText("");
                    specs.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
