package com.example.simpletestapp.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.simpletestapp.R;

public class LoginScreen implements AppUiScreen {

    @Override
    public AppUiScreen assertScreen() {
        onView(withId(R.id.btnLogin)).check(matches(withText("Login")));
        return this;
    }

    public LoginScreen enterUsername(String username) {
        onView(withId(R.id.etUsername)).perform(typeText(username));
        return this;
    }

    public LoginScreen enterPassword(String password) {
        onView(withId(R.id.etPassword)).perform(typeText(password));
        return this;
    }

    public HomeScreen clickLogin() {
        onView(withId(R.id.btnLogin)).perform(click());
        return new HomeScreen();
    }


}
