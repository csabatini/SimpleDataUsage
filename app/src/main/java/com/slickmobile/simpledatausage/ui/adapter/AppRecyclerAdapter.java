package com.slickmobile.simpledatausage.ui.adapter;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slickmobile.simpledatausage.R;
import com.slickmobile.simpledatausage.model.InstalledApp;
import com.slickmobile.simpledatausage.mvp.presenter.AppListPresenter;
import com.slickmobile.simpledatausage.util.ByteFormatter;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AppRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<InstalledApp> mList = Collections.emptyList();
    private AppListPresenter presenter;
    private ByteFormatter formatter;
    private Context ctx;

    public AppRecyclerAdapter(AppListPresenter presenter, ByteFormatter formatter, Context context) {
        this.presenter = presenter;
        this.formatter = formatter;
        this.ctx = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        ViewHolder vh = (ViewHolder) viewHolder;
        vh.pkgName.setText(mList.get(i).getLabel());
        try {
            vh.appIcon.setImageDrawable(
                ctx.getPackageManager().getApplicationIcon(mList.get(i).getPackageName()));
        } catch (PackageManager.NameNotFoundException ex) {
            // icon not found
        }

        //vh.rxBytes.setText(String.valueOf(TrafficStats.getUidRxBytes(mList.get(i).uid) / 1024f));
        vh.rxBytes.setText(formatter.format(mList.get(i).getBytes()));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void updateData(List<InstalledApp> data) {
        Collections.sort(data, Collections.reverseOrder(
            new Comparator<InstalledApp>() {
                @Override
                public int compare(InstalledApp lhs, InstalledApp rhs) {
                    return Long.valueOf(lhs.getBytes()).compareTo(rhs.getBytes());
                }
            }
        ));
        mList = data;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.iconView)
        ImageView appIcon;
        @Bind(R.id.pkgView)
        TextView pkgName;
        @Bind(R.id.rxBytes)
        TextView rxBytes;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
