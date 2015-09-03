package com.slickmobile.simpledatausage.mvp.interactor;

import com.slickmobile.simpledatausage.mvp.listener.MainInteractorListener;

import javax.inject.Inject;

public class MainInteractorImpl implements MainInteractor {

    @Inject
    public MainInteractorImpl() {
    }

    @Override
    public void handleUserPlanChoice(boolean choiceMade, MainInteractorListener listener) {
        if (!choiceMade) {
            listener.onUserPlanChoiceNotMade();
        }
    }
}