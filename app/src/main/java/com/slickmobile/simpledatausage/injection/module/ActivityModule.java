package com.slickmobile.simpledatausage.injection.module;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;
import android.view.LayoutInflater;

import com.slickmobile.simpledatausage.injection.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    private final Activity activity;

    public ActivityModule (Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivityContext() {
        return this.activity;
    }

    @Provides
    @ActivityScope
    public LayoutInflater provideLayoutInflater() {
        return LayoutInflater.from(activity);
    }

}