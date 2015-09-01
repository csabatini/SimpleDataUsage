package com.slickmobile.simpledatausage.injection.component;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.content.Context;

import com.slickmobile.simpledatausage.injection.module.AppModule;
import com.slickmobile.simpledatausage.injection.module.DbModule;
import com.slickmobile.simpledatausage.rx.AppSchedulers;
import com.slickmobile.simpledatausage.service.CollectUsageService;
import com.slickmobile.simpledatausage.ui.MainActivity;
import com.slickmobile.simpledatausage.ui.SummaryFragment;
import com.slickmobile.simpledatausage.util.ByteFormatter;
import com.squareup.sqlbrite.BriteDatabase;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DbModule.class})
public interface AppComponent {
    void inject(MainActivity activity);
    void inject(CollectUsageService service);
    void inject(SummaryFragment fragment);
    Context context();
    AppSchedulers schedulers();
    BriteDatabase database();
    AlarmManager alarmManager();
    NotificationManager notificationManager();
    ByteFormatter formatter();
}
