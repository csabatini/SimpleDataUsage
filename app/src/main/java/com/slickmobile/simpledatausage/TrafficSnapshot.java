package com.slickmobile.simpledatausage;

import java.util.HashMap;
import android.content.Context;
import android.content.pm.ApplicationInfo;

public class TrafficSnapshot {
    private TrafficRecord device=null;
    private HashMap<Integer, TrafficRecord> apps=
            new HashMap<Integer, TrafficRecord>();

    public TrafficSnapshot(Context ctxt) {
        device=new TrafficRecord();

        HashMap<Integer, String> appNames=new HashMap<Integer, String>();

        for (ApplicationInfo app :
                ctxt.getPackageManager().getInstalledApplications(0)) {
            appNames.put(app.uid, app.packageName);
        }

        for (Integer uid : appNames.keySet()) {
            apps.put(uid, new TrafficRecord(uid, appNames.get(uid)));
        }
    }

    public TrafficRecord getDevice() {
        return device;
    }

    public void setDevice(TrafficRecord device) {
        this.device = device;
    }

    public HashMap<Integer, TrafficRecord> getApps() {
        return apps;
    }

    public void setApps(HashMap<Integer, TrafficRecord> apps) {
        this.apps = apps;
    }
}
