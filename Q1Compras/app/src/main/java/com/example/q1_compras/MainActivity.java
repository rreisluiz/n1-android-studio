package com.example.q1_compras;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RadioGroup rbgProducts;
    Button btnCalcularTotal;
    TextView txtValorTotal;
    ArrayList<CheckBox> listCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        rbgProducts = findViewById(R.id.rbgProducts);
        btnCalcularTotal = findViewById(R.id.btnCalcularTotal);
        txtValorTotal = findViewById(R.id.txtValorTotal);

        // Tive essa ideia e quis tentar aplicar para mudar o preço dinamicamente apenas pelo products.xml,
        // automatizando a concatenação e conversão para moeda local :)
        listCheckBox = getCheckBoxChildrens();

        // Carregar titulos dos produtos
        listCheckBox.forEach((cb) -> {
            if (cb != null && cb.getTag() != null) {
                String productTag = cb.getTag().toString();
                float price = getFractionByName(productTag + "_price");
                cb.setText(getStringByName(productTag + "_title", price));
            }
        });

        btnCalcularTotal.setOnClickListener(v -> {
            float totalSum = 0f;

            for(CompoundButton cb : listCheckBox) {
                if (cb.isChecked()) {
                    String productTag = cb.getTag().toString();
                    float price = getFractionByName(productTag + "_price");
                    totalSum += price;
                }
            }

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            String formattedTotalSum = currencyFormat.format(totalSum);

            txtValorTotal.setText(getString(R.string.txt_valor_total, formattedTotalSum));
            txtValorTotal.setVisibility(View.VISIBLE);
        });
    }

    private ArrayList<CheckBox> getCheckBoxChildrens() {
        ArrayList<CheckBox> list = new ArrayList<>();

        for (int i = 0; i < rbgProducts.getChildCount(); i++) {
            View view = rbgProducts.getChildAt(i);
            CheckBox cb = view instanceof CheckBox ? (CheckBox) view : null;

            if (cb != null) list.add(cb);
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
    public String getStringByName(String resourceName, float priceValue) {
        int resourceId = getResources().getIdentifier(resourceName, "string", getPackageName());

        if (resourceId == 0) {
            return null;
        }

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        String formattedPrice = currencyFormat.format(priceValue);

        // Pass priceValue as a vararg
        return getString(resourceId, formattedPrice);
    }
}