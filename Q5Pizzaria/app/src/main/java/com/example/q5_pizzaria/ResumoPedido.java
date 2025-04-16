package com.example.q5_pizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.Locale;

public class ResumoPedido extends AppCompatActivity {

    Intent previousIntent;
    TextView txtNome;
    TextView txtSaborPizza;
    TextView txtTamanhoPizza;
    TextView txtMetodoPagamento;
    TextView txtValorTotal;
    Button btnVoltarMenuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_resumo_pedido);

        previousIntent = getIntent();
        txtSaborPizza = findViewById(R.id.txtSaborPizza);
        txtTamanhoPizza = findViewById(R.id.txtTamanhoPizza);
        txtMetodoPagamento = findViewById(R.id.txtMetodoPagamento);
        txtValorTotal = findViewById(R.id.txtValorTotal);
        btnVoltarMenuPrincipal = findViewById(R.id.btnVoltarMenuPrincipal);

        txtSaborPizza.setText("Sabor da Pizza: " + previousIntent.getStringExtra("SABOR_PIZZA"));
        txtTamanhoPizza.setText("Tamanho: " + previousIntent.getStringExtra("TAMANHO"));
        txtMetodoPagamento.setText("Método de Pagamento: " + previousIntent.getStringExtra("METODO_PAGAMENTO"));

        float priceSaborPizza = previousIntent.getFloatExtra("SABOR_PIZZA_PRICE", 0f);
        float priceTamanhoPizza = previousIntent.getFloatExtra("TAMANHO_PRICE", 0f);

        float totalValue = priceSaborPizza + priceTamanhoPizza;
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