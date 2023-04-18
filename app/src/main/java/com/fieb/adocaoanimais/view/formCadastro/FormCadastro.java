package com.fieb.adocaoanimais.view.formCadastro;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.fieb.adocaoanimais.databinding.ActivityFormCadastroBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class FormCadastro extends AppCompatActivity {
    private ActivityFormCadastroBinding binding;
    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFormCadastroBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        binding.btnCadastrar.setOnClickListener(view -> {

            String email = binding.editEmail.getText().toString();
            String senha = binding.editSenha.getText().toString();
            if (email.isEmpty() || senha.isEmpty()) {
                Snackbar snackBar = Snackbar.make(view, "Preencha todos os campos!", Snackbar.LENGTH_SHORT);
                snackBar.setBackgroundTint(Color.RED);
                snackBar.show();
            } else {
                Task<AuthResult> authResultTask = auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(cadastro -> {

                    if (cadastro.isSuccessful()) {
                        Snackbar snackBar = Snackbar.make(view, "Sucesso ao cadastrar usuario !", Snackbar.LENGTH_SHORT);
                        snackBar.setBackgroundTint(Color.BLUE);
                        snackBar.show();
                        binding.editEmail.setText("");
                        binding.editSenha.setText("");
                    }
                }).addOnFailureListener(exception -> {
                    String mensagemErro;
                    if (exception instanceof FirebaseAuthWeakPasswordException) {
                        mensagemErro = "Digite senha no mínimo 6 caracteres";
                    } else if (exception instanceof FirebaseAuthInvalidCredentialsException) {
                        mensagemErro = "Digite um email válido";
                    } else if (exception instanceof FirebaseAuthUserCollisionException) {
                        mensagemErro = "Esta conta ja esta em uso";
                    } else if (exception instanceof FirebaseNetworkException) {
                        mensagemErro = "Sem conexão com a internet";
                    } else {
                        mensagemErro = "Erro desconhecido";
                    }
                    // faça algo com a mensagem de erro, como exibi-la em uma caixa de diálogo
                    Snackbar snackBar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT);
                    snackBar.setBackgroundTint(Color.RED);
                    snackBar.show();
                });
            }

        });
    }
}