package com.example.q2_salario;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.text.NumberFormat;
import java.util.Locale;

public class BrazilCurrencyTextWatcher implements TextWatcher {
    private final EditText editText;
    private String current = "";
    private final Locale brazilLocale = new Locale("pt", "BR");
    private final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(brazilLocale);

    public BrazilCurrencyTextWatcher(EditText editText) {
        this.editText = editText;
        currencyFormat.setMaximumFractionDigits(2);
        currencyFormat.setMinimumFractionDigits(2);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        if (!s.toString().equals(current)) {
            editText.removeTextChangedListener(this);

            String cleanString = s.toString().replaceAll("[^\\d]", "");

            if (!cleanString.isEmpty()) {
                try {
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = currencyFormat.format(parsed / 100);

                    current = formatted;
                    editText.setText(formatted);
                    editText.setSelection(formatted.length());
                } catch (NumberFormatException e) {
                    current = "";
                    editText.setText("");
                }
            } else {
                current = "";
                editText.setText("");
            }

            editText.addTextChangedListener(this);
        }
    }

    public float getRawValue() {
        String formattedValue = editText.getText().toString();
        try {
            // Remove currency symbol and thousands separators
            String cleanString = formattedValue.replaceAll("[R\\$\\.]", "")
                    .replace(",", ".");
            return Float.parseFloat(cleanString);
        } catch (NumberFormatException e) {
            return 0.0f; // or handle error as needed
        }
    }
}