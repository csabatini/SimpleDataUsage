package com.slickmobile.simpledatausage.mvp.presenter;

import com.slickmobile.simpledatausage.mvp.interactor.MainInteractor;
import com.slickmobile.simpledatausage.mvp.listener.MainInteractorListener;
import com.slickmobile.simpledatausage.mvp.view.MainView;

import javax.inject.Inject;

public class MainPresenterImpl implements MainPresenter, MainInteractorListener {

    private MainView view;
    private MainInteractor interactor;

    @Inject
    public MainPresenterImpl(MainView view, MainInteractor interactor) {
        this.view = view;
        this.interactor = interactor;
    }

    @Override
    public void create() {
        boolean choice = view.getUserPlanChoice();
        interactor.handleUserPlanChoice(choice, this);
    }

    @Override
    public void onUserPlanChoiceNotMade() {
        view.showUserChoiceDialog();
    }
}
