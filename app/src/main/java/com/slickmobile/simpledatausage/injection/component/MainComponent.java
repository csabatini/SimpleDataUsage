package com.slickmobile.simpledatausage.injection.component;

import com.slickmobile.simpledatausage.injection.ActivityScope;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;
import com.slickmobile.simpledatausage.injection.module.MainModule;
import com.slickmobile.simpledatausage.mvp.interactor.MainInteractor;
import com.slickmobile.simpledatausage.mvp.presenter.MainPresenter;
import com.slickmobile.simpledatausage.mvp.view.MainView;
import com.slickmobile.simpledatausage.ui.MainActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = {ActivityModule.class, MainModule.class})
public interface MainComponent {
    void inject(MainActivity activity);
    MainView view();
    MainInteractor interactor();
    MainPresenter presenter();
}
