package com.example.q4_lanchefacil;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.NumberFormat;
import java.util.Locale;

public class ResumoPedido extends AppCompatActivity {

    Intent previousIntent;
    TextView txtNome;
    TextView txtLanche;
    TextView txtAcompanhamento;
    TextView txtBebida;
    TextView txtValorTotal;
    Button btnVoltarMenuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumo_pedido);

        previousIntent = getIntent();
        txtNome = findViewById(R.id.txtNome);
        txtLanche = findViewById(R.id.txtLanche);
        txtAcompanhamento = findViewById(R.id.txtAcompanhamento);
        txtBebida = findViewById(R.id.txtBebida);
        txtValorTotal = findViewById(R.id.txtValorTotal);
        btnVoltarMenuPrincipal = findViewById(R.id.btnVoltarMenuPrincipal);

        txtNome.setText("Nome do Cliente: " + previousIntent.getStringExtra("NOME_CLIENTE"));
        txtLanche.setText("Lanche: " + previousIntent.getStringExtra("LANCHE"));
        txtAcompanhamento.setText("Acompanhamento: " + previousIntent.getStringExtra("ACOMPANHAMENTO"));
        txtBebida.setText("Bebida: " + previousIntent.getStringExtra("BEBIDA"));

        float priceLanche = previousIntent.getFloatExtra("LANCHE_PRICE", 0f);
        float priceAcompanhamento = previousIntent.getFloatExtra("ACOMPANHAMENTO_PRICE", 0f);
        float priceBebida = previousIntent.getFloatExtra("BEBIDA_PRICE", 0f);

        float totalValue = priceLanche + priceAcompanhamento + priceBebida;
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formattedPrice = currencyFormat.format(totalValue);

        txtValorTotal.setText("Valor total: " + formattedPrice);

        btnVoltarMenuPrincipal.setOnClickListener(v -> {
            Intent intent = new Intent(ResumoPedido.this, MainActivity.class);
            finish();
            startActivity(intent);
        });
    }
}