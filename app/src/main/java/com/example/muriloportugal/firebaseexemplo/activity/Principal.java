package com.example.muriloportugal.firebaseexemplo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.muriloportugal.firebaseexemplo.R;
import com.example.muriloportugal.firebaseexemplo.dao.ConfiguracaoFirebase;
import com.example.muriloportugal.firebaseexemplo.entidades.Produtos;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Principal extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private Spinner spAltura,spLargura,spPeso,spCor;
    private Button btnBuscaValor;
    private TextView tvValorBusca;
    private DatabaseReference firebase;
    private ValueEventListener eventListener,eventListenerValor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        spAltura = findViewById(R.id.spAltura);
        spLargura = findViewById(R.id.spLargura);
        spPeso = findViewById(R.id.spPeso);
        spCor = findViewById(R.id.spCor);
        btnBuscaValor = findViewById(R.id.btnBuscaValor);
        tvValorBusca = findViewById(R.id.tvValorBusca);

        firebase = ConfiguracaoFirebase.getReferenciaFirebase().child("Produtos");

        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> listAltura = new ArrayList<String>();
                final List<String> listLargura = new ArrayList<String>();
                final List<String> listPeso = new ArrayList<String>();
                final List<String> listCor = new ArrayList<String>();

                for (DataSnapshot data : dataSnapshot.getChildren()){
                    Produtos produtos = data.getValue(Produtos.class);
                    listAltura.add(produtos.getAltura().toString());
                    listLargura.add(produtos.getLargura().toString());
                    listPeso.add(produtos.getPeso().toString());
                    listCor.add(produtos.getCor().toString());
                }
                ArrayAdapter<String> alturaAdapter = new ArrayAdapter<String>(Principal.this,android.R.layout.simple_spinner_item,listAltura);
                alturaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spAltura.setAdapter(alturaAdapter);

                ArrayAdapter<String> larguraAdapter = new ArrayAdapter<String>(Principal.this,android.R.layout.simple_spinner_item,listLargura);
                larguraAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spLargura.setAdapter(larguraAdapter);

                ArrayAdapter<String> pesoAdapter = new ArrayAdapter<String>(Principal.this,android.R.layout.simple_spinner_item,listPeso);
                pesoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spPeso.setAdapter(pesoAdapter);

                ArrayAdapter<String> corAdapter = new ArrayAdapter<String>(Principal.this,android.R.layout.simple_spinner_item,listCor);
                corAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCor.setAdapter(corAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        btnBuscaValor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filtro = "_"+spAltura.getSelectedItem().toString().replaceAll("[\\.,\\,]","")+
                        "_"+spLargura.getSelectedItem().toString().replaceAll("[\\.,\\,]","")+
                        "_"+spPeso.getSelectedItem().toString().replaceAll("[\\.,\\,]","")+
                        "_"+spCor.getSelectedItem().toString().replaceAll("[\\.,\\,]","");

                firebase.child(filtro).addListenerForSingleValueEvent(eventListenerValor);
//                //DatabaseReference fireb = FirebaseDatabase.getInstance().getReference();
//                firebase.child(filtro).addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        Produtos produto = dataSnapshot.getValue(Produtos.class);
//                        if (produto != null){
//                            tvValorBusca.setText(produto.getValor().toString());
//                        }else{
//                            Toast.makeText(Principal.this,"Nenhum valor encontrado!",Toast.LENGTH_SHORT).show();
//                            tvValorBusca.setText("");
//                        }
//                    }
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });

            }
        });

        eventListenerValor = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Produtos produto = dataSnapshot.getValue(Produtos.class);
                if (produto != null){
                    tvValorBusca.setText(produto.getValor().toString());
                }else{
                    Toast.makeText(Principal.this,"Nenhum valor encontrado!",Toast.LENGTH_SHORT).show();
                    tvValorBusca.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Sobreescreve o metodo para criar o menu.
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.menu_sair){
            logoutUsuario();
        }else if(id == R.id.menu_add){

        }else if(id == R.id.menu_editar){

        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUsuario() {
        autenticacao.signOut();
        Intent intent = new Intent(Principal.this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(eventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(eventListener);
        firebase.removeEventListener(eventListenerValor);
    }
}
