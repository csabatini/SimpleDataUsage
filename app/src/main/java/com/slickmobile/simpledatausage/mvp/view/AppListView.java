package com.slickmobile.simpledatausage.mvp.view;

import com.slickmobile.simpledatausage.model.InstalledApp;

import java.util.List;

public interface AppListView {
    void showToast(String message);
    void showApps(List<InstalledApp> appList);
}
