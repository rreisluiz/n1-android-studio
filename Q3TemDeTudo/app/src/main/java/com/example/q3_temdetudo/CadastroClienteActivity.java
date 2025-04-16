package com.example.q3_temdetudo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class CadastroClienteActivity extends AppCompatActivity {

    Button btnCadastrar;
    EditText etNomeUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastro_cliente);

        btnCadastrar = findViewById(R.id.btnVoltarMenuPrincipal);
        etNomeUsuario = findViewById(R.id.etNomeUsuario);

        btnCadastrar.setOnClickListener(v -> {
            String nomeUsuario = etNomeUsuario.getText().toString();

            if (nomeUsuario.isBlank()) {
                etNomeUsuario.requestFocus();
                Toast.makeText(getApplicationContext(), "Campo não pode ser vazio!", Toast.LENGTH_SHORT).show();
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("NAME_USUARIO", nomeUsuario.trim());
            Intent intent = new Intent(CadastroClienteActivity.this, FinalCadastroActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}