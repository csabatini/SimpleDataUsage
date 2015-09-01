package com.slickmobile.simpledatausage;

import android.app.Application;

import com.slickmobile.simpledatausage.injection.component.AppComponent;
import com.slickmobile.simpledatausage.injection.component.DaggerAppComponent;
import com.slickmobile.simpledatausage.injection.module.AppModule;
import com.slickmobile.simpledatausage.injection.module.DbModule;
import com.squareup.leakcanary.LeakCanary;

import net.danlew.android.joda.JodaTimeAndroid;

import timber.log.Timber;

public class App extends Application {

    private AppComponent component;

    public void onCreate() {
        super.onCreate();
        setupGraph();
        JodaTimeAndroid.init(this);
        LeakCanary.install(this);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    private void setupGraph() {
        component = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .dbModule(new DbModule())
                .build();
    }

    public AppComponent getComponent() {
        return component;
    }

}
