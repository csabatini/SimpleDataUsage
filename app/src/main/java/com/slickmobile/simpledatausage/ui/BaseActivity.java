package com.slickmobile.simpledatausage.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.slickmobile.simpledatausage.App;
import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.injection.component.AppComponent;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;

public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGraph(getAppComponent(), getActivityModule());
        setContentView(getLayoutId());
        initializeToolbar();
    }

    protected void addFragment(int containerId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(containerId, fragment)
                .commit();
    }

    protected AppComponent getAppComponent() {
        return ((App) getApplicationContext()).getComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    protected abstract int getLayoutId();

    protected abstract void setupGraph(AppComponent appComponent, ActivityModule activityModule);

    private void initializeToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }
}
