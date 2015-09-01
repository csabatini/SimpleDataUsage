package com.slickmobile.simpledatausage.injection.module;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;

import com.slickmobile.simpledatausage.App;
import com.slickmobile.simpledatausage.rx.AppSchedulers;
import com.slickmobile.simpledatausage.util.ByteFormatter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideAppContext(){
        return this.app;
    }

    @Provides
    @Singleton
    public AppSchedulers provideSchedulers() {
        return AppSchedulers.newLiveInstance();
    }

    @Provides
    @Singleton
    public AlarmManager provideAlarmManager() {
        return (AlarmManager) app.getSystemService(Context.ALARM_SERVICE);
    }

    @Provides
    @Singleton
    public NotificationManager provideNotificationManager() {
        return (NotificationManager) app.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Provides
    @Singleton
    public ByteFormatter provideByteFormatter() {
        return new ByteFormatter();
    }
}
