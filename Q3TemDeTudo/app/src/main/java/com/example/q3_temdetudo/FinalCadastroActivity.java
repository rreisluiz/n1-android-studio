package com.example.q3_temdetudo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FinalCadastroActivity extends AppCompatActivity {

    Intent previousIntent;
    TextView txtMensagemFinal;
    Button btnVoltarMenuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_final_cadastro);

        previousIntent = getIntent();
        txtMensagemFinal = findViewById(R.id.txtMensagemFinal);
        btnVoltarMenuPrincipal = findViewById(R.id.btnVoltarMenuPrincipal);

        String nameUsuario = previousIntent.getStringExtra("NAME_USUARIO");
        txtMensagemFinal.setText("Seja Bem vindo(a), \n" + nameUsuario + "!");

        btnVoltarMenuPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(FinalCadastroActivity.this, MainActivity.class);
            finish();
            startActivity(intent);
        });
    }
}