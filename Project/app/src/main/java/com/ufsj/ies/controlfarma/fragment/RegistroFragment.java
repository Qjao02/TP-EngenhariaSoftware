package com.ufsj.ies.controlfarma.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.ufsj.ies.controlfarma.R;
import com.ufsj.ies.controlfarma.config.ConfiguracaoFirebase;
import com.ufsj.ies.controlfarma.helper.Base64Custom;
import com.ufsj.ies.controlfarma.model.Medicamento;
import com.ufsj.ies.controlfarma.model.Registro;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class RegistroFragment extends Fragment {
    private Spinner spinner;
    private ArrayList<String> medicamentos;
    private DatabaseReference databaseReference;
    private DatabaseReference databaseReferenceAux;
    private DatabaseReference databaseReference2;
    private ArrayAdapter<String> adapter;
    private EditText numeroEstante;
    private EditText quantidade;
    private EditText validade;
    private EditText codigoDeBarra;
    private EditText fabricante;
    private EditText fornecedor;
    private Button cadastrar;
    private String medicamentoSelecionado;
    private Date date;
    private ValueEventListener valueEventListener;
    private ValueEventListener valueEventListener2;
    private boolean cadastroAutorizado;
    private String numeroEstanteStr,quantidadeStr,validadeStr,codigoDeBarraStr,fabricanteStr,fornecedorStr;
    public RegistroFragment() {
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
        cadastroAutorizado=true;
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        spinner = (Spinner) view.findViewById(R.id.medicamentosRegistroSpinner);

        databaseReference = ConfiguracaoFirebase.getFirebase().child("medicamentos");
        valueEventListener2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean resultado = true;
                medicamentos = new ArrayList<String>();
                medicamentos.clear();
                for(DataSnapshot dados:dataSnapshot.getChildren()){
                    Medicamento m = dados.getValue(Medicamento.class);
                    medicamentos.add(m.getNome());
                }
                adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_dropdown_item,medicamentos);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        medicamentoSelecionado = medicamentos.get(i).toLowerCase();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        databaseReference.addValueEventListener(valueEventListener2);

        numeroEstante = (EditText) view.findViewById(R.id.numEstanteEditText);
        quantidade = (EditText) view.findViewById(R.id.quantidadeMedicamentoEditText);
        validade = (EditText) view.findViewById(R.id.validadeMedicamentoEditText);
        codigoDeBarra = (EditText) view.findViewById(R.id.codBarraMedicamentoEditText);
        fabricante = (EditText) view.findViewById(R.id.fabricanteMedicamentoEditText);
        fornecedor = (EditText) view.findViewById(R.id.fornecedorEditText);
        cadastrar = (Button) view.findViewById(R.id.registroButton);

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cadastroAutorizado=true;
                numeroEstanteStr = numeroEstante.getText().toString();
                quantidadeStr = quantidade.getText().toString();
                validadeStr = validade.getText().toString();
                codigoDeBarraStr = codigoDeBarra.getText().toString();
                fabricanteStr = fabricante.getText().toString();
                fornecedorStr = fornecedor.getText().toString();

                if(!numeroEstanteStr.isEmpty() && !quantidadeStr.isEmpty() && !quantidadeStr.isEmpty() && !validadeStr.isEmpty()
                        && !codigoDeBarraStr.isEmpty() && !fabricanteStr.isEmpty() && !fornecedorStr.isEmpty()){
                    Format formatter;
                    String s;
                    date = new Date();
                    formatter = new SimpleDateFormat(validadeStr);
                    s = formatter.format(date);
                    if(medicamentoSelecionado != null && cadastroAutorizado){
                        cadastroAutorizado=false;
                        databaseReference2 = ConfiguracaoFirebase.getFirebase().child("medicamentos").child(medicamentoSelecionado).child("numPedidos");
                        valueEventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                int numPedidos = dataSnapshot.getValue(Integer.class);
                                cadastrarRegistro(numPedidos+1);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        };
                        databaseReference2.addValueEventListener(valueEventListener);
                    }

                }else{
                    Toast.makeText(getActivity(),"Erro: Existem campos não preenchidos.",Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }
    private void cadastrarRegistro(int numPedidos){

        Registro registro = new Registro();
        registro.setNumeroRegistro(Base64Custom.codificarBase64(medicamentoSelecionado+numPedidos));
        int codBarra = Integer.parseInt(codigoDeBarraStr);
        registro.setCodigoDeBarra(codBarra);
        int numEstante = Integer.parseInt(numeroEstanteStr);
        registro.setNumeroEstante(numEstante);
        int qtd = Integer.parseInt(quantidadeStr);
        registro.setQuantidade(qtd);
        registro.setValidade(date);
        registro.setFabricante(fabricanteStr);
        registro.setFornecedor(fornecedorStr);
        databaseReferenceAux = ConfiguracaoFirebase.getFirebase().child("registros").child(registro.getNumeroRegistro());
        databaseReferenceAux.setValue(registro);
        Toast.makeText(getActivity(),"Registro adicionado com sucesso.",Toast.LENGTH_SHORT).show();

        numeroEstante.setText("");
        quantidade.setText("");
        validade.setText("");
        codigoDeBarra.setText("");
        fabricante.setText("");
        fornecedor.setText("");
        databaseReference2.removeEventListener(valueEventListener);
        databaseReference2.setValue(numPedidos);

    }
}
