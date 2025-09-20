package com.example.simpletestapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.simpletestapp.ui.LoginScreen;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginFlowTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void loginFlow_works() {
        new LoginScreen()
                .enterUsername("test")
                .enterPassword("1234")
                .clickLogin()
                .assertScreenAndRunScans();
    }
}
