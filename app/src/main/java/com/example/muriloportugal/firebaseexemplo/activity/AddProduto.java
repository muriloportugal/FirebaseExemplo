package com.example.muriloportugal.firebaseexemplo.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private Produtos produtos;

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

        Intent intent = getIntent();
        produtos = intent.getParcelableExtra("Produto Parcel");
        if (produtos != null){
            edtAlturaAdd.setText(produtos.getAltura());
            edtLarguraAdd.setText(produtos.getLargura());
            edtPesoAdd.setText(produtos.getPeso());
            edtCorAdd.setText(produtos.getCor());
            edtValorAdd.setText(String.valueOf(produtos.getValor()));

            edtAlturaAdd.setEnabled(false);
            edtAlturaAdd.setFocusable(false);
            edtLarguraAdd.setEnabled(false);
            edtLarguraAdd.setFocusable(false);
            edtPesoAdd.setEnabled(false);
            edtPesoAdd.setFocusable(false);
            edtCorAdd.setEnabled(false);
            edtCorAdd.setFocusable(false);
        }

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

                    edtAlturaAdd.setText("");
                    edtLarguraAdd.setText("");
                    edtPesoAdd.setText("");
                    edtCorAdd.setText("");
                    edtValorAdd.setText("");
                }else{
                    Toast.makeText(AddProduto.this,"Preencher todos os campos.",Toast.LENGTH_SHORT).show();
                }

                closeKeyboard();
            }
        });


    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private boolean verificaPreenchimento() {
        return !edtAlturaAdd.getText().toString().equals("") &&
                !edtLarguraAdd.getText().toString().equals("") &&
                !edtPesoAdd.getText().toString().equals("") &&
                !edtCorAdd.getText().toString().equals("") &&
                !edtValorAdd.getText().toString().equals("");
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
