package com.example.lab7penzev;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    private EditText fullName, login, email, phone, password, repeatPassword, birthDate;
    private Spinner productSpinner;
    private CheckBox agreeCheckBox;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        fullName = findViewById(R.id.fullName);
        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeatPassword);
        birthDate = findViewById(R.id.birthDate);
        productSpinner = findViewById(R.id.productSpinner);
        agreeCheckBox = findViewById(R.id.agreeCheckBox);
        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    Toast.makeText(RegistrationActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateForm() {
        boolean isValid = true;

        // Валидация ФИО
        String fullNameText = fullName.getText().toString();
        if (!Pattern.matches("[а-яА-Я\\s-]+", fullNameText)) {
            fullName.setError("ФИО должно содержать только кириллические буквы, дефис и пробелы");
            isValid = false;
        }

        // Валидация логина
        String loginText = login.getText().toString();
        if (!Pattern.matches("[a-zA-Z]+", loginText)) {
            login.setError("Логин должен содержать только латинские буквы");
            isValid = false;
        }

        // Валидация email
        String emailText = email.getText().toString();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.setError("Введите корректный email");
            isValid = false;
        }

        // Валидация номера телефона
        String phoneText = phone.getText().toString();
        if (!Pattern.matches("^\\+[0-9]{10,15}$", phoneText)) {
            phone.setError("Введите номер телефона в международном формате");
            isValid = false;
        }

        // Валидация пароля
        String passwordText = password.getText().toString();
        String repeatPasswordText = repeatPassword.getText().toString();
        if (!passwordText.equals(repeatPasswordText)) {
            repeatPassword.setError("Пароли не совпадают");
            isValid = false;
        }

        // Валидация даты рождения
        String birthDateText = birthDate.getText().toString();
        try {
            int year = Integer.parseInt(birthDateText.split("-")[0]);
            if (year < 1900) {
                birthDate.setError("Дата рождения должна быть не ранее 1900 года");
                isValid = false;
            }
        } catch (Exception e) {
            birthDate.setError("Введите корректную дату");
            isValid = false;
        }

        // Валидация согласия на обработку персональных данных
        if (!agreeCheckBox.isChecked()) {
            Toast.makeText(this, "Необходимо согласие на обработку персональных данных", Toast.LENGTH_SHORT).show();
            isValid = false;
        }

        if (!isValid) {
            Log.e("RegistrationError", "Форма содержит ошибки");
        }

        return isValid;
    }
}