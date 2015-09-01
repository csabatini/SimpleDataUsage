package com.slickmobile.simpledatausage.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.slickmobile.simpledatausage.service.CollectUsageService;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent collectService = new Intent(context, CollectUsageService.class);
        context.startService(collectService);
    }
}
