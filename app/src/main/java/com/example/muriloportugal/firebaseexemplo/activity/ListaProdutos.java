package com.example.muriloportugal.firebaseexemplo.activity;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.muriloportugal.firebaseexemplo.R;
import com.example.muriloportugal.firebaseexemplo.adapter.ProdutosAdapter;
import com.example.muriloportugal.firebaseexemplo.dao.ConfiguracaoFirebase;
import com.example.muriloportugal.firebaseexemplo.entidades.Produtos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaProdutos extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<Produtos> adapter;
    private ArrayList<Produtos> produtos;
    private DatabaseReference firebase;
    private ValueEventListener eventListenerProdutos;
    private Produtos produtoExcluir;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_produtos);

        produtos = new ArrayList<>();
        listView = findViewById(R.id.listProdutos);
        adapter = new ProdutosAdapter(this,produtos);
        listView.setAdapter(adapter);

        firebase = ConfiguracaoFirebase.getReferenciaFirebase().child("Produtos");

        eventListenerProdutos = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                produtos.clear();
                for (DataSnapshot dado : dataSnapshot.getChildren()){
                    Produtos produto = dado.getValue(Produtos.class);
                    produtos.add(produto);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                produtoExcluir = adapter.getItem(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaProdutos.this);
                builder.setTitle("Deletar");
                builder.setMessage("Tem certeza que deseja deletar este item?");
                builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        firebase.child(produtoExcluir.getFiltro().toString()).removeValue();
                        Toast.makeText(ListaProdutos.this,"Item excluido!",Toast.LENGTH_SHORT).show();
                    }
                });
                alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(eventListenerProdutos);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(eventListenerProdutos);
    }
}
