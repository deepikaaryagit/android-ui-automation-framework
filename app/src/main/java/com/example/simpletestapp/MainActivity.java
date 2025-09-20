package com.example.simpletestapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase instances (safe because MyApplication initializes FirebaseApp)
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics.getInstance().log("MainActivity started");

        // UI references
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        // Login button click
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etUsername.getText().toString();
                String pass = etPassword.getText().toString();

                if (user.equals("test") && pass.equals("1234")) {
                    // Log successful login event
                    Bundle successBundle = new Bundle();
                    successBundle.putString("username", user);
                    mFirebaseAnalytics.logEvent("login_success", successBundle);

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    // Log failed login event
                    Bundle failBundle = new Bundle();
                    failBundle.putString("username", user);
                    mFirebaseAnalytics.logEvent("login_failed", failBundle);

                    // Record a non-fatal exception in Crashlytics
                    FirebaseCrashlytics.getInstance().recordException(
                            new Exception("Login failed for user: " + user));
                }
            }
        });
    }
}
