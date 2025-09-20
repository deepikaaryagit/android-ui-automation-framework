package com.example.simpletestapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class HybridTest {

    private static final String PACKAGE_NAME = "com.example.simpletestapp";
    private FirebaseAnalytics mFirebaseAnalytics;
    private String runId; // unique per test execution

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void initFirebase() {
        Context context = ApplicationProvider.getApplicationContext();
        if (FirebaseApp.getApps(context).isEmpty()) {
            FirebaseApp.initializeApp(context);
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(context);
        FirebaseCrashlytics.getInstance().log("HybridTest starting...");

        // Generate unique run ID
        runId = UUID.randomUUID().toString();

        // --- Log test run start ---
        Bundle bundle = new Bundle();
        bundle.putString("test_name", "HybridTest");
        bundle.putString("run_id", runId);
        bundle.putLong("timestamp", System.currentTimeMillis());
        mFirebaseAnalytics.logEvent("test_run_start", bundle);
    }

    @Test
    public void espressoLogin_thenUiAutomatorHomeAndResume() {
        // --- Espresso: perform login ---
        onView(withId(R.id.etUsername)).perform(typeText("test"));
        onView(withId(R.id.etPassword)).perform(typeText("1234"));
        onView(withId(R.id.btnLogin)).perform(click());
        onView(withId(R.id.tvWelcome)).check(matches(withText("Welcome Home!")));
        FirebaseCrashlytics.getInstance().log("HybridTest: Login successful");

        // --- Switch to UI Automator ---
        UiDevice device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Dismiss "Save password" dialog if it appears
        UiObject2 savePasswordDialog = device.wait(
                Until.findObject(By.textContains("Save password")), 2000);
        if (savePasswordDialog != null) {
            UiObject2 notNowButton = device.findObject(By.text("Not now"));
            if (notNowButton != null) {
                notNowButton.click();
                FirebaseCrashlytics.getInstance().log("HybridTest: Dismissed save password popup");
            }
        }

        // --- Press home ---
        device.pressHome();

        // --- Relaunch the app ---
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        assertNotNull("Launch intent is null", intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        // --- Wait until either Welcome or Login is visible ---
        UiObject2 welcome = device.wait(
                Until.findObject(By.res(PACKAGE_NAME, "tvWelcome")), 5000);
        UiObject2 username = device.wait(
                Until.findObject(By.res(PACKAGE_NAME, "etUsername")), 5000);

        if (welcome != null) {
            FirebaseCrashlytics.getInstance().log("HybridTest: Relaunched into HomeActivity");
            assertTrue("Wrong text after relaunch!", welcome.getText().equals("Welcome Home!"));
        } else if (username != null) {
            FirebaseCrashlytics.getInstance().log("HybridTest: Relaunched into LoginActivity");
            assertNotNull("Username field should be visible after relaunch", username);
        } else {
            FirebaseCrashlytics.getInstance().recordException(
                    new Exception("HybridTest: Neither Welcome nor Username found after relaunch"));
            throw new AssertionError("Neither Welcome nor Username field found after relaunch");
        }
    }

    @After
    public void endTest() {
        // --- Log test run end ---
        Bundle bundle = new Bundle();
        bundle.putString("test_name", "HybridTest");
        bundle.putString("run_id", runId);
        bundle.putLong("timestamp", System.currentTimeMillis());
        mFirebaseAnalytics.logEvent("test_run_end", bundle);

        FirebaseCrashlytics.getInstance().log("HybridTest finished.");
    }
}
