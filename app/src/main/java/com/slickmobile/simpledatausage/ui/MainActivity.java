package com.slickmobile.simpledatausage.ui;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.receiver.AlarmReceiver;
import com.slickmobile.simpledatausage.util.ByteFormatter;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {

    @Inject
    AlarmManager am;
    @Inject
    ByteFormatter bf;

    private final int NOTIFICATION_ID = 568331681;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
        Fragment currentFragment =
                getSupportFragmentManager().findFragmentById(R.id.container);
        if (currentFragment == null) {
            addFragment(R.id.container, new AppListFragment());
        }
        setupService();
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
