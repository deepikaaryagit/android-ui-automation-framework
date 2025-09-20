package com.example.simpletestapp;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class UiAutomatorTest {

    private static final String PACKAGE_NAME = "com.example.simpletestapp";
    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    @Test
    public void loginWithUiAutomator() {
        // 1. Launch app
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE_NAME);
        assertNotNull("Launch intent is null", intent);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // start fresh
        context.startActivity(intent);

        // 2. Wait for app
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), 5000);

        // 3. Find username field and type
        UiObject2 username = device.wait(Until.findObject(By.res(PACKAGE_NAME, "etUsername")), 3000);
        assertNotNull("Username field not found", username);
        username.setText("test");

        // 4. Find password field and type
        UiObject2 password = device.wait(Until.findObject(By.res(PACKAGE_NAME, "etPassword")), 3000);
        assertNotNull("Password field not found", password);
        password.setText("1234");

        // 5. Press login button
        UiObject2 loginButton = device.wait(Until.findObject(By.res(PACKAGE_NAME, "btnLogin")), 3000);
        assertNotNull("Login button not found", loginButton);
        loginButton.click();

        // 6. Verify "Welcome Home!" is visible
        UiObject2 welcome = device.wait(Until.findObject(By.res(PACKAGE_NAME, "tvWelcome")), 5000);
        assertNotNull("Welcome text not found", welcome);
        assertTrue("Wrong text!", welcome.getText().equals("Welcome Home!"));
    }
}
