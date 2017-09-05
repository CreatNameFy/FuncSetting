package com.android.funcsetting;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/9/4.
 */
public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.AppInfoHodler> {
    private List<AppInfo> data;
    private Context mContext;

    public AppListAdapter(Context context, List<AppInfo> data) {
        this.data = data;
        mContext = context;
    }

    @Override
    public AppListAdapter.AppInfoHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.app_item, parent, false);
        return new AppInfoHodler(view, viewType);
    }

    @Override
    public void onBindViewHolder(AppListAdapter.AppInfoHodler holder, int position) {
        final AppInfo info = data.get(position);
        holder.app_name.setText(info.getAppName());
        holder.app_icon.setImageDrawable(info.getDrawable());
        holder.right_icon.setImageResource(R.mipmap.add);
        if(info.isContanin()){
            holder.app_name.setTextColor(Color.GRAY);
        }else{
            holder.app_name.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AppInfoHodler extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView app_name;
        public ImageView app_icon;
        public ImageView right_icon;
        public RelativeLayout item;

        public AppInfoHodler(View itemView, int viewType) {
            super(itemView);

            app_name = (TextView) itemView.findViewById(R.id.app_name);
            right_icon = (ImageView) itemView.findViewById(R.id.right_icon);
            app_icon = (ImageView) itemView.findViewById(R.id.app_icon);
            item = (RelativeLayout) itemView.findViewById(R.id.item);


        }
    }
}
