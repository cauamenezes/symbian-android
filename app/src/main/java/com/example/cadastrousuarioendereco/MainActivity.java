package com.example.cadastrousuarioendereco;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;

public class MainActivity extends AppCompatActivity {

    private EditText etNome;
    private EditText etSobrenome;
    private EditText etLogin;
    private EditText etSenha;
    private Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNome = findViewById(R.id.et_nome_usuario);
        etSobrenome = findViewById(R.id.et_sobrenome_usuario);
        etLogin = findViewById(R.id.et_login_usuario);
        etSenha = findViewById(R.id.et_senha_usuario);
        btnCadastrarUsuario = findViewById(R.id.btn_cadastrar_usuario);

        btnCadastrarUsuario.setOnClickListener(view -> {

            if (!validate()) {

                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Cadastro de Usuário")
                    .setMessage("Você está cadastrando um novo usuário")
                    .setPositiveButton("Salvar", (dialog1, which) -> {
                        String nome = etNome.getText().toString();
                        String sobrenome = etSobrenome.getText().toString();
                        String login = etLogin.getText().toString();
                        String senha = etSenha.getText().toString();

                        int idUsuario =
                                SQLHelper.getInstance(this).addUser(nome, sobrenome, login, senha);

                        if (idUsuario > 0) {

                            Toast.makeText(this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();

                            Intent intent  = new Intent(MainActivity.this, CadastroEnderecoUsuario.class);
                            intent.putExtra("idUsuario", idUsuario);
                            startActivity(intent);

                        } else {
                            Toast.makeText(this, "Ocorreu um erro ao realizar o cadastro :(", Toast.LENGTH_SHORT).show();
                        }


                    })
                    .setNegativeButton("Cancelar", (dialog1, which) -> {
                    }).create();

            dialog.show();
        });

    }

    private boolean validate() {

        return (
                !etNome.getText().toString().isEmpty() &&
                !etSobrenome.getText().toString().isEmpty() &&
                !etLogin.getText().toString().isEmpty() &&
                !etSenha.getText().toString().isEmpty()
        );
    }


}
