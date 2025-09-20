package com.example.simpletestapp.ui;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import com.example.simpletestapp.R;

public class HomeScreen implements AppUiScreen {

    @Override
    public AppUiScreen assertScreen() {
        onView(withId(R.id.tvWelcome)).check(matches(withText("Welcome Home!")));
        return this;
    }
}
