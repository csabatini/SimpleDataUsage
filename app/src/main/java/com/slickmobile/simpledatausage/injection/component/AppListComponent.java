package com.slickmobile.simpledatausage.injection.component;

import com.slickmobile.simpledatausage.injection.ActivityScope;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;
import com.slickmobile.simpledatausage.injection.module.AppListModule;
import com.slickmobile.simpledatausage.mvp.interactor.AppListInteractor;
import com.slickmobile.simpledatausage.mvp.presenter.AppListPresenter;
import com.slickmobile.simpledatausage.mvp.view.AppListView;
import com.slickmobile.simpledatausage.ui.AppListFragment;
import com.slickmobile.simpledatausage.ui.adapter.AppRecyclerAdapter;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, AppListModule.class})
public interface AppListComponent extends ActivityComponent {
    void inject(AppListFragment fragment);
    AppListView view();
    AppListInteractor interactor();
    AppListPresenter presenter();
    AppRecyclerAdapter adapter();
}
