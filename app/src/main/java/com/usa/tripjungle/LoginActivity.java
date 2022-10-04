package com.usa.tripjungle;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    EditText user, password;
    TextView loginBtn, txtEmailValidation, txtPassValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Objects.requireNonNull(getSupportActionBar()).hide();

        user = findViewById(R.id.login_user);
        password = findViewById(R.id.login_password);
        txtEmailValidation = findViewById(R.id.txtEmailValidation);
        txtPassValidation = findViewById(R.id.txtPassValidation);

        user.setText("");
        password.setText("");
        loginBtn = findViewById(R.id.login_login);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        loginBtn.setOnClickListener(v -> {
            String email = user.getText().toString().trim();
            String pass = password.getText().toString().trim();
            if (!email.matches(emailPattern)) {
                txtEmailValidation.setTextColor(Color.rgb(255, 0, 0));
                txtEmailValidation.setText("Dirección de correo electrónico no válida.");
                loginBtn.setBackgroundResource(R.drawable.botton_border);
                loginBtn.setEnabled(false);
            }
            if (pass.trim().length() <= 0) {
                txtPassValidation.setText("Por favor, introduzca su contraseña.");
                loginBtn.setEnabled(false);
            }
            if (email.matches(emailPattern) && pass.length() > 0 && email.equals("trip@gmail.com") && pass.equals("trip")) {
                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        user.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String email = user.getText().toString().trim();
                if (email.length() > 0)
                {
                    if (password.getText().toString().trim().length() > 0) {
                        txtPassValidation.setText("");
                        loginBtn.setBackgroundResource(R.drawable.botton_border);
                        loginBtn.setEnabled(true);
                    }
                    txtEmailValidation.setTextColor(Color.rgb(0, 255, 0));
                    txtEmailValidation.setText("");
                } else {
                    loginBtn.setBackgroundResource(R.drawable.botton_border_disable);
                    loginBtn.setEnabled(false);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                String pass = password.getText().toString().trim();
                if (pass.length() > 0)
                {
                    txtPassValidation.setText("");
                    if (user.getText().toString().trim().length() > 0) {
                        txtEmailValidation.setText("");
                        loginBtn.setBackgroundResource(R.drawable.botton_border);
                        loginBtn.setEnabled(true);
                    }
                } else {
                    loginBtn.setBackgroundResource(R.drawable.botton_border_disable);
                    loginBtn.setEnabled(false);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // other stuffs
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // other stuffs
            }
        });

    }
}