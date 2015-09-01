package com.slickmobile.simpledatausage.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.injection.component.AppComponent;
import com.slickmobile.simpledatausage.injection.component.DaggerAppListComponent;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;
import com.slickmobile.simpledatausage.injection.module.AppListModule;
import com.slickmobile.simpledatausage.model.InstalledApp;
import com.slickmobile.simpledatausage.mvp.presenter.AppListPresenter;
import com.slickmobile.simpledatausage.mvp.view.AppListView;
import com.slickmobile.simpledatausage.ui.adapter.AppRecyclerAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppListFragment extends BaseFragment implements AppListView {

    @Inject
    AppListPresenter presenter;
    @Inject
    AppRecyclerAdapter adapter;
    @Inject
    Activity activity;

    @Bind(R.id.recycler)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_app_list, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.pause();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showApps(List<InstalledApp> appList) {
        adapter.updateData(appList);
    }

    @Override
    protected void setupGraph(AppComponent appComponent, ActivityModule activityModule) {
        DaggerAppListComponent.builder()
                .appComponent(appComponent)
                .activityModule(activityModule)
                .appListModule(new AppListModule(this))
                .build()
                .inject(this);
    }
}
