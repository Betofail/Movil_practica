package com.example.beto.movil_practica;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Movil_practica extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
