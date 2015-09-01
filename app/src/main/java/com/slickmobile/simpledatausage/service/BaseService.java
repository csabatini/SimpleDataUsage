package com.slickmobile.simpledatausage.service;

import android.app.Service;

import com.slickmobile.simpledatausage.App;
import com.slickmobile.simpledatausage.injection.component.AppComponent;

public abstract class BaseService extends Service {

    protected AppComponent getAppComponent() {
        return ((App) this.getApplicationContext()).getComponent();
    }
}
