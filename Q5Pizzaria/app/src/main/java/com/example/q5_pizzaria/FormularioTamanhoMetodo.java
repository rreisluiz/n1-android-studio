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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class FormularioTamanhoMetodo extends AppCompatActivity {

    EditText etNomeCliente;
    RadioGroup rbgTamanhoPizza;
    RadioGroup rbgMetodoPagamento;
    ArrayList<RadioGroup> listRadioGroups;
    Button btnFinalizarPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_tamanho_metodo);

        btnFinalizarPedido = findViewById(R.id.btnFinalizarPedido);

        listRadioGroups = new ArrayList<>();

        rbgTamanhoPizza = findViewById(R.id.rbgTamanhoPizza);
        listRadioGroups.add(rbgTamanhoPizza);

        rbgMetodoPagamento = findViewById(R.id.rbgMetodoPagamento);
        listRadioGroups.add(rbgMetodoPagamento);

        listRadioGroups.forEach((radioGroup -> {
            getRadioButtonChildren(radioGroup).forEach((rb) -> {
                if (rb != null && rb.getTag() != null) {
                    String foodTag = rb.getTag().toString();
                    float price = getFractionByName(foodTag + "_price");
                    String foodName = getStringByName(foodTag, "", -1f);
                    rb.setText(getStringByName(foodTag + "_title", foodName, price));
                }
            });
        }));

        btnFinalizarPedido.setOnClickListener(v -> {
            Intent intent = new Intent(FormularioTamanhoMetodo.this, ResumoPedido.class);
            Intent previousIntent = getIntent();
            intent.putExtras(Objects.requireNonNull(previousIntent.getExtras()));

            AtomicBoolean ok = new AtomicBoolean(true);

            listRadioGroups.forEach((radioGroup -> {
                ArrayList<RadioButton> listRB = getRadioButtonChildren(radioGroup);

                Toast emptyMessage = Toast.makeText(getApplicationContext(), "Campos não podem ser vazios", Toast.LENGTH_SHORT);

                if (listRB.stream().noneMatch(CompoundButton::isChecked)) {
                    emptyMessage.show();
                    ok.set(false);
                    return;
                }

                RadioButton rb = getRadioButtonChildren(radioGroup).stream().filter(CompoundButton::isChecked).findFirst().get();
                String pizzaTag = rb.getTag().toString();


                if (pizzaTag.contains("tamanho")) {
                    Bundle bundleTamanho = new Bundle();
                    float price = getFractionByName(pizzaTag + "_price");
                    bundleTamanho.putFloat("TAMANHO_PRICE", price);
                    bundleTamanho.putString("TAMANHO", getStringByName(pizzaTag, "", 0f));
                    intent.putExtras(bundleTamanho);
                }

                if (pizzaTag.contains("metodo")) {
                    Bundle bundleMetodo = new Bundle();
                    bundleMetodo.putString("METODO_PAGAMENTO", getStringByName(pizzaTag + "_title", "", 0f));
                    intent.putExtras(bundleMetodo);
                }

            }));

            if (!ok.get()) {
                return;
            }

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