package com.slickmobile.simpledatausage.mvp.presenter;

import com.slickmobile.simpledatausage.model.InstalledApp;
import com.slickmobile.simpledatausage.mvp.interactor.AppListInteractor;
import com.slickmobile.simpledatausage.mvp.view.AppListView;
import com.slickmobile.simpledatausage.rx.AppSchedulers;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import timber.log.Timber;

public class AppListPresenterImpl extends RxPresenter implements AppListPresenter {

    private AppListView view;
    private AppListInteractor interactor;
    private AppSchedulers schedulers;

    @Inject
    public AppListPresenterImpl(AppListView view, AppListInteractor interactor,
                                AppSchedulers schedulers) {
        this.view = view;
        this.interactor = interactor;
        this.schedulers = schedulers;
    }

    @Override
    public void resume() {
        add(interactor.getInstalledApps()
                  .compose(this.<List<InstalledApp>>applySchedulers(schedulers))
                  .subscribe(new AppSubscriber()));
    }

    @Override
    public void pause() {
        unsubscribe();
    }

    public class AppSubscriber extends Subscriber<List<InstalledApp>> {
        @Override
        public void onCompleted() {
            Timber.d("onComplete retrieving apps");
        }

        @Override
        public void onError(Throwable e) {
            Timber.e(e, "onError retrieving apps");
            view.showToast("Error loading apps");
        }

        @Override
        public void onNext(List<InstalledApp> apps) {
            Timber.d("onNext with list size %d", apps.size());
            view.showApps(apps);
        }
    }
}
