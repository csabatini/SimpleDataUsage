package com.slickmobile.simpledatausage.ui;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.injection.component.AppComponent;
import com.slickmobile.simpledatausage.injection.component.DaggerMainComponent;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;
import com.slickmobile.simpledatausage.injection.module.MainModule;
import com.slickmobile.simpledatausage.mvp.presenter.MainPresenter;
import com.slickmobile.simpledatausage.mvp.view.MainView;
import com.slickmobile.simpledatausage.receiver.AlarmReceiver;
import com.slickmobile.simpledatausage.util.ByteFormatter;

import javax.inject.Inject;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter presenter;
    @Inject
    SharedPreferences prefs;
    @Inject
    AlarmManager am;
    @Inject
    ByteFormatter bf;

    private final int NOTIFICATION_ID = 568331681;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.create();
        /*Fragment currentFragment =
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment == null) {
            addFragment(R.id.container, new AppListFragment());
        }*/
        setupService();
    }

    @Override
    public boolean getUserPlanChoice() {
        return prefs.getBoolean("planChoiceMade", false);
    }

    @Override
    public void showUserChoiceDialog() {
        //TODO: presenter call should be made dialog button clicked
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Titel")
                .setMessage("This is a message")
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel, null)
                .create()
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setupGraph(AppComponent appComponent, ActivityModule activityModule) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void setupService() {
        Intent alarm = new Intent(this, AlarmReceiver.class);
        if (PendingIntent.getBroadcast(this, 0, alarm, PendingIntent.FLAG_NO_CREATE) == null) {
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarm, 0);
            am.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime(), 1000 * 30, pendingIntent);
        }
    }
}
