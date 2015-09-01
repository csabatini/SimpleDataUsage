package com.slickmobile.simpledatausage.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.data.DatabaseHelper;
import com.slickmobile.simpledatausage.injection.component.AppComponent;
import com.slickmobile.simpledatausage.injection.module.ActivityModule;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import timber.log.Timber;

public class SummaryFragment extends BaseFragment {

    @Inject
    BriteDatabase db;

    @Bind(R.id.usage)
    TextView usageView;
    
    private CompositeSubscription mSub = new CompositeSubscription();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mSub.add(db.createQuery(DatabaseHelper.TOTAL_USAGE_TABLE,
                "SELECT " + DatabaseHelper.COL_BYTES + " FROM " + DatabaseHelper.TOTAL_USAGE_TABLE)
                .map(new Func1<SqlBrite.Query, Long>() {
                    @Override
                    public Long call(SqlBrite.Query query) {
                        Cursor cursor = query.run();
                        cursor.moveToFirst();
                        return cursor.getLong(cursor.getColumnIndex(DatabaseHelper.COL_BYTES));
                    }
                })

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Func1<Long, Boolean>() {
                    @Override
                    public Boolean call(Long result) {
                        return (result != null);
                    }
                })
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "onError");
                    }

                    @Override
                    public void onNext(Long bytes) {
                        Timber.d("onNext - %d", bytes);
                        usageView.setText(String.valueOf(bytes));
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSub.unsubscribe();
        Timber.d("onDestroyView");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAppComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Timber.d("fragment onResume");

    }


    @Override
    public void onPause() {
        super.onPause();
        Timber.d("fragment onPause");
    }

    @Override
    protected void setupGraph(AppComponent appComponent, ActivityModule activityModule) {
    }
}
