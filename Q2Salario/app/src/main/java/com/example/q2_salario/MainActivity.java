package com.example.q2_salario;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText etValorSalario;
    RadioGroup rbgAumento;
    Button btnCalcularAumento;
    TextView txtValorAumento;
    ArrayList<RadioButton> listRadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        etValorSalario = findViewById(R.id.etValorSalario);
        etValorSalario.addTextChangedListener(new BrazilCurrencyTextWatcher(etValorSalario));

        rbgAumento = findViewById(R.id.rbgAumento);
        btnCalcularAumento = findViewById(R.id.btnCalcularAumento);
        txtValorAumento = findViewById(R.id.txtValorAumento);

        listRadioButton = getRadioButtonChildrens();

        listRadioButton.forEach(rb -> {
            rb.setText(String.format("%d%%", getIntegerByName(rb.getTag().toString())));
        });

        btnCalcularAumento.setOnClickListener(v -> {
            listRadioButton.stream().filter(RadioButton::isChecked).findFirst().ifPresent(rb -> {
                float salario = Float.parseFloat(etValorSalario.getText().toString().replaceAll("[R\\$\\.]", "")
                        .replace(",", "."));
                float raise = (float) getIntegerByName(rb.getTag().toString()) / 100;
                float salarioAumento = salario + (salario * raise);

                NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                String formattedSalarioAumento = currencyFormat.format(salarioAumento);

                txtValorAumento.setText("O Valor do salário com aumento: \n" + formattedSalarioAumento);
                txtValorAumento.setVisibility(View.VISIBLE);
            });
        });
    }

    private ArrayList<RadioButton> getRadioButtonChildrens() {
        ArrayList<RadioButton> list = new ArrayList<>();

        for (int i = 0; i < rbgAumento.getChildCount(); i++) {
            View view = rbgAumento.getChildAt(i);
            RadioButton rb = view instanceof RadioButton ? (RadioButton) view : null;

            if (rb != null) list.add(rb);
        }
        return list;
    }

    @SuppressLint("DiscouragedApi")
    public int getIntegerByName(String resourceName) {
        int resourceId = getResources().getIdentifier(resourceName, "integer", getPackageName());

        if (resourceId == 0) {
            return 0;
        }

        // Use 1, 1 to get the actual fraction value as defined
        return getResources().getInteger(resourceId);
    }
}