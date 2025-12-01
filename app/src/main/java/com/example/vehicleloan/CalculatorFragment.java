package com.example.vehicleloan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class CalculatorFragment extends Fragment {

    private TextInputLayout tilPrice, tilDown, tilRate, tilYears;
    private TextInputEditText inputPrice, inputDown, inputRate, inputYears;
    private Button btnCalculate, btnReset;
    private TextView outputResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_calculator, container, false);

        // fragment enter animation
        Animation fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in);
        root.startAnimation(fadeIn);

        // Bind Views
        tilPrice = root.findViewById(R.id.til_price);
        tilDown = root.findViewById(R.id.til_down);
        tilRate = root.findViewById(R.id.til_rate);
        tilYears = root.findViewById(R.id.til_years);

        inputPrice = root.findViewById(R.id.input_price);
        inputDown = root.findViewById(R.id.input_down);
        inputRate = root.findViewById(R.id.input_rate);
        inputYears = root.findViewById(R.id.input_years);

        btnCalculate = root.findViewById(R.id.btn_calculate);
        btnReset = root.findViewById(R.id.btn_reset);
        outputResult = root.findViewById(R.id.output_result);

        // Info icon tooltips
        tilPrice.setEndIconOnClickListener(v ->
                Toast.makeText(getContext(), "Enter total vehicle price before down payment", Toast.LENGTH_LONG).show());
        tilDown.setEndIconOnClickListener(v ->
                Toast.makeText(getContext(), "Enter the initial down payment", Toast.LENGTH_LONG).show());
        tilRate.setEndIconOnClickListener(v ->
                Toast.makeText(getContext(), "Enter the annual interest rate (%)", Toast.LENGTH_LONG).show());
        tilYears.setEndIconOnClickListener(v ->
                Toast.makeText(getContext(), "Enter loan period in years", Toast.LENGTH_LONG).show());

        // Button listeners
        btnCalculate.setOnClickListener(v -> calculateLoan());
        btnReset.setOnClickListener(v -> resetFields());

        // Real-time validation
        addValidation(inputPrice, tilPrice, "Enter valid vehicle price");
        addValidation(inputDown, tilDown, "Enter valid down payment");
        addValidation(inputRate, tilRate, "Enter valid interest rate");
        addValidation(inputYears, tilYears, "Enter valid loan period");

        return root;
    }

    // Real-time validation
    private void addValidation(TextInputEditText input, TextInputLayout layout, String errorMessage) {
        input.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString().trim();
                if (!text.isEmpty() && !isNumeric(text)) {
                    layout.setError(errorMessage);
                    shakeView(input);
                } else {
                    layout.setError(null);
                }
            }
        });
    }

    private boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void calculateLoan() {
        // Clear previous errors
        tilPrice.setError(null);
        tilDown.setError(null);
        tilRate.setError(null);
        tilYears.setError(null);
        outputResult.setText("");

        String priceStr = inputPrice.getText().toString().trim();
        String downStr = inputDown.getText().toString().trim();
        String rateStr = inputRate.getText().toString().trim();
        String yearsStr = inputYears.getText().toString().trim();

        if (priceStr.isEmpty() || downStr.isEmpty() || rateStr.isEmpty() || yearsStr.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields correctly", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            double down = Double.parseDouble(downStr);
            double rate = Double.parseDouble(rateStr) / 100.0;
            int years = Integer.parseInt(yearsStr);

            if (down > price) {
                tilDown.setError("Down payment cannot exceed vehicle price");
                shakeView(inputDown);
                Toast.makeText(getContext(), "Down payment cannot exceed vehicle price", Toast.LENGTH_SHORT).show();
                return;
            }

            double loanAmount = price - down;
            double monthlyRate = rate / 12.0;
            int months = years * 12;

            double monthlyPayment = (loanAmount * monthlyRate) /
                    (1 - Math.pow(1 + monthlyRate, -months));

            double totalPayment = monthlyPayment * months;
            double totalInterest = totalPayment - loanAmount;

            outputResult.setText(String.format(
                    "Monthly Payment : RM %.2f\nTotal Interest : RM %.2f\nTotal Cost : RM %.2f",
                    monthlyPayment, totalInterest, totalPayment));

        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }

    private void resetFields() {
        inputPrice.setText("");
        inputDown.setText("");
        inputRate.setText("");
        inputYears.setText("");
        tilPrice.setError(null);
        tilDown.setError(null);
        tilRate.setError(null);
        tilYears.setError(null);
        outputResult.setText("");
    }

    private void shakeView(View view) {
        Animation shake = new TranslateAnimation(0, 10, 0, 0);
        shake.setDuration(300);
        shake.setInterpolator(new CycleInterpolator(3));
        view.startAnimation(shake);
    }
}