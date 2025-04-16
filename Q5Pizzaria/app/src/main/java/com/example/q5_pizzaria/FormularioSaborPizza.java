package com.example.q5_pizzaria;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class FormularioSaborPizza extends AppCompatActivity {

    EditText etNomeCliente;
    RadioGroup rbgSaborPizza;
    ArrayList<RadioButton> listRadioButtons;
    Button btnProximaEtapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_sabor_pizza);

        btnProximaEtapa = findViewById(R.id.btnProximaEtapa);
        rbgSaborPizza = findViewById(R.id.rbgSaborPizza);

        listRadioButtons = getRadioButtonChildren(rbgSaborPizza);

        listRadioButtons.forEach((rb) -> {
            if (rb != null && rb.getTag() != null) {
                String saborPizzaTag = rb.getTag().toString();
                float price = getFractionByName(saborPizzaTag + "_price");
                String foodName = getStringByName(saborPizzaTag, "", -1f);
                rb.setText(getStringByName(saborPizzaTag + "_title", foodName, price));
            }
        });

        btnProximaEtapa.setOnClickListener(v -> {
            Intent intent = new Intent(FormularioSaborPizza.this, FormularioTamanhoMetodo.class);
            Toast emptyMessage = Toast.makeText(getApplicationContext(), "Campos não podem ser vazios", Toast.LENGTH_SHORT);

            if (listRadioButtons.stream().noneMatch(CompoundButton::isChecked)) {
                emptyMessage.show();
                return;
            }

            RadioButton rb = listRadioButtons.stream().filter(CompoundButton::isChecked).findFirst().get();
            String saborPizzaTag = rb.getTag().toString();

            Bundle bundleSaborPizza = new Bundle();
            float price = getFractionByName(saborPizzaTag + "_price");
            bundleSaborPizza.putFloat("SABOR_PIZZA_PRICE", price);
            bundleSaborPizza.putString("SABOR_PIZZA", getStringByName(saborPizzaTag, "", 0f));
            intent.putExtras(bundleSaborPizza);
            startActivity(intent);
        });
    }

    private ArrayList<RadioButton> getRadioButtonChildren(RadioGroup radioGroup) {
        ArrayList<RadioButton> list = new ArrayList<>();

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View view = radioGroup.getChildAt(i);
            RadioButton rb = view instanceof RadioButton ? (RadioButton) view : null;

            if (rb != null) list.add(rb);
        }
        return list;
    }

    @SuppressLint("DiscouragedApi")
    public float getFractionByName(String resourceName) {
        int resourceId = getResources().getIdentifier(resourceName, "fraction", getPackageName());

        if (resourceId == 0) {
            return 0f;
        }

        // Use 1, 1 to get the actual fraction value as defined
        return getResources().getFraction(resourceId, 100, 0);
    }

    @SuppressLint("DiscouragedApi")
    public String getStringByName(String resourceName, String foodName, float priceValue) {
        int resourceId = getResources().getIdentifier(resourceName, "string", getPackageName());

        if (resourceId == 0) {
            return null;
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formattedPrice = currencyFormat.format(priceValue);

        if (priceValue == -1f) {
            return getString(resourceId);
        }

        return getString(resourceId, foodName, formattedPrice);
    }
}