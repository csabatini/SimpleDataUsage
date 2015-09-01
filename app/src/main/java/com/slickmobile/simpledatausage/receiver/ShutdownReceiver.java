package com.slickmobile.simpledatausage.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.util.Log;

public class ShutdownReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
            Log.d("SimpleDataUsage", "Shutdown!");
            Log.d("SimpleDataUsage", "Recording stats: " + TrafficStats.getTotalRxBytes());
    }
}
