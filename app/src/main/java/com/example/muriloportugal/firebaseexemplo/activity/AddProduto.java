package com.example.muriloportugal.firebaseexemplo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muriloportugal.firebaseexemplo.R;
import com.example.muriloportugal.firebaseexemplo.dao.ConfiguracaoFirebase;
import com.example.muriloportugal.firebaseexemplo.entidades.Produtos;
import com.google.firebase.database.DatabaseReference;

public class AddProduto extends AppCompatActivity {

    private EditText edtAlturaAdd;
    private EditText edtLarguraAdd;
    private EditText edtPesoAdd;
    private EditText edtCorAdd;
    private EditText edtValorAdd;
    private Button btnSalvar;
    private DatabaseReference firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_produto);

        edtAlturaAdd = findViewById(R.id.edtAlturaAdd);
        edtLarguraAdd = findViewById(R.id.edtLarguraAdd);
        edtPesoAdd = findViewById(R.id.edtPesoAdd);
        edtCorAdd = findViewById(R.id.edtCorAdd);
        edtValorAdd = findViewById(R.id.edtValorAdd);
        btnSalvar = findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (verificaPreenchimento()){
                    Produtos produtos = new Produtos();
                    produtos.setAltura(edtAlturaAdd.getText().toString());
                    produtos.setLargura(edtLarguraAdd.getText().toString());
                    produtos.setPeso(edtPesoAdd.getText().toString());
                    produtos.setCor(edtCorAdd.getText().toString());
                    produtos.setValor(Double.valueOf(edtValorAdd.getText().toString()));

                    salvarProdutos(produtos);
                }else{
                    Toast.makeText(AddProduto.this,"Preencher todos os campos.",Toast.LENGTH_SHORT);
                }

            }
        });


    }

    private boolean verificaPreenchimento() {
        if (!edtAlturaAdd.getText().toString().equals("") &&
            !edtLarguraAdd.getText().toString().equals("") &&
            !edtPesoAdd.getText().toString().equals("") &&
            !edtCorAdd.getText().toString().equals("") &&
            !edtValorAdd.getText().toString().equals("")){
            return true;
        }
        return false;
    }

    private boolean salvarProdutos(Produtos produtos) {
        try{
            firebase = ConfiguracaoFirebase.getReferenciaFirebase().child("Produtos");
            firebase.child(produtos.getFiltro()).setValue(produtos);
            Toast.makeText(AddProduto.this,"Produto salvo com sucesso!",Toast.LENGTH_SHORT).show();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(AddProduto.this,"Erro ao salvar produto.",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
