package com.slickmobile.simpledatausage.ui;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slickmobile.simpledatausage.ui.adapter.AppRecyclerAdapter;
import com.slickmobile.simpledatausage.R;

import java.util.Collections;
import java.util.List;

public class MonitorActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<ApplicationInfo> apps = Collections.emptyList();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);

        apps = this.getPackageManager().getInstalledApplications(0);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);
        //recycler.setAdapter(adapter);
        //adapter.updateData(apps);
    }
}
