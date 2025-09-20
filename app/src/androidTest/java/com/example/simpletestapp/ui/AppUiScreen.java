package com.example.simpletestapp.ui;

public interface AppUiScreen {
    AppUiScreen assertScreen();

    @SuppressWarnings("unchecked")
    default <T extends AppUiScreen> T assertScreenAndRunScans() {
        assertScreen();
        // placeholder for accessibility scan / Firebase log
        System.out.println("Scan performed on: " + this.getClass().getSimpleName());
        return (T) this;
    }
}
