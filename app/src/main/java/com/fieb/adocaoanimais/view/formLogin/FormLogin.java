package com.fieb.adocaoanimais.view.formLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.fieb.adocaoanimais.R;
import com.fieb.adocaoanimais.databinding.ActivityFormLoginBinding;
import com.fieb.adocaoanimais.view.formCadastro.FormCadastro;
import com.fieb.adocaoanimais.view.telaPrincipal.TelaPrincipal;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class FormLogin extends AppCompatActivity {
    private ActivityFormLoginBinding binding;
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.txtTelaCadastro.setOnClickListener(view -> {
                    Intent intent = new Intent(this, FormCadastro.class);
                    startActivity(intent);
                }
        );

        binding.btnEntrar.setOnClickListener(view -> {
            String email = binding.editEmail.getText().toString();
            String senha = binding.editSenha.getText().toString();
            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar snackBar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT);
                snackBar.setBackgroundTint(Color.RED);
                snackBar.show();
            } else {
                auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(autenticacao -> {
                    if (autenticacao.isSuccessful()) {
                        navegarTelaPrincipal();
                    }
                }).addOnFailureListener(exception ->{
                    Snackbar snackBar = Snackbar.make(view, "Erro ao fazer o login do usuário!", Snackbar.LENGTH_SHORT);
                    snackBar.setBackgroundTint(Color.RED);
                    snackBar.show();
                });
            }
        });
    }

    private void navegarTelaPrincipal() {
        Intent intent = new Intent(this, TelaPrincipal.class);
        startActivity(intent);
        finish();
    }
}