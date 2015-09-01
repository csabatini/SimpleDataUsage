package com.slickmobile.simpledatausage.mvp.interactor;

import com.slickmobile.simpledatausage.model.InstalledApp;

import java.util.List;

import rx.Observable;

public interface AppListInteractor {

    Observable<List<InstalledApp>> getInstalledApps();
}
