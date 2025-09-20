package com.example.simpletestapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Firebase instances
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics.getInstance().log("HomeActivity started");

        // Log Firebase Analytics event
        Bundle bundle = new Bundle();
        bundle.putString("screen", "home");
        mFirebaseAnalytics.logEvent("entered_home", bundle);

        // Example: show welcome text (already in your layout)
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome Home!");

        // Optional: add a click that forces a test crash
        tvWelcome.setOnClickListener(v -> {
            // Non-fatal exception
            FirebaseCrashlytics.getInstance().recordException(
                    new Exception("User tapped Welcome Home text"));

            // Uncomment below to force crash
            // throw new RuntimeException("Test Crash from HomeActivity");
        });
    }
}
