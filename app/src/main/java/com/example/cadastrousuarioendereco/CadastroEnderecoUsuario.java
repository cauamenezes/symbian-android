package com.example.cadastrousuarioendereco;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import database.SQLHelper;

public class CadastroEnderecoUsuario extends AppCompatActivity {

    private EditText etCep;
    private EditText etNumero;
    private EditText etComplemento;
    private Button btnCadastrarEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco_usuario);

        etCep = findViewById(R.id.et_cep_usuario);
        etNumero = findViewById(R.id.et_numero_endereco);
        etComplemento = findViewById(R.id.et_complemento_endereco);
        btnCadastrarEndereco = findViewById(R.id.btn_cadastrar_endereco);

        btnCadastrarEndereco.setOnClickListener(view -> {

            if (!validate()) {

                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                return;
            }

            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("Cadastro de Endereço")
                    .setMessage("Você está cadastrando um novo endereço")
                    .setPositiveButton("Salvar", (dialog1, which) -> {
                        String cep = etCep.getText().toString();
                        String numero = etNumero.getText().toString();
                        String complemento = etComplemento.getText().toString();

                        getIntent().hasExtra("idUsuario");
                        Bundle extras = getIntent().getExtras();
                        int idUsuario = extras.getInt("idUsuario");

                        boolean cadastroEndereco =
                                SQLHelper.getInstance(CadastroEnderecoUsuario.this).addAdress(idUsuario, cep, numero, complemento);

                        if(cadastroEndereco){

                            Toast.makeText(this, "Cadastro efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(this, "Ocorreu um erro ao realizar o cadastro do endereço :(", Toast.LENGTH_SHORT).show();
                        }


                    })
                    .setNegativeButton("Cancelar", (dialog1, which) -> {
                    }).create();

            dialog.show();
        });

    }

    private boolean validate() {

        return (
                !etCep.getText().toString().isEmpty() &&
                !etNumero.getText().toString().isEmpty() &&
                !etComplemento.getText().toString().isEmpty()
        );
    }
}