package com.example.jeremy.broadcasttestapp.ui.activities;

import android.app.Application;

public class PizzaApplication extends Application {

    private static PizzaApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static synchronized PizzaApplication getInstance() {
        return instance;
    }
}
