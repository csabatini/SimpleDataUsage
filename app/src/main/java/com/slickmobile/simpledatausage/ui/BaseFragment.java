package com.slickmobile.simpledatausage.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.slickmobile.simpledatausage.injection.component.AppComponent;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;

public abstract class BaseFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGraph(getAppComponent(), getActivityModule());
    }

    protected AppComponent getAppComponent() {
        return ((BaseActivity) getActivity()).getAppComponent();
    }

    protected ActivityModule getActivityModule() {
        return ((BaseActivity) getActivity()).getActivityModule();
    }

    protected abstract void setupGraph(AppComponent appComponent, ActivityModule activityModule);

}