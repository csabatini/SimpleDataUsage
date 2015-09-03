package com.slickmobile.simpledatausage.injection.module;

import com.slickmobile.simpledatausage.injection.ActivityScope;
import com.slickmobile.simpledatausage.mvp.interactor.MainInteractor;
import com.slickmobile.simpledatausage.mvp.interactor.MainInteractorImpl;
import com.slickmobile.simpledatausage.mvp.presenter.MainPresenter;
import com.slickmobile.simpledatausage.mvp.presenter.MainPresenterImpl;
import com.slickmobile.simpledatausage.mvp.view.MainView;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public MainView provideView() {
        return view;
    }

    @Provides
    @ActivityScope
    public MainInteractor provideInteractor(MainInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    public MainPresenter providePresenter(MainPresenterImpl presenter) {
        return presenter;
    }
}
