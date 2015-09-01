package com.slickmobile.simpledatausage.injection.module;

import android.content.Context;

import com.slickmobile.simpledatausage.injection.ActivityScope;
import com.slickmobile.simpledatausage.mvp.interactor.AppListInteractor;
import com.slickmobile.simpledatausage.mvp.interactor.AppListInteractorImpl;
import com.slickmobile.simpledatausage.mvp.presenter.AppListPresenter;
import com.slickmobile.simpledatausage.mvp.presenter.AppListPresenterImpl;
import com.slickmobile.simpledatausage.mvp.view.AppListView;
import com.slickmobile.simpledatausage.ui.adapter.AppRecyclerAdapter;
import com.slickmobile.simpledatausage.util.ByteFormatter;

import dagger.Module;
import dagger.Provides;

@Module
public class AppListModule {

    private AppListView view;

    public AppListModule(AppListView view) {
        this.view = view;
    }

    @Provides
    @ActivityScope
    public AppListView provideView() {
        return view;
    }

    @Provides
    @ActivityScope
    public AppListInteractor provideInteractor(AppListInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @ActivityScope
    public AppListPresenter providePresenter(AppListPresenterImpl presenter) {
        return presenter;
    }

    @Provides
    @ActivityScope
    public AppRecyclerAdapter provideAdapter(AppListPresenter presenter, ByteFormatter bf, Context context) {
        return new AppRecyclerAdapter(presenter, bf, context);
    }


}
