package com.example.simpletestapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

@RunWith(AndroidJUnit4.class)
public class LoginUiTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void login_showsWelcomeMessage() {
        // type username and password
        onView(withId(R.id.etUsername)).perform(typeText("test"));
        onView(withId(R.id.etPassword)).perform(typeText("1234"));
        // click login
        onView(withId(R.id.btnLogin)).perform(click());
        // check next screen text
        onView(withId(R.id.tvWelcome)).check(matches(withText("Welcome Home!")));
    }
}
