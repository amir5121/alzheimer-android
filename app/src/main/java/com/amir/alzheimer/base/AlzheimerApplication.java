package com.amir.alzheimer.base;

import android.app.Application;

import com.amir.alzheimer.infrastructure.Database;

public class AlzheimerApplication extends Application {

    private Database database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = new Database(this);
    }

    public Database getDatabase() {
        return database;
    }
}
