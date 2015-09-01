package com.slickmobile.simpledatausage.injection.component;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;

import com.slickmobile.simpledatausage.injection.ActivityScope;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;
import com.slickmobile.simpledatausage.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    Activity activity();
}
