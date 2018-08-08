package com.example.muriloportugal.firebaseexemplo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.muriloportugal.firebaseexemplo.R;
import com.example.muriloportugal.firebaseexemplo.dao.ConfiguracaoFirebase;
import com.example.muriloportugal.firebaseexemplo.entidades.Usuarios;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText edtEamil;
    private EditText edtPassword;
    //private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Faz com que os dados sejam salvos para acesso offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        edtEamil = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        Button btnLogar = findViewById(R.id.btnLogar);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEamil.getText().toString().equals("") && !edtPassword.getText().toString().equals("")){
                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEamil.getText().toString() );
                    usuarios.setPassword(edtPassword.getText().toString());
                    validarLogin();
                }else{
                    Toast.makeText(MainActivity.this,"Informar email e senha",Toast.LENGTH_SHORT).show();
                }
            }
        });

        verificaUsuarioLogado();
    }

    private void verificaUsuarioLogado() {
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        if (autenticacao.getCurrentUser() != null){
            Intent intent = new Intent(MainActivity.this,Principal.class);
            startActivity(intent);
            finish();
        }
    }

    private void validarLogin() {
        autenticacao = ConfiguracaoFirebase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    callViewPrincipal();
                    Toast.makeText(MainActivity.this,"Login efetuado com sucesso!",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,"Usuário ou senha inválido",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void callViewPrincipal() {
        Intent intent = new Intent(MainActivity.this,Principal.class);
        startActivity(intent);
        finish();
    }
}
