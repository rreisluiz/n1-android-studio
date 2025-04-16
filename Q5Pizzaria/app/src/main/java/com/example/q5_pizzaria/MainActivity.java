package com.example.q5_pizzaria;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button btnPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnPedido = findViewById(R.id.btnPedido);

        btnPedido.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormularioSaborPizza.class);
            startActivity(intent);
        });
    }
}